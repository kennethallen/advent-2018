(ns day-06
  (:require [util :refer [counter]]
            [clojure.math.combinatorics :refer [cartesian-product]]))

(def pl #(Long/parseLong %))
(defn parse [l]
  (let [[_ x y] (re-matches #"(\d+), (\d+)" l)]
    [(pl x) (pl y)]))

(defn dist [[x0 y0] [x1 y1]]
  (+ (abs (- x0 x1)) (abs (- y0 y1))))

(defn closest [coords c]
  (first (reduce
    (fn [[min-i min-d] [i coord]]
      (let [d (dist c coord)]
        (cond
          (= d min-d) [nil d]
          (< d min-d) [i d]
          :else [min-i min-d])))
    [nil Long/MAX_VALUE]
    (map vector (range) coords))))

(defn part-1 [lines]
  (let [coords (mapv parse lines)
        min-x (apply min (map first coords))
        min-y (apply min (map second coords))
        max-x (apply max (map first coords))
        max-y (apply max (map second coords))
        cell-vals (map #(closest coords %) (cartesian-product (range (inc min-x) max-x) (range (inc min-y) max-y)))
        border (concat
          (map #(vector % min-y) (range min-x max-x))
          (map #(vector % max-y) (range min-x max-x))
          (map #(vector min-x %) (range (inc min-y) max-y))
          (map #(vector max-x %) (range min-y (inc max-y))))
        excluded (set (filter identity (map #(closest coords %) border)))
        counts (counter cell-vals)
        counts' (apply dissoc counts nil excluded)]
    (apply max (vals counts'))))

(defn part-2 [lines limit]
  (let [coords (mapv parse lines)
        min-x (apply min (map first coords))
        min-y (apply min (map second coords))
        max-x (apply max (map first coords))
        max-y (apply max (map second coords))]
    (count (filter
      (fn [c]
        (< (reduce + (map #(dist c %) coords)) limit))
      (cartesian-product (range (inc min-x) max-x) (range (inc min-y) max-y))))))