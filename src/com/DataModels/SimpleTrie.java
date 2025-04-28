package com.DataModels;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrie {
    private class TrieNode {
        SimpleHashMap<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode() {
            children = new SimpleHashMap<>();
            isEndOfWord = false;
        }
    }

    private TrieNode root;

    public SimpleTrie() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            // If the character is not already present, add a new TrieNode
            if (current.children.get(ch) == null) {
                current.children.put(ch, new TrieNode());
            }
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the Trie
    public boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false; // If character path doesn't exist, word is not in Trie
            }
            current = node;
        }
        return current.isEndOfWord; // True if we've reached the end of a valid word
    }

    // Check if the Trie contains any words that start with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false; // If character path doesn't exist, no word with the prefix
            }
            current = node;
        }
        return true; // If we can navigate through all characters, prefix exists
    }

    // Get words in the Trie that start with the given prefix
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        TrieNode current = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return words; // If character path doesn't exist, no word with the prefix
            }
            current = node;
        }
        getWordsWithPrefixRecursive(current, prefix.toLowerCase(), words);
        return words;
    }

    private void getWordsWithPrefixRecursive(TrieNode node, String prefix, List<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            getWordsWithPrefixRecursive(node.children.get(ch), prefix + ch, words);
        }
    }
}