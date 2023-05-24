package com.tres.network.uno.container;

import java.util.function.BiConsumer;

public abstract class FuncDifferenceContainerListener<E> extends DifferenceContainerListener<E> {

	protected BiConsumer<Integer, E> onItemAdded;

	protected BiConsumer<Integer, E> onItemRemoved;

	public FuncDifferenceContainerListener(BiConsumer<Integer, E> onItemAdded, BiConsumer<Integer, E> onItemRemoved) {
		this.onItemAdded = onItemAdded;
		this.onItemRemoved = onItemRemoved;
	}

	@Override
	public void onItemAdded(int slot, E item) {
		this.onItemAdded.accept(slot, item);
	}

	@Override
	public void onItemRemoved(int slot, E item) {
		this.onItemRemoved.accept(slot, item);
	}
}
