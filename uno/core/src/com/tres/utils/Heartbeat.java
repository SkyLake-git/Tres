package com.tres.utils;

import java.util.ArrayList;

public class Heartbeat extends Thread {

	protected double tps;

	protected double realTps;

	protected double delay;

	protected ArrayList<Syncable> list;

	protected double load;

	protected long nextRun;

	public Heartbeat(double tps) {
		this.tps = tps;
		this.delay = 1 / tps;
		this.list = new ArrayList<Syncable>();
		this.load = 0.0;
		this.realTps = 0.0;
		this.nextRun = 0;
	}

	public double getTps() {
		return tps;
	}

	public double getRealTps() {
		return realTps;
	}

	public ArrayList<Syncable> getList() {
		return list;
	}

	public double getLoad() {
		return load;
	}

	public long getNextRun() {
		return nextRun;
	}

	public void run() {
		while (!this.isInterrupted()) {
			try {
				Thread.sleep(3);
				// busy sleep?
			} catch (InterruptedException e) {
				break;
			}

			long start = System.currentTimeMillis();
			if (start - this.nextRun >= 0.0) {
				ArrayList<Syncable> list = (ArrayList<Syncable>) this.list.clone();
				for (Syncable syncable : list) {
					syncable.tick();
				}

				long end = System.currentTimeMillis();

				long delayMillis = (long) (this.delay * 1000);
				long loadMillis = end - start;
				this.load = (double) loadMillis / delayMillis;

				long wait = Math.max(0, delayMillis);

				this.realTps = 1000f / (Math.max(delayMillis, loadMillis));
				this.nextRun = start + delayMillis;
			}
		}
	}

	public interface Syncable {
		void tick();
	}
}
