package com.marcusmccurdy.trie;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Marcus McCurdy <marcus.mccurdy@gmail.com>
 */
public class Trie {

    private final Set<String> wordSet;
    private final Map<Character, Trie> children;
    private String value;

    public Trie() {
        this(null);
    }

    private Trie(String value) {
        this.value = value;
        wordSet = new HashSet<String>();
        children = new HashMap<Character, Trie>();
    }

    public void add(char c) {
        if (value != null) {
            value += c;
        } else {
            value = Character.toString(c);
        }
        children.put(c, new Trie(value));
    }

    public void insert(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot add null to a Trie");
        }
        Trie node = this;
        wordSet.add(word);
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.add(c);
            }
            node = node.children.get(c);
        }
    }

    public String find(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node.value;
    }

    public Collection<String> autoComplete(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return Collections.emptyList();
            }
            node = node.children.get(c);
        }
        return node.allPrefixes(wordSet);
    }

    private Collection<String> allPrefixes(Set<String> wordSet) {
        List<String> results = new ArrayList<String>();
        if (wordSet.contains(value)) {
            results.add(value);
        }
        for (Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            Collection<String> childPrefixes = child.allPrefixes(wordSet);
            results.addAll(childPrefixes);
        }
        return results;
    }
}
