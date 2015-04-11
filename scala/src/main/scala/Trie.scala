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
  def add(char: Char): Trie = {
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
      if(children.contains(char)){
        current = children(char)
      } else {
        current = current.add(char)
      }
    }
    current.value = Some(word)

  }

  /**
   * Determines if the Trie has an exact match for word in it
   *
   * @param word the word to find
   * @return true if the word is in the trie already, false if not
   */
  def contains(word: String): Boolean = ???


  def allPrefixes: Set[String] = ???

  /**
   * Retrieve a set of words that begin with a given string
   * @return all members of the trie that begin with the given string
   */
  def autocomplete(beginningWith: String): Set[String] = ???
}
