package com.tres.utils;


@FunctionalInterface
public interface AbsorbFunction<T, R> {

	R apply(T t) throws Exception;
}
