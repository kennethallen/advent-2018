(ns day-15-test
  (:require [clojure.test :refer :all]
            [day-15 :refer [adjs greedy-search part-1]]))

(deftest test-djikstra
  (is (=
    [[1 1] {
      [0 0] [0 nil]
      [0 1] [1 [0 0]]
      [1 0] [1 [0 0]]
      [1 1] [2 [0 1]]}]
    (greedy-search
      [0 0]
      {
        [0 0] [[0 1] [1 0]]
        [0 1] [[0 0] [1 1]]
        [1 0] [[0 0] [1 1]]
        [1 1] [[0 1] [1 0]]}
      #(= [1 1] %))))
  (with-open [rdr (clojure.java.io/reader "test/input/15f.txt")]
    (let [lines (vec (line-seq rdr))
          dests (set
            (for [[y row] (map-indexed vector lines)
                  [x cell] (map-indexed vector row)
                  :when (= \E cell)
                  adj (adjs [y x])
                  :when (= \. (get-in lines adj))]
              adj))]
      (is (=
        [
          [3 2]
          {
            [1 6] [8 [2 6]],
            [2 5] [8 [2 6]],
            [2 6] [7 [3 6]],
            [2 7] [8 [2 6]],
            [3 2] [8 [4 2]],
            [3 5] [7 [3 6]],
            [3 6] [6 [4 6]],
            [4 2] [7 [4 3]],
            [4 3] [6 [5 3]],
            [4 6] [5 [4 7]],
            [4 7] [4 [5 7]],
            [5 1] [7 [5 2]],
            [5 2] [6 [5 3]],
            [5 3] [5 [6 3]],
            [5 5] [3 [6 5]],
            [5 6] [4 [5 5]],
            [5 7] [3 [6 7]],
            [6 1] [6 [7 1]],
            [6 3] [4 [6 4]],
            [6 4] [3 [6 5]],
            [6 5] [2 [7 5]],
            [6 7] [2 [7 7]],
            [7 1] [5 [7 2]],
            [7 2] [4 [7 3]],
            [7 3] [3 [7 4]],
            [7 4] [2 [7 5]],
            [7 5] [1 [7 6]],
            [7 6] [0 nil],
            [7 7] [1 [7 6]]}]
        (greedy-search
          [7 6]
          (fn [pos] (filter #(= \. (get-in lines %)) (adjs pos)))
          dests)))))
  )

(deftest test-part-1
  (are [q a] (= a (with-open [rdr (clojure.java.io/reader (format "test/input/15%s.txt" q))] (part-1 (line-seq rdr))))
    "a" 27730
    "b" 36334
    "c" 39514
    "d" 27755
    "e" 28944
    "f" 18740
    "" 189000
    )
)
