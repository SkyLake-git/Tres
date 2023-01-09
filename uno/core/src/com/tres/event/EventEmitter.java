package com.tres.event;

import com.github.jafarlihi.eemit.Callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class EventEmitter extends com.github.jafarlihi.eemit.EventEmitter<Event> {

	public <E extends Event> String on(Class<E> channel, Callback<E> callback) {
		return super.on(String.valueOf(channel), (Callback<Event>) callback);
	}

	public void emit(Event event) {
		super.emit(String.valueOf(event.getClass()), event);
	}

	public ArrayList<String> on(Object obj) {
		ArrayList<String> uuids = new ArrayList<>();

		for (Method method : obj.getClass().getMethods()) {
			if (method.isAnnotationPresent(NotHandler.class)) {
				continue;
			}

			for (Parameter param : method.getParameters()) {
				try {
					Class<? extends Event> tmp = (Class<? extends Event>) param.getType(); // fixme: hack
				} catch (Throwable e) {
					continue;
				}

				String uuid = this.on((Class<? extends Event>) param.getType(), (channel, event) -> {
					try {
						method.invoke(obj, event);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				});

				uuids.add(uuid);
			}
		}

		return uuids;
	}
}
