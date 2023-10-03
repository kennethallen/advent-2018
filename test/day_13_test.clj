(ns day-13-test
  (:require [clojure.test :refer :all]
            [day-13 :refer [part-1]]))

(def sample-0 [
  "|"
  "v"
  "|"
  "|"
  "|"
  "^"
  "|"])
(def sample-1 [
  "/->-\\        "
  "|   |  /----\\"
  "| /-+--+-\\  |"
  "| | |  | v  |"
  "\\-+-/  \\-+--/"
  "  \\------/   "])

(deftest test-part-1
  (is (= [0 3] (part-1 sample-0)))
  (is (= [7 3] (part-1 sample-1)))
  (with-open [rdr (clojure.java.io/reader "test/input/13.txt")]
    (is (= [113 136] (part-1 (line-seq rdr))))))
