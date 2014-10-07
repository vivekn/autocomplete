from __future__ import division
import os,sys,inspect
currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
parentdir = os.path.dirname(currentdir)
sys.path.insert(0,parentdir)

from trie import Trie
from sys import argv
from time import clock
from random import choice
from cPickle import dumps

trie = Trie()
arr = []
n = 1000

if len(argv) > 1:
    n = int(argv[1])

words = [line for line in open(os.path.join(currentdir, "wordlist"))]

def insert_trie():
    for word in words:
        trie.insert(word)

def insert_array():
    for word in words:
        arr.append(word)

def trie_autocomplete():
    for i in xrange(n):
        word = choice(words)[:3]
        a = trie.autocomplete(word)

def array_autocomplete():
    for i in xrange(n):
        word = choice(words)[:3]
        a = [w for w in words if w.startswith(word)]

def timefn(f, times, *args):
    for i in xrange(n):
        total = 0
        for i in xrange(times):
            start = clock()
            f(*args)
            end = clock()
            total += end - start
        return total / times

def approx_memory_usage(obj):
    return len(dumps(obj))

def benchmark():
    print "array insertion: %.10f" % timefn(insert_array, 5)
    print "trie insertion: %.10f" % timefn(insert_trie, 5)
    print "array memory: %d" % approx_memory_usage(arr)
    print "trie memory: %d" % approx_memory_usage(trie)
    print "array autocomplete: %.10f" % timefn(array_autocomplete, 1)
    print "trie autocomplete: %.10f" % timefn(trie_autocomplete, 1)

if __name__ == '__main__':
    benchmark()
