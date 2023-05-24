package com.tres.utils;

@FunctionalInterface
public interface AbsorbConsumer<T> {

	void accept(T t) throws Exception;
}
