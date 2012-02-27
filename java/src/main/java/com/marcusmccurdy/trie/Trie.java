package com.marcusmccurdy.trie;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Marcus McCurdy <marcus.mccurdy@gmail.com>
 */
public class Trie {

    protected final Map<Character, Trie> children;
    protected String terminal;
    protected boolean leaf = false;

    public Trie() {
        this(null);
    }

    private Trie(String value) {
        this.terminal = value;
        children = new HashMap<Character, Trie>();
    }

    protected void add(char c) {
        String val;
        if (this.terminal == null) {
            val = Character.toString(c);
        } else {
            val = this.terminal + c;
        }
        children.put(c, new Trie(val));
    }

    public void insert(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot add null to a Trie");
        }
        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.add(c);
            }
            node = node.children.get(c);
        }
        node.leaf = true;
    }

    public String find(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return "";
            }
            node = node.children.get(c);
        }
        return node.terminal;
    }

    public Collection<String> autoComplete(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return Collections.emptyList();
            }
            node = node.children.get(c);
        }
        return node.allPrefixes();
    }

    protected Collection<String> allPrefixes() {
        List<String> results = new ArrayList<String>();
        if (this.leaf) {
            results.add(this.terminal);
        }
        for (Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            Collection<String> childPrefixes = child.allPrefixes();
            results.addAll(childPrefixes);
        }
        return results;
    }
}
