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
	if t != nil {
		for _, child := range t.Children {
			if child.Value == value {
				return child.Link, nil
			}
		}
	}
	return nil, errors.New("Value not found")
}

func (t *Trie) AllPrefixes() ([]string, error) {
	results := []string{}
	if t != nil {
		if t.End == true {
			results = append(results, t.Value)
		}

		for _, val := range t.Children {
			prefixes, err := val.Link.AllPrefixes()
			if err != nil {
				break
			}
			results = append(results, prefixes...)
		}
	} else {
		return results, errors.New("No end found, so no completions available")
	}
	return results, nil
}

func (t *Trie) AutoComplete(prefix string) ([]string, error) {
	trie := t

	for _, val := range prefix {
		link, _ := trie.Find(val)
		trie = link
	}

	results, err := trie.AllPrefixes()

	return results, err
}
