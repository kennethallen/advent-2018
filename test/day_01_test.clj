(ns day-01-test
  (:require [clojure.test :refer :all]
            [day-01 :refer [part-1 part-2]]))

(deftest test-part-1
  (are [a q] (= a (part-1 q))
    3 ["+1" "+1" "+1"]
    0 ["+1" "+1" "-2"]
    -6 ["-1" "-2" "-3"])
  (with-open [rdr (clojure.java.io/reader "test/input/01.txt")]
    (is (= 505 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (are [a q] (= a (part-2 q))
    0 ["+1" "-1"]
    10 ["+3" "+3" "+4" "-2" "-4"]
    5 ["-6" "+3" "+8" "+5" "-6"]
    14 ["+7" "+7" "-2" "-7" "-4"])
  (with-open [rdr (clojure.java.io/reader "test/input/01.txt")]
    (is (= 72330 (part-2 (line-seq rdr))))))
