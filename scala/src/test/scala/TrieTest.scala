package cx.cad.autocomplete

import org.scalatest.{Matchers, WordSpec}

class TrieTest extends WordSpec with Matchers {

  "The Trie" should {
    "insert a single character string" in {
      val t = new Trie()
      t.insert("f")
      t.children.size should be(1)
      t.children.head._2.value.get should be("f")
    }

    "insert a two single character strings" in {
      val t = new Trie()
      t.insert("f")
      t.insert("z")

      t.children.size should be(2)
      t.children.head._2.value.get should be("f")
      t.children.tail.head._2.value.get should be("z")
    }

    "insert one multicharacter string" in {
      val trie = new Trie()

      trie.insert("what")

      trie.children.size should be(1)
      trie.children.head._2.value should be(None) // because it's not a leaf

      val w: (Char, Trie) = trie.children.head
      val h: (Char, Trie) = w._2.children.head
      val a: (Char, Trie) = h._2.children.head
      val t: (Char, Trie) = a._2.children.head

      w._1 should be('w')
      w._2.value should be(None)
      h._1 should be('h')
      h._2.value should be(None)
      a._1 should be('a')
      a._2.value should be(None)
      t._1 should be('t')
      t._2.value should be(Some("what"))
    }

    "insert two multicharacter strings what begin with the same prefix" in {
      val trie = new Trie()

      trie.insert("what")
      trie.insert("who")

      trie.children.size should be(1)

      val w: (Char, Trie) = trie.children.head
      val h: (Char, Trie) = w._2.children.head
      val a: (Char, Trie) = h._2.children.head
      val t: (Char, Trie) = a._2.children.head

      val o: (Char, Trie) = ('o', h._2.children.get('o').get)

      w._1 should be('w')
      w._2.value should be(None)
      h._1 should be('h')
      h._2.value should be(None)
      a._1 should be('a')
      a._2.value should be(None)
      t._1 should be('t')
      t._2.value should be(Some("what"))
      o._2.value should be(Some("who"))
    }

    "insert two multicharacter strings that do not have the same prefix" in {
      val trie = new Trie()

      trie.insert("what")
      trie.insert("fun")

      trie.children.size should be(2)
      trie.children.keySet shouldEqual(Set('f', 'w'))
    }

    "contain a single character string inserted" in {
      val trie = new Trie()
      trie.insert("f")
      trie.contains("f") should be(true)
    }

    "does not contain a string when nothing was inserted" in {
      val trie = new Trie()
      trie.contains("a") should be(false)
    }

    "does not contain a single character string that was not inserted" in {
      val trie = new Trie()
      trie.insert("f")
      trie.contains("a") should be(false)
    }

    "contain a single, multicharacter string inserted" in {
      val trie = new Trie()
      trie.insert("what")
      trie.contains("what") should be(true)
    }

    "contain both of two multicharacter strings inserted" in {
      val trie = new Trie()
      trie.insert("what")
      trie.insert("who")
      trie.contains("what") should be(true)
      trie.contains("who") should be(true)
    }

    "become a nicely formatted string" in {
      val trie = new Trie
      trie.insert("hello")

      println(trie.toString)
      trie.toString shouldEqual("{h: {e: {l: {l: {o: 'hello'}}}}}")
    }

    "be readable when there are multiple words" in {
      val trie = new Trie
      trie.insert("who")
      trie.insert("where")
      trie.insert("how")

      println(trie.toString)

      trie.toString shouldEqual("{w: {h: {o: 'who'},{e: {r: {e: 'where'}}}}},{h: {o: {w: 'how'}}}")
    }
  }

}
