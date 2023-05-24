package com.tres.network.uno.container;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class Container<E> {
	protected Set<ContainerListener<E>> listeners;

	public Container() {
		this.listeners = new HashSet<>();
	}

	public Set<ContainerListener<E>> getListeners() {
		return listeners;
	}

	public List<E> getContents() {
		return new ArrayList<>(this.internalGetContents());
	}

	public void setContents(List<SlotInfo<E>> contents) {
		HashMap<Integer, E> oldContents = this.getAll();

		this.internalSetContents(contents);

		for (ContainerListener<E> listener : this.listeners) {
			listener.onContentChange(oldContents);
		}
	}

	abstract protected Collection<E> internalGetContents();

	public HashMap<Integer, E> getAll() {
		return new HashMap<>(this.internalGetAll());
	}

	abstract protected HashMap<Integer, E> internalGetAll();

	public void clear() {
		this.internalClear();
	}

	abstract protected void internalClear();

	abstract protected void internalSetContents(List<SlotInfo<E>> contents);

	public @Nullable E get(int slot) {
		return this.internalGet(slot);
	}

	abstract protected @Nullable E internalGet(int slot);

	public void set(int slot, @Nullable E item) {
		E before = this.get(slot);

		this.internalSet(slot, item);

		for (ContainerListener<E> listener : this.listeners) {
			listener.onSlotChange(slot, before);
		}
	}

	abstract protected void internalSet(int slot, @Nullable E item);

	/**
	 * alias of ::set(int, null)
	 */
	public void remove(int slot) {
		this.set(slot, null);
	}

	public void removeAll(Collection<Integer> slots) {
		for (int slot : slots) {
			this.remove(slot);
		}
	}

	public void add(E item) {
		this.internalAdd(item);
	}

	abstract protected void internalAdd(E item);

	public void addAll(Collection<E> items) {
		for (E item : items) {
			this.add(item);
		}
	}
}
