(ns day-07-test
  (:require [clojure.test :refer :all]
            [day-07 :refer [part-1 part-2]]))

(def sample ["Step C must be finished before step A can begin."
             "Step C must be finished before step F can begin."
             "Step A must be finished before step B can begin."
             "Step A must be finished before step D can begin."
             "Step B must be finished before step E can begin."
             "Step D must be finished before step E can begin."
             "Step F must be finished before step E can begin."])

(deftest test-part-1
  (is (= "CABDFE" (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/07.txt")]
    (is (= "EPWCFXKISTZVJHDGNABLQYMORU" (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= 15 (part-2 sample 2 0)))
  (with-open [rdr (clojure.java.io/reader "test/input/07.txt")]
    (is (= 952 (part-2 (line-seq rdr) 5 60)))))
