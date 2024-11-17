package com.dataModel;
import java.io.BufferedWriter;
import java.io.IOException;

@FunctionalInterface
public interface NodeWriter<T> {
    void write(T value, BufferedWriter writer) throws IOException;
}