package trie

import (
	"strings"
	"testing"
)

var wordlist string = "Lorem ipsum dolor sit amet consectetur adipiscing elit Integer at lorem arcu Ut accumsan at urna vitae commodo Morbi vitae aliquam sem Aliquam augue arcu pharetra vel arcu quis mollis sagittis elit Nunc id erat varius tortor luctus lobortis Sed viverra sapien sed malesuada mollis metus eros luctus arcu id sagittis arcu tellus quis mauris Vestibulum sit amet nisl ipsum Quisque vulputate suscipit aliquam Mauris placerat nunc et metus ullamcorper tempus Ut non nisl condimentum aliquam tortor volutpat suscipit nisi Pellentesque rhoncus odio purus nec accumsan quam feugiat vel Pellentesque pharetra diam eget dignissim pharetra neque diam sodales sapien sit amet bibendum urna arcu ut ante Nullam accumsan dolor ut tincidunt euismod Integer ut turpis a erat convallis cursus Maecenas imperdiet porttitor iaculis Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Ut a metus quis ipsum dictum congue Pellentesque rutrum urna non dictum interdum Phasellus rutrum orci orci vitae varius massa placerat id Curabitur facilisis elit sit amet consequat sollicitudin purus massa gravida lectus rutrum pretium arcu erat sit amet mauris Proin faucibus urna dui Mauris ac ornare mi sit amet porta metus Quisque tristique fermentum magna in faucibus Cras ullamcorper tortor sit amet nulla lobortis eget hendrerit eros pretium Aenean consectetur eu metus non dignissim Vestibulum mollis orci vitae ultrices volutpat Sed malesuada mi nibh ac ullamcorper lectus venenatis vitae Praesent varius ipsum faucibus sapien vestibulum ac porttitor est rutrum Vestibulum vitae sem non erat facilisis mattis et sit amet elit In luctus accumsan odio non venenatis Donec sed bibendum velit Ut vitae metus enim Etiam sollicitudin lorem eget nisi consectetur condimentum Donec nec vulputate mauris Phasellus vestibulum felis lacus ut tincidunt nunc ornare et Proin id sagittis purus Aliquam erat volutpat Sed vel arcu non magna hendrerit aliquet ut id quam Pellentesque elementum turpis id pharetra egestas Nunc nec sem dictum tincidunt ipsum ut tempus velit Suspendisse eu venenatis nibh Quisque blandit felis nibh condimentum tincidunt est gravida sed Donec eget tincidunt sem Nullam et euismod mi Integer arcu tortor porttitor consequat libero nec sodales fringilla enim Donec fermentum vitae erat vitae congue Duis non nisl porttitor vulputate massa sit amet ornare enim Etiam sollicitudin ligula sit amet nisi dapibus ornare Phasellus sapien justo aliquet ac purus porta malesuada eleifend urna Ut accumsan quis leo at egestas Nullam malesuada est in est pharetra posuere Phasellus hendrerit aliquet odio et feugiat Praesent egestas sodales aliquet Mauris scelerisque rhoncus enim sit amet viverra justo rhoncus at Donec sit amet malesuada quam Fusce cursus iaculis purus eu sodales Maecenas consectetur libero at eros euismod dignissim Aenean interdum dapibus velit vitae aliquet justo bibendum non Mauris condimentum vitae lacus at pellentesque Nulla eget lacus id urna scelerisque vulputate In gravida id nisi a bibendum Cras in semper nisl Suspendisse adipiscing odio eget diam consectetur pretium Etiam lectus ligula viverra sed lectus nec ornare sagittis libero Pellentesque porttitor rutrum arcu a posuere libero sagittis eget Pellentesque tincidunt a massa vitae blandit Sed condimentum enim vel vestibulum placerat est"
var words []string

func init() {
	words = strings.Split(strings.ToLower(wordlist), " ")

}

func TestInsert(t *testing.T) {
	word := "sword"

	trie := NewTrie()
	trie.Insert(word)

	if len(trie.Children) != 1 || trie.Children[0].Link.Value != word[0:1] {
		t.Errorf("Word not inserted correctly, got %s but expected %s", trie.Children[0].Link.Value, word[0:1])
	}
}

func TestFind(t *testing.T) {
	var character rune = 99

	trie := NewTrie()
	trie.Insert("character")

	_, err := trie.Find(character)

	if err != nil {
		t.Errorf("trie.Find(%s) returned no result", string(character))
	}
}

func TestFindFail(t *testing.T) {
	var character rune = 99

	trie := NewTrie()
	trie.Insert("fail")

	_, err := trie.Find(character)

	if err == nil {
		t.Errorf("trie.Find(%s) returned a result", string(character))
	}
}

func TestAutocomplete(t *testing.T) {
	var words []string = []string{"list", "item", "thing", "lion"}
	var result []string = []string{"list", "lion"}
	var search string = "li"
	trie := NewTrie()
	for _, val := range words {
		trie.Insert(val)
	}

	results := trie.AutoComplete(search)

	if len(results) != len(result) || results[0] != result[0] || results[1] != result[1] {
		t.Errorf("trie.AutoComplete(%s) returned %d: %v (should be %d results: %v)", string(search), len(results), results, len(result), result)
	}
}

func BenchmarkInsert(b *testing.B) {
	trie := NewTrie()
	for i := 0; i < b.N; i++ {
		trie.Insert("word")
	}
}

func BenchmarkAutocomplete(b *testing.B) {
	trie := NewTrie()
	for _, val := range words {
		trie.Insert(val)
	}

	for i := 0; i < b.N; i++ {
		trie.AutoComplete("lo")
	}
}
