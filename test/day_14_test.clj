(ns day-14-test
  (:require [clojure.test :refer :all]
            [day-14 :refer [digits part-1 part-2]]))

(deftest test-part-1
  (are [q a] (= a (part-1 q))
    9 5158916779
    5 124515891
    18 9251071085
    2018 5941429882
    765071 3171123923))

(deftest test-part-2
  (are [q a] (= a (part-2 q))
    [5 1 5 8 9] 9
    [0 1 2 4 5] 5
    [9 2 5 1 0] 18
    [5 9 4 1 4] 2018
    (digits 765071) 20353748))
