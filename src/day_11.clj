(ns day-11
  (:require [clojure.math.combinatorics :refer [cartesian-product]]))

(defn power [serial x y]
  (let [rack (+ x 10)
        a (* rack y)
        b (+ a serial)
        c (* b rack)
        d (mod (quot c 100) 10)]
    (- d 5)))

(def sum #(reduce + %))

(defn solve [serial square-size]
  (let [cols (map (fn [x] (map #(power serial x %) (range 1 301))) (range 1 301))
        triples-sums (map #(map sum (partition square-size 1 %)) cols)
        squares-sums (mapv #(apply mapv + %) (partition square-size 1 triples-sums))]
    (apply max-key
      first
      (map
        #(into [(get-in squares-sums %)] (map inc %))
        (cartesian-product
          (range (count squares-sums))
          (range (count squares-sums)))))))

(def part-1 #(rest (solve % 3)))

(defn part-2 [serial]
  (rest (apply max-key first (map #(conj (solve serial %) %) (range 1 301)))))
