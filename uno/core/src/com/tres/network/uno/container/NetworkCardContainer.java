package com.tres.network.uno.container;

import com.tres.network.uno.NetworkCard;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkCardContainer extends SimpleContainer<NetworkCard> {

	protected HashMap<Integer, Integer> mapByRuntimeId;

	public NetworkCardContainer() {
		super();

		this.mapByRuntimeId = new HashMap<>();
	}

	public HashMap<Integer, Integer> getRuntimeIdMapping() {
		return mapByRuntimeId;
	}

	public NetworkCard getByRuntimeId(int runtimeId) {
		return this.get(this.mapByRuntimeId.get(runtimeId));
	}

	public Integer getSlotByRuntimeId(int runtimeId) {
		return this.mapByRuntimeId.get(runtimeId);
	}

	protected void checkDuplication(int runtimeId) {
		if (this.mapByRuntimeId.containsKey(runtimeId)) {
			throw new RuntimeException("duplication of runtime id");
		}
	}

	@Override
	protected Collection<NetworkCard> internalGetContents() {
		return super.internalGetContents();
	}

	@Override
	protected HashMap<Integer, NetworkCard> internalGetAll() {
		return super.internalGetAll();
	}

	@Override
	protected void internalClear() {
		super.internalClear();

		this.mapByRuntimeId.clear();
	}

	@Override
	protected void internalSetContents(List<SlotInfo<NetworkCard>> contents) {
		super.internalSetContents(contents);

		int i = 0;
		for (SlotInfo<NetworkCard> info : contents) {
			this.checkDuplication(info.item.runtimeId);
			this.mapByRuntimeId.put(info.item.runtimeId, i++);
		}
	}

	@Override
	protected @Nullable NetworkCard internalGet(int slot) {
		return super.internalGet(slot);
	}

	@Override
	protected void internalSet(int slot, @Nullable NetworkCard item) {
		super.internalSet(slot, item);

		if (item != null) {
			this.checkDuplication(item.runtimeId);

			this.mapByRuntimeId.put(item.runtimeId, slot);
		} else {
			for (Map.Entry<Integer, Integer> entry : this.mapByRuntimeId.entrySet()) {
				if (entry.getValue() == slot) {
					this.mapByRuntimeId.remove(entry.getKey());
					break;
				}
			}
		}
	}

	@Override
	protected void internalAdd(NetworkCard item) {
		super.internalAdd(item);
	}
}
