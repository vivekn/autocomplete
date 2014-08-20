package trie

import (
	"errors"
)

type Trie struct {
	Value    string
	Children []Child
	End      bool
}

type Child struct {
	Value rune
	Link  *Trie
}

func NewTrie() *Trie {
	return &Trie{}
}

func NewChild(value rune) Child {
	child := Child{}
	child.Value = value
	return child
}

func (t *Trie) Add(value rune) *Trie {
	link := NewTrie()
	link.Value = t.Value + string(value)
	child := NewChild(value)
	child.Link = link

	t.Children = append(t.Children, child)

	return link
}

func (t *Trie) Insert(value string) {
	trie := t

	for i, val := range value {
		link, err := trie.Find(val)

		if i < len(value) {
			if err != nil {
				link = trie.Add(val)
			}

			trie = link
		}

	}

	trie.End = true
}

func (t *Trie) Find(value rune) (*Trie, error) {
	for _, child := range t.Children {
		if child.Value == value {
			return child.Link, nil
		}
	}
	return nil, errors.New("Value not found")
}

func (t *Trie) AllPrefixes() []string {
	results := []string{}

	if t.End == true {
		results = append(results, t.Value)
	}

	for _, val := range t.Children {
		prefixes := val.Link.AllPrefixes()
		results = append(results, prefixes...)
	}

	return results
}

func (t *Trie) AutoComplete(prefix string) []string {
	trie := t

	for _, val := range prefix {
		link, _ := trie.Find(val)
		trie = link
	}

	results := trie.AllPrefixes()

	return results
}
