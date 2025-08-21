package com.tornado.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in the Trie data structure.
 * Each node contains:
 * - A map of child nodes (character -> TrieNode)
 * - A flag indicating if this node represents the end of a word
 * - An optional value associated with the word (for key-value pairs)
 */
public class TrieNode {
    private Map<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;
    private Object value;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
        this.value = null;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Checks if this node has a child for the given character.
     *
     * @param ch the character to check
     * @return true if child exists, false otherwise
     */
    public boolean hasChild(char ch) {
        return children.containsKey(ch);
    }

    /**
     * Gets the child node for the given character.
     *
     * @param ch the character
     * @return the child TrieNode or null if not found
     */
    public TrieNode getChild(char ch) {
        return children.get(ch);
    }

    /**
     * Adds a child node for the given character.
     *
     * @param ch the character
     * @param node the TrieNode to add as child
     */
    public void addChild(char ch, TrieNode node) {
        children.put(ch, node);
    }

    /**
     * Removes the child node for the given character.
     *
     * @param ch the character
     */
    public void removeChild(char ch) {
        children.remove(ch);
    }

    /**
     * Checks if this node has any children.
     *
     * @return true if node has children, false otherwise
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
}