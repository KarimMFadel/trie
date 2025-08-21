package com.tornado.trie;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Trie (Prefix Tree) data structure implementation.
 * Supports storing strings with optional associated values.
 *
 * Operations supported:
 * - create: Initialize a new Trie
 * - insert: Add a word (with optional value) to the Trie
 * - lookup: Check if a word exists and retrieve its value
 * - update: Modify the value associated with an existing word
 * - delete: Remove a word from the Trie
 */
@Slf4j
public class TrieDS {
    private TrieNode root;

    /**
     * Creates a new empty Trie.
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie without an associated value.
     *
     * @param word the word to insert
     */
    public void insert(@NonNull String word) {
        insert(word, null);
    }

    /**
     * Inserts a word into the Trie with an associated value.
     *
     * @param word the word to insert
     * @param value the value to associate with the word
     */
    public void insert(@NonNull String word, Object value) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        log.debug("Inserting word: {} with value: {}", word, value);
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch)) {
                current.addChild(ch, new TrieNode());
            }
            current = current.getChild(ch);
        }

        current.setEndOfWord(true);
        current.setValue(value);
    }

    /**
     * Looks up a word in the Trie.
     *
     * @param word the word to look up
     * @return true if the word exists in the Trie, false otherwise
     */
    public boolean lookup(@NonNull String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        log.debug("Looking up word: {}", word);
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord();
    }

    /**
     * Gets the value associated with a word in the Trie.
     *
     * @param word the word to look up
     * @return the value associated with the word, or null if word doesn't exist
     */
    public Object getValue(@NonNull String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        log.debug("Getting value for word: {}", word);
        TrieNode node = findNode(word);
        if (node != null && node.isEndOfWord()) {
            return node.getValue();
        }
        return null;
    }

    /**
     * Updates the value associated with an existing word in the Trie.
     *
     * @param word the word to update
     * @param newValue the new value to associate with the word
     * @return true if the word was found and updated, false otherwise
     */
    public boolean update(@NonNull String word, Object newValue) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        log.debug("Updating word: {} with new value: {}", word, newValue);
        TrieNode node = findNode(word);
        if (node != null && node.isEndOfWord()) {
            node.setValue(newValue);
            return true;
        }
        return false;
    }

    /**
     * Deletes a word from the Trie.
     *
     * @param word the word to delete
     * @return true if the word was found and deleted, false otherwise
     */
    public boolean delete(@NonNull String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        log.debug("Deleting word: {}", word);
        return deleteHelper(root, word, 0);
    }

    /**
     * Helper method for deleting a word from the Trie.
     * Uses recursion to traverse and clean up unnecessary nodes.
     */
    private boolean deleteHelper(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // We've reached the end of the word
            if (!current.isEndOfWord()) {
                return false; // Word doesn't exist
            }

            current.setEndOfWord(false);
            current.setValue(null);

            // If current has no children, it can be deleted
            return !current.hasChildren();
        }

        char ch = word.charAt(index);
        TrieNode node = current.getChild(ch);

        if (node == null) {
            return false; // Word doesn't exist
        }

        boolean shouldDeleteChild = deleteHelper(node, word, index + 1);

        if (shouldDeleteChild) {
            current.removeChild(ch);

            // Return true if current has no children and is not end of another word
            return !current.isEndOfWord() && !current.hasChildren();
        }

        return false;
    }

    /**
     * Finds the TrieNode corresponding to the given word.
     *
     * @param word the word to find
     * @return the TrieNode if found, null otherwise
     */
    private TrieNode findNode(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch)) {
                return null;
            }
            current = current.getChild(ch);
        }

        return current;
    }

    /**
     * Checks if the Trie is empty.
     *
     * @return true if the Trie contains no words, false otherwise
     */
    public boolean isEmpty() {
        return !root.hasChildren();
    }

    /**
     * Gets all words in the Trie that start with the given prefix.
     *
     * @param prefix the prefix to search for
     * @return a list of words that start with the prefix
     */
    public List<String> getWordsWithPrefix(@NonNull String prefix) {
        log.debug("Getting words with prefix: {}", prefix);
        List<String> result = new ArrayList<>();
        TrieNode prefixNode = findNode(prefix);

        if (prefixNode != null) {
            collectWords(prefixNode, prefix, result);
        }

        return result;
    }

    /**
     * Helper method to collect all words starting from a given node.
     */
    private void collectWords(TrieNode node, String currentWord, List<String> result) {
        if (node.isEndOfWord()) {
            result.add(currentWord);
        }

        for (char ch : node.getChildren().keySet()) {
            collectWords(node.getChild(ch), currentWord + ch, result);
        }
    }
}