(ns day-03
  (:require [clojure.math.combinatorics :refer [cartesian-product
                                                combinations]]))

(defrecord Claim [id pos dim])

(def pl #(Long/parseLong %))
(defn parse [l]
  (let [[_ id px py dx dy] (re-matches #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" l)]
    (Claim. (pl id) [(pl px) (pl py)] [(pl dx) (pl dy)])))

(defn overlap [cs]
  (apply cartesian-product (map
    (fn [i] (range
      (apply max (map #((:pos %) i) cs))
      (apply min (map #(+ ((:pos %) i) ((:dim %) i)) cs))))
    (range 2))))

(defn part-1 [lines]
  (let [claims (map parse lines)]
    (count (set (mapcat overlap (combinations claims 2))))))

(defn part-2 [lines]
  (let [claims (map parse lines)]
    (:id (first (filter
      (fn [claim]
        (every? #(or (= % claim) (empty? (overlap [claim %]))) claims))
      claims)))))
