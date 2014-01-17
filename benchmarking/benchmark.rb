$in_bench_dir = true
begin
  require "../trie"
rescue LoadError
  $in_bench_dir = false
  require "./trie"
end
require "benchmark"

def prefix
  if !$in_bench_dir
    "benchmarking" + File::SEPARATOR
  else
    ""
  end
end

wl = prefix + "wordlist"
n = ARGV[0].to_i || 100

Benchmark.bm do |x|
  x.report("array insertion") do
    array = []
    File.open(wl).each_line { |line| array << line }
  end

  x.report("trie insertion") do
    trie = Trie.new
    File.open(wl).each_line { |line| trie.insert line}
  end
end

$words = File.open(prefix + "wordlist").readlines
$len = $words.length
$rand = Random.new

$trie = Trie.new
$words.each {|w| $trie.insert w}

def get_word
   $words.at $rand.rand(0..$len)
end


Benchmark.bm do |x|
  x.report("array autocomplete") do
    wl = $words.clone
    n.times do 
      word = get_word
      wl.select {|w| w.start_with? word}
    end
  end

  x.report("trie autocomplete") do
    n.times do
      word = get_word
      $trie.autocomplete word
    end
  end
end
