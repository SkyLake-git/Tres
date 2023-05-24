package com.tres.network.uno.container;

import java.util.HashMap;

public abstract class DifferenceContainerListener<E> extends BaseContainerListener<E> {

	abstract public void onItemAdded(int slot, E item);

	abstract public void onItemRemoved(int slot, E item);

	@Override
	public void onSlotChange(int slot, E before) {
		E item = this.getContainer().get(slot);

		if (before != null) {
			this.onItemRemoved(slot, before);
		}

		if (item != null) {
			this.onItemAdded(slot, item);
		}
	}

	@Override
	public void onContentChange(HashMap<Integer, E> oldContents) {
		oldContents.forEach(this::onItemRemoved);

		HashMap<Integer, E> contents = this.getContainer().getAll();

		contents.forEach(this::onItemAdded);
	}
}
