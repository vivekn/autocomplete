/*
 * Trie data structure using STL
 *
 * Author : Vivek Narayanan
 */

#include <map>
#include <vector>
#include <string>
#include <sstream>
#include <set>

using namespace std;

string char_to_string(char c) {
    //  Convert char to string
    stringstream ss;
    ss<<c;
    string res;
    ss>>res;
    return res;
}

class Trie {
    public:
        map<char, Trie> children;
        string value;
        set<string> wordlist;

        Trie(string);
        void add(char);
        string find(string);
        void insert(string);
        vector<string> all_prefixes(set<string>);
        vector<string> autocomplete(string);
};

Trie::Trie(string val="") {
    value = val;
}

void Trie::add(char c) {
    if (value == "") 
        children[c] = Trie(char_to_string(c));
    else
        children[c] = Trie(value + c);
}

string Trie::find(string word) {
    Trie * node = this;
    for (int i = 0; i < word.length(); i++) {
        const char c = word[i];
        if (node->children.find(c) == node->children.end())
            return "";
        else 
            node = &node->children[c];
    }
    return node->value;
}

void Trie::insert(string word) {
    Trie * node = this;
    wordlist.insert(word);
    for (int i = 0; i < word.length(); i++) {
        const char c = word[i];
        if (node->children.find(c) == node->children.end())
            node->add(c);
        node = &node->children[c];
    }
}

vector<string> Trie::all_prefixes(set<string> wlist) {
    vector<string> results;
    if (wlist.find(value) != wlist.end())
        results.push_back(value);

    if (children.size()) {
        map<char, Trie>::iterator iter;
        vector<string>::iterator node;
        for(iter = children.begin(); iter != children.end(); iter++) {
            vector<string> nodes = iter->second.all_prefixes(wlist);
            for(node = nodes.begin(); node != nodes.end(); node++) 
                results.push_back(*node);       
        }
    }
    return results;
}

vector<string> Trie::autocomplete(string prefix) {
    Trie * node = this;
    vector<string> results;
    for (int i = 0; i < prefix.length(); i++) {
        const char c = prefix[i];
        if (node->children.find(c) == node->children.end())
            return results;
        else 
            node = &node->children[c];
    }
    return node->all_prefixes(wordlist);
}

