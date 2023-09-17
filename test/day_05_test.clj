(ns day-05-test
  (:require [clojure.test :refer :all]
            [day-05 :refer [part-1]]))

(deftest test-part-1
  (is (= 0 (part-1 "aA")))
  (is (= 0 (part-1 "abBA")))
  (is (= 4 (part-1 "abAB")))
  (is (= 6 (part-1 "aabAAB")))
  (with-open [rdr (clojure.java.io/reader "test/input/05.txt")]
    (is (= 10132 (part-1 (first (line-seq rdr)))))))
