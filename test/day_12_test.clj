(ns day-12-test
  (:require [clojure.test :refer :all]
            [day-12 :refer [part-1]]))

(def sample [
  "initial state: #..#.#..##......###...###"
  ""
  "...## => #"
  "..#.. => #"
  ".#... => #"
  ".#.#. => #"
  ".#.## => #"
  ".##.. => #"
  ".#### => #"
  "#.#.# => #"
  "#.### => #"
  "##.#. => #"
  "##.## => #"
  "###.. => #"
  "###.# => #"
  "####. => #"])

(deftest test-part-1
  (is (= 325 (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/12.txt")]
    (is (= 3725 (part-1 (line-seq rdr))))))
