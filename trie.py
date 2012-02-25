"""
A fast data structure for searching strings with autocomplete support.
"""

class Trie(object):
    def __init__(self, value=None):
        self.children = {}
        self.value = value
        self.flag = False # Flag to represent that a word ends at this node

    def add(self, char):
        val = self.value + char if self.value else char
        self.children[char] = Trie(val)

    def insert(self, word):
        node = self
        for char in word:
            if char not in node.children:
                node.add(char)
            node = node.children[char]
        node.flag = True

    def find(self, word):
        node = self
        for char in word:
            if char not in node.children:
                return None
            node = node.children[char]
        return node.value

    def all_prefixes(self):
        results = set()
        if self.flag:
            results.add(self.value)
        if not self.children: return results
        return reduce(lambda a, b: a | b, [node.all_prefixes() for node in self.children.values()]) | results

    def autocomplete(self, prefix):
        node = self
        for char in prefix:
            if char not in node.children:
                return set()
            node = node.children[char]
        return node.all_prefixes()

