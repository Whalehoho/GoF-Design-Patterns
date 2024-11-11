package com.datastructureutils;

import java.io.BufferedWriter;
import java.io.IOException;

public class BinarySearchTree<T extends Comparable<T>> {
    private class Node {
        T value;
        Node left;
        Node right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    // Insert a value into the BinarySearchTree
    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, T value) {
        if (node == null) {
            return new Node(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insertRecursive(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRecursive(node.right, value);
        }
        // If value is equal, we skip insertion (or you could handle duplicates as needed)

        return node;
    }

    // Search for a value by ID (int) in the BinarySearchTree
    public T search(int id, NodeValueExtractor<T> extractor) {
        return searchRecursive(root, id, extractor);
    }

    private T searchRecursive(Node node, int id, NodeValueExtractor<T> extractor) {
        if (node == null) {
            return null;  // Not found
        }

        int nodeId = extractor.extractId(node.value);
        if (nodeId == id) {
            return node.value;  // Return the value found
        }

        if (id < nodeId) {
            return searchRecursive(node.left, id, extractor);  // Search in left subtree
        } else {
            return searchRecursive(node.right, id, extractor);  // Search in right subtree
        }
    }

    // Get root node of the BinarySearchTree
    public Node getRoot() {
        return root;
    }

    // In-order traversal that writes data to the file
    public void inOrderTraversal(BufferedWriter writer, NodeWriter<T> nodeWriter) throws IOException {
        inOrderTraversal(root, writer, nodeWriter);
    }

    private void inOrderTraversal(Node node, BufferedWriter writer, NodeWriter<T> nodeWriter) throws IOException {
        if (node == null) {
            return;
        }

        // Traverse left
        inOrderTraversal(node.left, writer, nodeWriter);

        // Write current node value using the provided NodeWriter
        nodeWriter.write(node.value, writer);

        // Traverse right
        inOrderTraversal(node.right, writer, nodeWriter);
    }

    // Additional methods like in-order traversal, delete, etc., can be added here
}