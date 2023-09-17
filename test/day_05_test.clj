(ns day-05-test
  (:require [clojure.test :refer :all]
            [day-05 :refer [part-1 part-2]]))

(deftest test-part-1
  (are [a q] (= a (part-1 q))
    0 "aA"
    0 "abBA"
    4 "abAB"
    6 "aabAAB"
    10 "dabAcCaCBAcCcaDA")
  (with-open [rdr (clojure.java.io/reader "test/input/05.txt")]
    (is (= 10132 (part-1 (first (line-seq rdr)))))))

(deftest test-part-2
  (is (= 4 (part-2 "dabAcCaCBAcCcaDA")))
  (with-open [rdr (clojure.java.io/reader "test/input/05.txt")]
    (is (= 4572 (part-2 (first (line-seq rdr)))))))
