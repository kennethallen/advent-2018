(ns day-11-test
  (:require [clojure.test :refer :all]
            [day-11 :refer [power part-1]]))

(deftest test-power
  (are [a serial x y] (= a (power serial x y))
    -5 57 122 79
    0 39 217 196
    4 71 101 153))

(deftest test-part-1
  (are [a q] (= a (part-1 q))
    [33 45] 18
    [21 61] 42
    [235 18] 5153))
