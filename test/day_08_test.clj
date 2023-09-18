(ns day-08-test
  (:require [clojure.test :refer :all]
            [day-08 :refer [part-1 part-2]]))

(def sample "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")

(deftest test-part-1
  (is (= 138 (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/08.txt")]
    (is (= 48260 (part-1 (first (line-seq rdr)))))))

(deftest test-part-2
  (is (= 66 (part-2 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/08.txt")]
    (is (= 25981 (part-2 (first (line-seq rdr)))))))
