(ns day-04-test
  (:require [clojure.test :refer :all]
            [day-04 :refer [part-1 part-2]]))

(def sample [
  "[1518-11-05 00:03] Guard #99 begins shift"
  "[1518-11-03 00:24] falls asleep"
  "[1518-11-01 00:25] wakes up"
  "[1518-11-03 00:05] Guard #10 begins shift"
  "[1518-11-01 00:30] falls asleep"
  "[1518-11-02 00:40] falls asleep"
  "[1518-11-05 00:45] falls asleep"
  "[1518-11-01 00:00] Guard #10 begins shift"
  "[1518-11-04 00:46] wakes up"
  "[1518-11-01 00:05] falls asleep"
  "[1518-11-01 00:55] wakes up"
  "[1518-11-04 00:36] falls asleep"
  "[1518-11-04 00:02] Guard #99 begins shift"
  "[1518-11-05 00:55] wakes up"
  "[1518-11-01 23:58] Guard #99 begins shift"
  "[1518-11-03 00:29] wakes up"
  "[1518-11-02 00:50] wakes up"])

(deftest test-part-1
  (is (= 240 (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/04.txt")]
    (is (= 71748 (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= (* 99 45) (part-2 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/04.txt")]
    (is (= 106850 (part-2 (line-seq rdr))))))
