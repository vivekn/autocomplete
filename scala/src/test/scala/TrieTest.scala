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
  }

}
