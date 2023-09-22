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

(defn part-1 [serial]
  (let [cols (map (fn [x] (map #(power serial x %) (range 1 301))) (range 1 301))
        triples-sums (map #(map sum (partition 3 1 %)) cols)
        squares-sums (mapv #(apply mapv + %) (partition 3 1 triples-sums))]
    (map inc
      (apply max-key
        #(get-in squares-sums %)
        (cartesian-product
          (range 298)
          (range 298))))))
