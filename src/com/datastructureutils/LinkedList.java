package com.datastructureutils;

public class LinkedList<T> {

    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add element to the end of the list
    public void add(T value) {
        LinkedListNode<T> newNode = new LinkedListNode<>(value);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Add element to the start of the list
    public void addFirst(T value) {
        LinkedListNode<T> newNode = new LinkedListNode<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // Remove the first element and return its value
    public T removeFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    // Get the first element without removing it
    public T getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.value;
    }

    // Get the size of the list
    public int size() {
        return size;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Print the elements of the list
    public void print() {
        LinkedListNode<T> current = head;
        while (current != null) {
            System.out.print(current.value + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Get the head of the list
    public LinkedListNode<T> getHead() {
        return head;
    }
}
