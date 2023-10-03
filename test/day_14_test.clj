(ns day-14-test
  (:require [clojure.test :refer :all]
            [day-14 :refer [part-1]]))

(deftest test-part-1
  (are [q a] (= a (part-1 q))
    9 5158916779
    5 124515891
    18 9251071085
    2018 5941429882
    765071 3171123923))
