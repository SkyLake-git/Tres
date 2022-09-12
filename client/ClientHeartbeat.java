package client;

public class ClientHeartbeat extends Thread {

	protected double tps;

	protected double realTps;
	protected double delay;
	protected Client client;


	protected double load;

	ClientHeartbeat(Client client, double tps) {
		this.tps = tps;
		this.delay = 1 / tps;
		this.client = client;
		this.load = 0.0;
		this.realTps = 0.0;
	}

	public double getLoad() {
		return this.load;
	}

	public double getRealTps() {
		return this.realTps;
	}

	public void run() {
		while (!this.isInterrupted()) {
			long start = System.currentTimeMillis();
			this.client.tick();
			long end = System.currentTimeMillis();

			long load = end - start;
			double loadPercentage = load / this.delay;
			this.load = loadPercentage;

			double wait = Math.max(0, (this.delay - load) * 1000);

			this.realTps = Math.min(20, 1 / (Math.max(0.001, load)));

			try {
				Thread.sleep((long) wait);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
