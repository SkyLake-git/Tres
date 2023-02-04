package com.tres.utils;


@FunctionalInterface
public interface AbsorbSupplier<T> {

	T get() throws Exception;
}
