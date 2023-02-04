package com.tres.utils;


import java.util.function.Function;

@FunctionalInterface
public interface AbsorbFunction<T, R> {

	R apply(T t) throws Exception;
}
