require 'set'
class Trie

  attr_accessor :children, :value, :flag

  def initialize value=nil
    @children = {}
    @value = value
    @flag = false #flag to represent that a word ends at this node
  end
  
  def add char
    val = value ? value + char : char
    children[char] = Trie.new val
  end
  
  def insert word
    node = self
    word.each_char do |char|
      node.add char if not node.children.has_key? char
      node = node.children[char]
    end
    node.flag = true
  end
  
  def find word
    node = self
    word.each_char do |char|
      return nil if not node.children.has_key? char
      node = node.children[char]
    end
    return node.value
  end
  
  def all_prefixes
    results = Set.new
    results.add value if flag
    return results if children.empty?
    
    ap = children.values.collect {|node| node.all_prefixes}
    
    reduced = ap.reduce {|a,b| a.merge b}
    reduced or results
  end
  
  def autocomplete prefix
    node = self
    prefix.each_char do |char|
      return Set.new if not node.children.has_key? char
      node = node.children[char]
    end
    return node.all_prefixes
  end
end
