package com.tres.snooze;

import java.util.ArrayList;
import java.util.HashMap;

public class Sleeper {

	private final SleeperSharedObject sharedObject;

	private final HashMap<Integer, Runnable> notifiers;

	private int nextNotifierId;

	public Sleeper() {
		this.sharedObject = new SleeperSharedObject();
		this.notifiers = new HashMap<>();
		this.nextNotifierId = 0;
	}

	public void addNotifier(SleeperNotifier notifier, Runnable task) {
		int id = this.nextNotifierId++;
		notifier.attachSleeper(this.sharedObject, id);
		this.notifiers.put(id, task);
	}

	public void removeNotifier(SleeperNotifier notifier) {
		this.notifiers.remove(notifier.getId());
	}

	private void sleep(long timeoutMillis) {
		synchronized (this.sharedObject) {
			if (this.sharedObject.size() == 0) {
				try {
					this.sharedObject.wait(timeoutMillis);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void sleepUntilNotification() {
		this.sleep(0);
		this.processNotifications();
	}

	public void processNotifications() {
		while (true) {
			ArrayList<Integer> ids = new ArrayList<>();

			synchronized (this.sharedObject) {
				ids.addAll(this.sharedObject.keySet());
				this.sharedObject.clear();
			}

			if (ids.size() == 0) {
				break;
			}

			for (int id : ids) {
				if (!this.notifiers.containsKey(id)) {
					continue;
				}

				(this.notifiers.get(id)).run();
			}
		}
	}
}
