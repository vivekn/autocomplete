package cx.cad.autocomplete

class Trie extends TrieLike {

  var children: Map[Char, Trie] = Map()
  var value: Option[String] = None

  /**
   * Add the current trie's value to its children
   *
   * @param char
   * @return
   */
  protected def add(char: Char): Trie = {
    val trie = new Trie
    children = children + (char -> trie)
    trie
  }

  /**
   * Inserts a word into the trie structure
   *
   * @param word
   * @return
   */
  def insert(word: String) = {
    var current = this
    word.foreach { (char: Char) =>
        current = current.children.getOrElse(char, current.add(char))
    }
    current.value = Some(word)
  }

  /**
   * Determines if the Trie has an exact match for word in it
   *
   * @param word the word to find
   * @return true if the word is in the trie already, false if not
   */
  def contains(word: String): Boolean = {
    var current = Option(this)
    word.foreach { (char: Char) =>
      current = current.get.children.get(char)
    }
    current match {
      case Some(x) if x.value.nonEmpty => true
      case None => false
    }
  }

  /**
   * Provides a list of all values in the trie
   * @return a set of values, the current trie's value followed by its children
   */
  def allValues: Set[String] = {
    value.fold[Set[String]](Set.empty)(Set(_)) ++ children.flatMap{ case(_, trie) => trie.allValues}
  }

  /**
   * Retrieve a set of words that begin with a given string
   * @return all members of the trie that begin with the given string
   */
  def valuesWithPrefix(prefix: String): Set[String] = {
    var current = this
    prefix.foreach { (char: Char) =>
      current = current.children.getOrElse(char, new Trie)
    }
    current.allValues
  }

  override def toString: String = {
    children.map { case (char, trie) =>
      s"{$char: ${trie.toString}${trie.value.map(v => s"'$v'").getOrElse("")}}"
    }.mkString(",")
  }
}
