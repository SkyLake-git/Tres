package com.tres.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
}
