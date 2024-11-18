package com.DataModels;

// This class will represent a node in the LinkedList.
public class LinkedListNode<T> {
    T value;       // The value stored in this node
    LinkedListNode<T> next; // Reference to the next node in the list

    // Constructor to create a new node with a value
    public LinkedListNode(T value) {
        this.value = value;
        this.next = null;
    }

    // Get the value of the node
    public T getValue() {
        return value;
    }

    // Get the next node in the list
    public LinkedListNode<T> getNext() {
        return next;
    }
}
