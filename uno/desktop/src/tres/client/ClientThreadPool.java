package tres.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

public class ClientThreadPool {

	protected ScheduledExecutorService service;

	public ClientThreadPool(int poolSize) {
		this.service = Executors.newScheduledThreadPool(poolSize);
	}

	public ScheduledExecutorService getService() {
		return service;
	}

	public <T> CompletableFuture<T> supply(Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(supplier, this.service);
	}

	public CompletableFuture<Void> run(Runnable runnable) {
		return CompletableFuture.runAsync(runnable, this.service);
	}
}
