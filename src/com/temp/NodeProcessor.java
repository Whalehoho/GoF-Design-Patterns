package com.DataModel;

@FunctionalInterface
public interface NodeProcessor<T> {
    void process(T value, NodeValueExtractor<T> extractor);
}