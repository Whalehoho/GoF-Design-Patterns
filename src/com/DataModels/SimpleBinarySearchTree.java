package com.DataModels;

import java.io.BufferedWriter;
import java.io.IOException;

public class SimpleBinarySearchTree<T extends Comparable<T>> {
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

    public SimpleBinarySearchTree() {
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

    // Remove a value from the BinarySearchTree
    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node node, T value) {
        if (node == null) {
            return null;  // Value not found
        }

        if (value.compareTo(node.value) < 0) {
            node.left = deleteRecursive(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = deleteRecursive(node.right, value);
        } else {
            // Value found
            if (node.left == null && node.right == null) {
                return null;  // Node has no children
            } else if (node.right == null) {
                return node.left;  // Node has only left child
            } else if (node.left == null) {
                return node.right;  // Node has only right child
            } else {
                // Node has two children
                T minValue = findMinValue(node.right);
                node.value = minValue;
                node.right = deleteRecursive(node.right, minValue);
            }
        }

        return node;
    }

    private T findMinValue(Node node) {
        return node.left == null ? node.value : findMinValue(node.left);
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
    public void inOrderWriterTraversal(BufferedWriter writer, NodeWriter<T> nodeWriter) throws IOException {
        inOrderWriterTraversal(root, writer, nodeWriter);
    }

    private void inOrderWriterTraversal(Node node, BufferedWriter writer, NodeWriter<T> nodeWriter) throws IOException {
        if (node == null) {
            return;
        }

        // Traverse left
        inOrderWriterTraversal(node.left, writer, nodeWriter);

        // Write current node value using the provided NodeWriter
        nodeWriter.write(node.value, writer);

        // Traverse right
        inOrderWriterTraversal(node.right, writer, nodeWriter);
    }

    // In-order traversal that read data from the tree
    public void inOrderTraversal(NodeValueExtractor<T> extractor, NodeProcessor<T> processor) {
        inOrderTraversal(root, extractor, processor);
    }

    private void inOrderTraversal(Node node, NodeValueExtractor<T> extractor, NodeProcessor<T> processor) {
        if (node == null) {
            return;
        }

        // Traverse left
        inOrderTraversal(node.left, extractor, processor);

        // Process current node value using the provided NodeProcessor
        processor.process(node.value, extractor);

        // Traverse right
        inOrderTraversal(node.right, extractor, processor);
    }

    // Additional methods like in-order traversal, delete, etc., can be added here
}