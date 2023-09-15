(ns day-00-test
  (:require [clojure.test :refer :all]
            [day-00 :refer :all]))

(deftest test-part-1
  (is (= 3 (part-1 ["+1" "+1" "+1"])))
  (is (= 0 (part-1 ["+1" "+1" "-2"])))
  (is (= -6 (part-1 ["-1" "-2" "-3"])))
  (with-open [rdr (clojure.java.io/reader "test/input/00.txt")]
    (is (= 505 (part-1 (line-seq rdr))))))
