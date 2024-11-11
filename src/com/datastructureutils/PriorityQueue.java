package com.datastructureutils;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PriorityQueue<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    // Add an element to the priority queue
    public void enqueue(T item) {
        heap.add(item);
        bubbleUp(heap.size() - 1);
    }

    // Remove and return the element with the highest priority (smallest element in min-heap)
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T minItem = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heap.set(0, lastItem);
            bubbleDown(0);
        }
        return minItem;
    }

    // Peek at the element with the highest priority without removing it
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap.get(0);
    }

    // Check if the priority queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Helper method to maintain the heap property while enqueuing
    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // Helper method to maintain the heap property while dequeuing
    private void bubbleDown(int index) {
        int size = heap.size();
        int leftChild = 2 * index + 1;
        while (leftChild < size) {
            int smallest = leftChild;
            int rightChild = leftChild + 1;

            if (rightChild < size && heap.get(rightChild).compareTo(heap.get(leftChild)) < 0) {
                smallest = rightChild;
            }

            if (heap.get(index).compareTo(heap.get(smallest)) <= 0) {
                break;
            }

            swap(index, smallest);
            index = smallest;
            leftChild = 2 * index + 1;
        }
    }

    // Helper method to swap elements in the heap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
