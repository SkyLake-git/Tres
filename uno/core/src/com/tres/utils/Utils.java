package com.tres.utils;

import com.tres.snooze.Sleeper;
import com.tres.snooze.SleeperNotifier;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

	public static <C extends Enum<C>> C getEnumOrdinal(Class<C> enumClass, int ordinal) throws IndexOutOfBoundsException {
		if (!enumClass.isEnum()) {
			throw new RuntimeException("Provided Class is not enum");
		}

		C[] enums = enumClass.getEnumConstants();

		if (ordinal < 0 || enums.length < ordinal) {
			throw new IndexOutOfBoundsException("ordinal");
		}

		return enums[ordinal];

	}

	public static <S> ArrayList<S> toArrayList(S[] array) {
		return Arrays.stream(array).collect(Collectors.toCollection(ArrayList::new));
	}

	public static byte[] toByteArray(IntStream stream) {
		return stream.collect(ByteArrayOutputStream::new, (baos, i) -> baos.write((byte) i),
						(baos1, baos2) -> baos1.write(baos2.toByteArray(), 0, baos2.size()))
				.toByteArray();
	}

	public static <S> Collection<S> toCollection(S item) {
		Collection<S> cl = new ArrayList<>();
		cl.add(item);
		return cl;
	}

	public static void synchronizeSleeperOnce(Sleeper sleeper, Consumer<SleeperNotifier> task, boolean wakeup) {
		SleeperNotifier notifier = new SleeperNotifier();

		sleeper.addNotifier(notifier, () -> {
			task.accept(notifier);
			sleeper.removeNotifier(notifier);
		});

		if (wakeup) {
			notifier.wakeup();
		}
	}
}
