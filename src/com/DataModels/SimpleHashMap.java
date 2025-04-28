package com.DataModels;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class SimpleHashMap<K, V> {
    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry>[] table;
    private static final int SIZE = 16;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        table = new LinkedList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int index = Math.abs(key.hashCode()) % SIZE;
        LinkedList<Entry> bucket = table[index];

        for (int i = 0; i < bucket.size(); i++) {
            Entry entry = bucket.removeFirst();
            if (entry.key.equals(key)) {
                entry.value = value;
                bucket.add(entry); // Re-add the entry to preserve order
                return;
            }
            bucket.add(entry); // Re-add entry if it's not the one we're looking to update
        }

        bucket.add(new Entry(key, value)); // Add new entry if key was not found
    }

    public V get(K key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        LinkedList<Entry> bucket = table[index];

        for (int i = 0; i < bucket.size(); i++) {
            Entry entry = bucket.removeFirst();
            if (entry.key.equals(key)) {
                bucket.add(entry); // Re-add entry to preserve order
                return entry.value;
            }
            bucket.add(entry); // Re-add entry if it's not the one we're looking for
        }

        return null; // Return null if key is not found
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (LinkedList<Entry> bucket : table) {
            for (Entry entry : bucket) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    public int getOrDefault(K key, int defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return (int) value;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
}