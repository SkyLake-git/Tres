package com.tres.network.uno.container;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class FuncContainerListener<E> extends BaseContainerListener<E> {

	protected BiConsumer<Integer, E> onSlotChange;

	protected Consumer<HashMap<Integer, E>> onContentChange;

	public FuncContainerListener(BiConsumer<Integer, E> onSlotChange, Consumer<HashMap<Integer, E>> onContentChange) {
		this.onSlotChange = onSlotChange;
		this.onContentChange = onContentChange;
	}

	@Override
	public void onSlotChange(int slot, E before) {
		this.onSlotChange.accept(slot, before);
	}

	@Override
	public void onContentChange(HashMap<Integer, E> oldContents) {
		this.onContentChange.accept(oldContents);
	}
}
