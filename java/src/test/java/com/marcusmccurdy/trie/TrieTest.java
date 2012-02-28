package com.marcusmccurdy.trie;

import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Marcus McCurdy <marcus.mccurdy@gmail.com>
 */
public class TrieTest {

    private Trie root;

    @Before
    public void setUp() {
        root = new Trie();
    }

    @Test
    public void testAdd() {
        root.add('z');
        assertEquals(1, root.children.size());
        Trie child = root.children.get('z');
        assertNotNull(child);
        assertEquals("z", child.value);
    }

    /**
     * Test of insert method, of class Trie.
     */
    @Test
    public void testInsert() {
        root.insert("test");
        assertEquals(1, root.children.size());
        root.insert("basic");
        assertEquals(2, root.children.size());
        assertEquals(1, root.children.get('b').children.size());
        assertEquals(1, root.children.get('t').children.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNull() {
        root.insert(null);
    }

    @Test
    public void testFind() {
        root.insert("hello");
        root.insert("test");
        root.insert("tea");
        root.insert("bravo");
        assertEquals("", root.find("missing"));
        assertEquals("test", root.find("test"));
    }

    @Test
    public void testAutoComplete() {
        root.insert("test");
        root.insert("tea");
        root.insert("tell");
        root.insert("zebra");
        Collection<String> results = root.autoComplete("te");
        assertTrue(results.contains("test"));
        assertTrue(results.contains("tea"));
        assertTrue(results.contains("tell"));
        assertFalse(results.contains("zebra"));
    }
}
