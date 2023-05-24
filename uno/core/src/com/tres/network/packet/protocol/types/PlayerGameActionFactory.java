package com.tres.network.packet.protocol.types;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.protocol.types.action.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PlayerGameActionFactory {
	private static final HashMap<Integer, Constructor<? extends PlayerGameAction>> actions = new HashMap<>();

	static {
		register(new PlayCardPlayerGameAction());
		register(new DrawCardPlayerGameAction());
		register(new ShoutUnoPlayerGameAction());
		register(new SkipTurnPlayerGameAction());
		register(new AccusateUnoPlayerGameAction());
	}

	public static <T extends PlayerGameAction> void register(T action) {
		try {
			actions.put(action.getId(), action.getClass().getConstructor());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static PlayerGameAction get(int id) {
		Constructor<? extends PlayerGameAction> actionConstructor = actions.get(id);
		if (actionConstructor == null) {
			return null;
		}

		try {
			return actionConstructor.newInstance();
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static PlayerGameAction readAction(PacketDecoder in) throws IOException {
		int id = in.readInt();
		PlayerGameAction action = get(id);

		if (action != null) {
			action.read(in);

			return action;
		}

		return null;
	}

}
