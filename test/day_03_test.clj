(ns day-03-test
  (:require [clojure.test :refer :all]
            [day-03 :refer [part-1 part-2]]))

(def sample ["#1 @ 1,3: 4x4" "#2 @ 3,1: 4x4" "#3 @ 5,5: 2x2"])

(deftest test-part-1
  (is (= 4 (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/03.txt")]
    (is (= 108961 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= 3 (part-2 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/03.txt")]
    (is (= 681 (part-2 (line-seq rdr))))))
