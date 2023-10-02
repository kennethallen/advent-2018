(ns day-11-test
  (:require [clojure.test :refer :all]
            [day-11 :refer [power part-1 part-2]]))

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

(deftest test-part-2
  (are [a q] (= a (part-2 q))
    [90 269 16] 18
    [232 251 12] 42
    [236 227 12] 5153))
