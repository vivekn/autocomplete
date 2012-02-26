#
#
require 'test/unit'
require './trie'

begin
  require 'minitest'
  require 'turn'
rescue LoadError
rescue RuntimeError
end

class TrieTest < Test::Unit::TestCase

  def setup
    @t = Trie.new
  end

  def test_interface
    methods = [:add, :insert, :find, :all_prefixes, :autocomplete]
    methods.each do |method|
      assert_respond_to(@t, method)
    end
  end

  def test_initialization
    assert @t.children.empty?
    assert !@t.flag
    assert_nil @t.value
  end
  
  def test_add
    @t.add "f"
    assert !@t.children.empty?
    assert_not_nil @t.children.first[1].value
  end
  
  def test_insert_one
    @t.insert "fun"
    assert !@t.children.empty?
    assert_equal "f", @t.children.first[1].value
    assert_equal "fu", @t.children.first[1].children.first[1].value
    assert_equal "fun", @t.children.first[1].children.first[1].children.first[1].value
    assert !@t.flag
    assert @t.children.first[1].children.first[1].children.first[1].flag
  end
  
  def test_insert_multi_different
    @t.insert "apple"
    @t.insert "orange"
    assert_equal 2, @t.children.size
    @t.insert "banana"
    assert_equal 3, @t.children.size
  end
  
  def test_insert_multi_same
    @t.insert "fun"
    @t.insert "fan"
    assert_equal 1, @t.children.size
    assert_equal 2, @t.children.first[1].children.size
  end
  
  def test_all_prefixes
    words = %w(fun fan apple orange banana)
    words.each do |w|
      @t.insert w
    end
    
    ap = @t.all_prefixes
    
    assert_equal words.size, ap.size
    
    words.each do |w|
      assert ap.include? w
    end
  end

  def test_autocomplete
    @t.insert "fun"
    @t.insert "fan"
    res = @t.autocomplete "f"
    assert_equal 2, res.size
    assert res.include? "fun"
    assert res.include? "fan"
  end

end
