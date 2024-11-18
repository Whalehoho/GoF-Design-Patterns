package com.DataModels;

@FunctionalInterface
public interface NodeProcessor<T> {
    void process(T value, NodeValueExtractor<T> extractor);
}