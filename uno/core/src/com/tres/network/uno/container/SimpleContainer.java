package com.tres.network.uno.container;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class SimpleContainer<E> extends Container<E> {

	protected HashMap<Integer, E> list;

	public SimpleContainer() {
		super();
		this.list = new HashMap<>();
	}

	@Override
	protected Collection<E> internalGetContents() {
		return this.list.values();
	}

	@Override
	protected HashMap<Integer, E> internalGetAll() {
		return this.list;
	}

	@Override
	protected void internalClear() {
		this.list.clear();
	}

	@Override
	protected void internalSetContents(List<SlotInfo<E>> contents) {
		this.clear();

		for (SlotInfo<E> info : contents) {
			this.set(info.slot, info.item);
		}
	}

	@Override
	protected @Nullable E internalGet(int slot) {
		return this.list.get(slot);
	}

	@Override
	protected void internalSet(int slot, @Nullable E item) {
		if (item == null) {
			this.list.remove(slot);
		} else {
			this.list.put(slot, item);
		}
	}

	@Override
	protected void internalAdd(E item) {
		int limit = 100000;

		for (int i = 0; i < limit; i++) {
			if (this.get(i) == null) {
				this.set(i, item);
				break;
			}
		}
	}
}
