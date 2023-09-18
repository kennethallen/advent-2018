(ns day-09-test
  (:require [clojure.test :refer :all]
            [day-09 :refer [part-1 part-2]]))

(deftest test-part-1
  (are [a b] (= b (part-1 a))
    "9 players; last marble is worth 25 points" 32
    "10 players; last marble is worth 1618 points" 8317
    "13 players; last marble is worth 7999 points" 146373
    "17 players; last marble is worth 1104 points" 2764
    "21 players; last marble is worth 6111 points" 54718
    "30 players; last marble is worth 5807 points" 37305)
  (with-open [rdr (clojure.java.io/reader "test/input/09.txt")]
    (is (= 436720 (part-1 (first (line-seq rdr)))))))

(deftest test-part-2
  (with-open [rdr (clojure.java.io/reader "test/input/09.txt")]
    (is (= 3527845091 (part-2 (first (line-seq rdr)))))))
