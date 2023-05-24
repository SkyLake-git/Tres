package com.tres.network.uno.container;

public abstract class BaseContainerListener<E> implements ContainerListener<E> {

	private Container<E> container = null;

	@Override
	public void listen(Container<E> container) {
		if (this.container != null) {
			throw new RuntimeException("already listening");
		}
		container.getListeners().add(this);
		this.container = container;
	}

	@Override
	public Container<E> getContainer() {
		if (this.container == null) {
			throw new RuntimeException("getting container: not listening");
		}
		return this.container;
	}
}
