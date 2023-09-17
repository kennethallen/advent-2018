(ns day-02-test
  (:require [clojure.test :refer :all]
            [day-02 :refer :all]))

(deftest test-part-1
  (is (= 12 (part-1
    ["abcdef"
     "bababc"
     "abbcde"
     "abcccd"
     "aabcdd"
     "abcdee"
     "ababab"])))
  (with-open [rdr (clojure.java.io/reader "test/input/02.txt")]
    (is (= 6944 (part-1 (line-seq rdr))))))
