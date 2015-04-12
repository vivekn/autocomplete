package cx.cad.autocomplete

import org.scalatest.{Matchers, WordSpec}

class TrieTest extends WordSpec with Matchers {

  "The Trie" should {
    "insert a single character string" in {
      val f = trieWithInsertions(List("f"))

      f.trie.children should have size 1
      f.trie.children.head._2.value.get should be("f")
    }

    "insert a two single character strings" in {
      val f = trieWithInsertions(List("f","z"))

      f.trie.children should have size 2
      f.trie.children.head._2.value.get should be ("f")
      f.trie.children.tail.head._2.value.get should be ("z")
    }

    "insert one multicharacter string" in {
      val f = trieWithInsertions(List("what"))

      f.trie.children should have size 1
      f.trie.children.head._2.value should be (None)

      val w: (Char, Trie) = f.trie.children.head
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
      val f = trieWithInsertions(List("what","who"))

      f.trie.children should have size 1

      val w: (Char, Trie) = f.trie.children.head
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
      val f = trieWithInsertions(List("what","fun"))

      f.trie.children.keySet shouldEqual Set('f', 'w')
    }

    "contain a single character string inserted" in {
      val f = trieWithInsertions(List("f"))

      f.trie.contains("f") should be (true)
    }

    "does not contain a string when nothing was inserted" in {
      val f = trieWithInsertions()

      f.trie.contains("a") should be (false)
    }

    "does not contain a single character string that was not inserted" in {
      val f = trieWithInsertions(List("f"))

      f.trie.contains("a") should be (false)
    }

    "contain a single, multicharacter string inserted" in {
      val f = trieWithInsertions(List("what"))

      f.trie.contains("what") should be (true)
    }

    "contain both of two multicharacter strings inserted" in {
      val words = List("who", "what")
      val f = trieWithInsertions(words)

      words.foreach { word => f.trie.contains(word) should be (true) }
    }

    "become a nicely formatted string" in {
      val f = trieWithInsertions(List("hello"))

      f.trie.toString shouldEqual "{h: {e: {l: {l: {o: 'hello'}}}}}"
    }

    "be readable when there are multiple words" in {
      val f = trieWithInsertions(List("who", "where", "how"))

      f.trie.toString shouldEqual "{w: {h: {o: 'who'},{e: {r: {e: 'where'}}}}},{h: {o: {w: 'how'}}}"
    }

    "provide a list of all values inserted" in {
      val words = List("who", "where", "how")
      val f = trieWithInsertions(words)

      f.trie.allPrefixes should contain theSameElementsAs words
    }

    "provide a list of values that begin with a prefix" in {
      val words = List("who", "where", "how")
      val f = trieWithInsertions(words)

      f.trie.autocomplete("wh") should contain theSameElementsAs words.takeWhile(_.startsWith("wh"))
    }

    "have only one entry when multiples of the same word are inserted" in {
      val words = List("who", "where", "who")
      val f = trieWithInsertions(words)

      f.trie.allPrefixes should have size 2
    }
  }

  def trieWithInsertions(toInsert: List[String] = List.empty) = {
    new {
      val trie = toInsert.foldLeft(new Trie)( (trie, word) => {trie.insert(word); trie})
    }
  }

}
