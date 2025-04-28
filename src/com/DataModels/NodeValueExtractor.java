package com.DataModels;

@FunctionalInterface
public interface NodeValueExtractor<T> {
    int extractId(T value);
}