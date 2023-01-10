package com.tres.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ParameterMethodCaller<E> {

	protected HashMap<Class<E>, Method> map;

	protected Object target;

	public ParameterMethodCaller(Object target) {
		this.map = new HashMap<>();
		this.target = target;

		this.analyze();
	}

	private void analyze() {
		for (Method method : this.target.getClass().getMethods()) {
			for (Class<?> paramType : method.getParameterTypes()) {
				try {
					Class<E> tmp = (Class<E>) paramType; // fixme: hack
				} catch (Throwable e) {
					continue;
				}

				this.map.put((Class<E>) paramType, method);
				break;
			}
		}
	}

	public void call(E param) throws Throwable {
		Method method = this.map.get(param.getClass());
		if (method != null) {
			try {
				method.invoke(this.target, param);
			} catch (IllegalAccessException e) {
				throw e;
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		}
	}
}
