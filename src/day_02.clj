(ns day-02
  (:require [clojure.math.combinatorics :refer [combinations]]))

(defn parse [l]
  (reduce
    (fn [counter char] (assoc counter char (inc (counter char 0))))
    {}
    (seq l)))

(defn part-1 [lines]
  (let [freq-sets (map vals (map parse lines))
        count-containing (fn [n] (count (filter (fn [freqs] (some #(= n %) freqs)) freq-sets)))]
    (* (count-containing 2) (count-containing 3))))

(defn samesies [s0 s1]
  (mapcat #(if (= %1 %2) [%1] []) s0 s1))
(defn part-2 [lines]
  (let [target-len (dec (count (first lines)))]
    (some
      #(when (= target-len (count %)) %)
      (map
        #(apply str (apply samesies %))
        (combinations (map seq lines) 2)))))
