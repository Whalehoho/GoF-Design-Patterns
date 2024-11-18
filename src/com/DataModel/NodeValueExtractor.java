package com.DataModel;

@FunctionalInterface
public interface NodeValueExtractor<T> {
    int extractId(T value);
}