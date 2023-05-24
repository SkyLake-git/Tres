package com.tres.network.uno.container;

public class SlotInfo<E> {

	public int slot;

	public E item;

	public SlotInfo(int slot, E item) {
		this.slot = slot;
		this.item = item;
	}
}
