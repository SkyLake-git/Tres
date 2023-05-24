package com.tres.utils;

import com.tres.event.EventEmitter;

import java.util.ArrayList;
import java.util.List;

public class EventLinks {

	protected List<String> links;

	protected EventEmitter emitter;

	public EventLinks(EventEmitter eventEmitter) {
		this.links = new ArrayList<>();
		this.emitter = eventEmitter;
	}

	public EventEmitter getEventEmitter() {
		return emitter;
	}

	public List<String> getAll() {
		return links;
	}

	public void on(String uuid) {
		this.links.add(uuid);
	}

	public void offAll() {
		for (String uuid : this.links) {
			this.emitter.off(uuid);
		}
	}
}
