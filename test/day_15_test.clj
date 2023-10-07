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
                  :when (= \G cell)
                  adj (adjs [y x])
                  :when (= \. (get-in lines adj))]
              adj))]
      (is (=
        nil
        (greedy-search
          [1 1]
          (fn [pos] (filter #(= \. (get-in lines %)) (adjs pos)))
          dests)))))
  )

(deftest test-part-1
  (are [q a] (= a (with-open [rdr (clojure.java.io/reader (format "test/input/15%s.txt" q))] (part-1 (line-seq rdr))))
    "a" 27730
    ;"b" 36334
    ;"c" 39514
    ;"d" 27755
    ;"e" 28944
    ;"f" 18740
    ;"" 189000
    )
)
