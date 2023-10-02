(ns day-12-test
  (:require [clojure.test :refer :all]
            [day-12 :refer [part-1 part-2]]))

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

(deftest test-part-2
  (with-open [rdr (clojure.java.io/reader "test/input/12.txt")]
    (is (= 3100000000293 (part-2 (line-seq rdr))))))
