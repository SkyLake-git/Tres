package com.tres.network.uno.container;

import java.util.HashMap;

public interface ContainerListener<E> {

	void listen(Container<E> container);

	Container<E> getContainer();

	void onSlotChange(int slot, E before);

	void onContentChange(HashMap<Integer, E> oldContents);
}
