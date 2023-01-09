package com.tres.snooze;

public class SleeperNotifier {

	protected SleeperSharedObject shared = null;

	protected Integer id = null;

	public SleeperNotifier() {
	}

	public void attachSleeper(SleeperSharedObject shared, int id) {
		this.shared = shared;
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public int getIdNonNull() throws Exception {
		if (this.id == null) {
			throw new Exception("id must be Integer.");
		}

		return this.id;
	}

	public void wakeup() {
		synchronized (this.shared) {
			if (this.shared.get(this.id) == null) {
				this.shared.put(this.id, this.id);
				this.shared.notifyAll();
			}
		}
	}
}
