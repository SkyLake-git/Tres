package com.tres.utils;

import java.util.function.Consumer;

@FunctionalInterface
public interface AbsorbConsumer<T> {

	void accept(T t) throws Exception;
}
