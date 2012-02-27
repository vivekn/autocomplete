package com.marcusmccurdy.trie;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcus McCurdy <marcus.mccurdy@gmail.com>
 */
public class TrieTest {

    private static final List<String> words = new ArrayList<String>();
    private Trie root;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        final BufferedReader reader =
                new BufferedReader(new InputStreamReader(TrieTest.class.getResourceAsStream("/words.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
    }

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
        String test = "test";
        root.insert(test);
        assertEquals(1, root.children.size());
    }

    /**
     * Test of find method, of class Trie.
     */
    @Test
    public void testFind() {
        root.insert("hello");
        root.insert("test");
        root.insert("tea");
        root.insert("bravo");
        assertEquals("", root.find("missing"));
        assertEquals("test", root.find("test"));
    }

    /**
     * Test of autoComplete method, of class Trie.
     */
    @Test
    public void testAutoComplete() {
        root.insert("test");
        root.insert("tea");
        root.insert("tell");
        Collection<String> results = root.autoComplete("te");
        assertTrue(results.contains("test"));
        assertTrue(results.contains("tea"));
        assertTrue(results.contains("tell"));
    }
}
