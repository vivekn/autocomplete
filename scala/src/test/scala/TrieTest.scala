package cx.cad.autocomplete

import org.scalatest.{Matchers, WordSpec}

class TrieTest extends WordSpec with Matchers {

  "The Trie" should {
    "insert a single character string" in {
      val trie = trieWithInsertions(List("f")).theTrie

      trie.children should have size 1
      trie.children.head._2.value.get should be("f")
    }

    "insert a two single character strings" in {
      val trie = trieWithInsertions(List("f","z")).theTrie

      trie.children should have size 2
      trie.children.head._2.value.get should be ("f")
      trie.children.tail.head._2.value.get should be ("z")
    }

    "insert one multicharacter string" in {
      val trie = trieWithInsertions(List("what")).theTrie

      trie.children should have size 1
      trie.children.head._2.value should be (None)

      val w: (Char, Trie) = trie.children.head
      val h: (Char, Trie) = w._2.children.head
      val a: (Char, Trie) = h._2.children.head
      val t: (Char, Trie) = a._2.children.head

      w._1 should be ('w')
      w._2.value should be (None)
      h._1 should be ('h')
      h._2.value should be (None)
      a._1 should be ('a')
      a._2.value should be (None)
      t._1 should be ('t')
      t._2.value should be (Some("what"))
    }

    "insert two multicharacter strings what begin with the same prefix" in {
      val trie = trieWithInsertions(List("what","who")).theTrie

      trie.children should have size 1

      val w: (Char, Trie) = trie.children.head
      val h: (Char, Trie) = w._2.children.head
      val a: (Char, Trie) = h._2.children.head
      val t: (Char, Trie) = a._2.children.head

      val o: (Char, Trie) = ('o', h._2.children.get('o').get)

      w._1 should be ('w')
      w._2.value should be(None)
      h._1 should be ('h')
      h._2.value should be(None)
      a._1 should be ('a')
      a._2.value should be(None)
      t._1 should be ('t')
      t._2.value should be (Some("what"))
      o._2.value should be (Some("who"))
    }

    "insert two multicharacter strings that do not have the same prefix" in {
      val trie = trieWithInsertions(List("what","fun")).theTrie

      trie.children.keySet shouldEqual Set('f', 'w')
    }

    "contain a single character string inserted" in {
      val trie = trieWithInsertions(List("f")).theTrie

      trie.contains("f") should be (true)
    }

    "does not contain a string when nothing was inserted" in {
      val trie = trieWithInsertions().theTrie

      trie.contains("a") should be (false)
    }

    "does not contain a single character string that was not inserted" in {
      val trie = trieWithInsertions(List("f")).theTrie

      trie.contains("a") should be (false)
    }

    "contain a single, multicharacter string inserted" in {
      val trie = trieWithInsertions(List("what")).theTrie

      trie.contains("what") should be (true)
    }

    "contain both of two multicharacter strings inserted" in {
      val words = List("who", "what")
      val trie = trieWithInsertions(words).theTrie

      words.foreach { word => trie.contains(word) should be (true) }
    }

    "become a nicely formatted string" in {
      val trie = trieWithInsertions(List("hello")).theTrie

      trie.toString shouldEqual "{h: {e: {l: {l: {o: 'hello'}}}}}"
    }

    "be readable when there are multiple words" in {
      val trie = trieWithInsertions(List("who", "where", "how")).theTrie

      trie.toString shouldEqual "{w: {h: {o: 'who'},{e: {r: {e: 'where'}}}}},{h: {o: {w: 'how'}}}"
    }

    "provide a list of all values inserted" in {
      val words = List("who", "where", "how")
      val trie = trieWithInsertions(words).theTrie

      trie.allValues should contain theSameElementsAs words
    }

    "provide a list of values that begin with a prefix" in {
      val words = List("who", "where", "how")
      val trie = trieWithInsertions(words).theTrie

      trie.valuesWithPrefix("wh") should contain theSameElementsAs words.takeWhile(_.startsWith("wh"))
    }

    "have only one entry when multiples of the same word are inserted" in {
      val words = List("who", "where", "who")
      val trie = trieWithInsertions(words).theTrie

      trie.allValues should have size 2
    }
  }

  def trieWithInsertions(toInsert: List[String] = List.empty) = {
    new {
      val theTrie = toInsert.foldLeft(new Trie)( (trie, word) => {trie.insert(word); trie})
    }
  }

}
