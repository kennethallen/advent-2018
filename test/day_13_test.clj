(ns day-13-test
  (:require [clojure.test :refer :all]
            [day-13 :refer [part-1 part-2]]))

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
(def sample-2 [
  "/>-<\\  "
  "|   |  "
  "| /<+-\\"
  "| | | v"
  "\\>+</ |"
  "  |   ^"
  "  \\<->/"])

(deftest test-part-1
  (is (= [0 3] (part-1 sample-0)))
  (is (= [7 3] (part-1 sample-1)))
  (with-open [rdr (clojure.java.io/reader "test/input/13.txt")]
    (is (= [113 136] (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= [6 4] (part-2 sample-2)))
  (with-open [rdr (clojure.java.io/reader "test/input/13.txt")]
    (is (= [114 136] (part-2 (line-seq rdr))))))
