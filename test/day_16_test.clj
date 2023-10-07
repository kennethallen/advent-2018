(ns day-16-test
  (:require [clojure.test :refer :all]
            [day-16 :refer [part-1]]))

(deftest test-part-1
  (with-open [rdr (clojure.java.io/reader "test/input/16.txt")]
    (is (= 529 (part-1 (line-seq rdr))))))
