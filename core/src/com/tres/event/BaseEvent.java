package com.tres.event;

import java.util.Date;

public class BaseEvent implements Event {

	protected Date createdAt;

	public BaseEvent(Date createdAt) {
		this.createdAt = createdAt;
	}

	public BaseEvent() {
		this.createdAt = new Date();
	}

	@Override
	public Date getCreatedAt() {
		return this.createdAt;
	}
}
