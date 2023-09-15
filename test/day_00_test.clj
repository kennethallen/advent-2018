(ns day-00-test
  (:require [clojure.test :refer :all]
            [day-00 :refer :all]))

(deftest test-part-1
  (is (= 3 (part-1 ["+1" "+1" "+1"])))
  (is (= 0 (part-1 ["+1" "+1" "-2"])))
  (is (= -6 (part-1 ["-1" "-2" "-3"])))
  (with-open [rdr (clojure.java.io/reader "test/input/00.txt")]
    (is (= 505 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= 0 (part-2 ["+1" "-1"])))
  (is (= 10 (part-2 ["+3" "+3" "+4" "-2" "-4"])))
  (is (= 5 (part-2 ["-6" "+3" "+8" "+5" "-6"])))
  (is (= 14 (part-2 ["+7" "+7" "-2" "-7" "-4"])))
  (with-open [rdr (clojure.java.io/reader "test/input/00.txt")]
    (is (= 72330 (part-2 (line-seq rdr))))))
