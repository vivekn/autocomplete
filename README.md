Autocomplete with Trie
======================

[Tries](https://en.wikipedia.org/wiki/Trie) for efficient automatic word completion in several languages.

Implementations
---------------

* C++ - Vivek Narayanan (@vivekn)
* Go - Martijn van Maasakkers (@mvmaasakkers)
* Java - Marcus McCurdy (@volker48)
* Python - Vivek Narayanan (@vivekn)
* Ruby - Colin Dean (@colindean)

Want to add an implementation? Submit a pull request! Please also submit tests and preferably a benchmark, too.

How to Use
----------

See the tests or the benchmarks for examples, but the general gist is something
like this, in Ruby notation:

    trie = Trie.new
    trie.insert("word")
    array_of_words_that_begin_with = trie.autocomplete("wo")


**License** - BSD
