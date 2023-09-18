(ns day-06-test
  (:require [clojure.test :refer :all]
            [day-06 :refer [part-1 part-2]]))

(def sample ["1, 1" "1, 6" "8, 3" "3, 4" "5, 5" "8, 9"])

(deftest test-part-1
  (is (= 17 (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/06.txt")]
    (is (= 5365 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= 16 (part-2 sample 32)))
  (with-open [rdr (clojure.java.io/reader "test/input/06.txt")]
    (is (= 42513 (part-2 (line-seq rdr) 10000)))))
