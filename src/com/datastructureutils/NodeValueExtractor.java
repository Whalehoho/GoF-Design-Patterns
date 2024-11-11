package com.datastructureutils;

@FunctionalInterface
public interface NodeValueExtractor<T> {
    int extractId(T value);
}