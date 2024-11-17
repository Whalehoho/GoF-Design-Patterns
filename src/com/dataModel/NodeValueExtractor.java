package com.dataModel;

@FunctionalInterface
public interface NodeValueExtractor<T> {
    int extractId(T value);
}