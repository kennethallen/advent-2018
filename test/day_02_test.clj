(ns day-02-test
  (:require [clojure.test :refer :all]
            [day-02 :refer [part-1 part-2]]))

(deftest test-part-1
  (is (= 12 (part-1 ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"])))
  (with-open [rdr (clojure.java.io/reader "test/input/02.txt")]
    (is (= 6944 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= "fgij" (part-2 ["abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"])))
  (with-open [rdr (clojure.java.io/reader "test/input/02.txt")]
    (is (= "srijafjzloguvlntqmphenbkd" (part-2 (line-seq rdr))))))
