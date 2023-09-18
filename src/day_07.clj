(ns day-07
  (:require [util :refer [counter]]
            [clojure.math.combinatorics :refer [cartesian-product]]))

(defn parse [l]
  (let [[_ s0 s1] (re-matches #"Step (.) must be finished before step (.) can begin." l)]
    [(.charAt s0 0) (.charAt s1 0)]))

(defn part-1' [deps order rem]
  (if (empty? rem)
    order
    (let [next (first (filter #(empty? (deps %)) rem))
          rem (disj rem next)]
      (recur
        #(disj (deps %) next)
        (conj order next)
        rem))))
(defn part-1 [lines]
  (let [rules (map parse lines)
        deps (reduce (fn [deps [blocker blocked]] (assoc deps blocked (conj (deps blocked #{}) blocker))) {} rules)]
    (apply str (part-1' deps [] (set (mapcat identity rules))))))
