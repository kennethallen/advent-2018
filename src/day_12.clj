(ns day-12
  (:require [clojure.data.int-map :refer [dense-int-set]]))

(defn plant-idxs [data]
  (filter #(= \# (get data %)) (range (count data))))

(defn parse-spec [spec]
  (reduce bit-set 0 (plant-idxs spec)))

(defn parse [lines]
  (let [[_ init] (re-matches #"initial state: ([#\.]*)" (first lines))
        init (dense-int-set (plant-idxs init))
        rules (map #(re-matches #"([#\.]{5}) => ([#\.])" %) (nnext lines))
        passes (dense-int-set (map parse-spec (map second (filter #(= "#" (get % 2)) rules))))]
    (assert (not (contains? passes 2r00000)))
    [init passes]))

(defn step [state passes]
  (dense-int-set
    (filter
      (fn [n] (contains? passes (reduce bit-set 0 (filter #(contains? state (+ % n -2)) (range 5)))))
      (range (- (apply min state) 2) (+ (apply max state) 2)))))

(defn debug-view [state]
  [(apply min state) (apply str (map #(if (contains? state %) \# \.) (range (apply min state) (inc (apply max state)))))])

(defn solve [lines gens]
  (let [[init passes] (parse lines)
        state (nth (iterate #(step % passes) init) gens)]
    (reduce + state)))

(def part-1 #(solve % 20))
