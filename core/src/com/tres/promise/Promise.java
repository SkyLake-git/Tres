package com.tres.promise;

import java.util.ArrayList;

public final class Promise<E> {

	private final ArrayList<Runnable> onSuccess;

	private final ArrayList<Runnable> onFailure;

	private final ArrayList<Runnable> onComplete;

	private boolean resolved;

	private E result;

	public Promise() {
		this.onSuccess = new ArrayList<>();
		this.onFailure = new ArrayList<>();
		this.onComplete = new ArrayList<>();
		this.resolved = false;
		this.result = null;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void onSuccess(Runnable callback) {
		if (this.resolved && this.result != null) {
			callback.run();
			return;
		}
		this.onSuccess.add(callback);
	}

	public void onFailure(Runnable callback) {
		if (this.resolved && this.result == null) {
			callback.run();
			return;
		}
		this.onFailure.add(callback);
	}

	public void onComplete(Runnable callback) {
		if (this.resolved) {
			callback.run();
			return;
		}
		this.onComplete.add(callback);
	}

	public void resolve(E result) {
		synchronized (this) {
			if (this.resolved) {
				return;
			}
			this.resolved = true;

			if (this.result == null) {
				this.result = result;
			}

			for (Runnable callback : this.onSuccess) {
				callback.run();
			}

			for (Runnable callback : this.onComplete) {
				callback.run();
			}
		}
	}

	public void reject() {
		synchronized (this) {
			if (this.resolved) {
				return;
			}

			this.resolved = true;

			for (Runnable callback : this.onFailure) {
				callback.run();
			}

			for (Runnable callback : this.onComplete) {
				callback.run();
			}
		}
	}

	public E getResult() {
		synchronized (this) {
			return this.result;
		}
	}
}
