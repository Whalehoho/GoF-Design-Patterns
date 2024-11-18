package com.DataModel;

public class SimpleStack<T> {
    private SimpleLinkedList<T> list;

    public SimpleStack() {
        list = new SimpleLinkedList<>();
    }

    public void push(T item) {
        list.addFirst(item);
    }

    public T pop() {
        return list.removeFirst();
    }

    public T peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
