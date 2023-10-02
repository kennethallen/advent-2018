(ns day-12
  (:require [clojure.data.int-map :refer [dense-int-set]]))

(defn plant-idxs [data]
  (filter #(= \# (get data %)) (range (count data))))

(defn parse-spec [spec]
  (reduce bit-set 0 (plant-idxs spec)))

(defn normalize [old-base state]
  (let [state (dense-int-set state)
        base (apply min state)]
    [(+ base old-base) (dense-int-set (map #(- % base) state))]))

(defn parse [lines]
  (let [[_ init] (re-matches #"initial state: ([#\.]*)" (first lines))
        init (normalize 0 (plant-idxs init))
        rules (map #(re-matches #"([#\.]{5}) => ([#\.])" %) (nnext lines))
        passes (dense-int-set (map parse-spec (map second (filter #(= "#" (get % 2)) rules))))]
    (assert (not (contains? passes 2r00000)))
    [init passes]))

(defn step [[base state] passes]
  (if (empty? state)
    (dense-int-set)
    (normalize
      base
      (filter
        (fn [n] (contains? passes (reduce bit-set 0 (filter #(contains? state (+ % n -2)) (range 5)))))
        (range (- (apply min state) 2) (+ (apply max state) 2))))))

(defn split-at' [i v] [(subvec v 0 i) (subvec v i)])

(defn solve' [gens states passes]
  (let [[next-base next-state] (step (last gens) passes)]
    (if-some [match-idx (get states next-state)]
      (conj (split-at' match-idx gens) (- next-base (first (get gens match-idx))))
      (recur (conj gens [next-base next-state]) (assoc states next-state (count gens)) passes))))
(defn solve [lines gen-idx]
  (let [[init passes] (parse lines)
        [prefix cycle cycle-shift] (solve' [init] {init 0} passes)]
    (if-some [[base state] (get prefix gen-idx)]
      (+ (* (count state) base) (reduce + state))
      (let [gen-idx-after-prefix (- gen-idx (count prefix))
            cycle-count (quot gen-idx-after-prefix (count cycle))
            [base state] (get cycle (mod gen-idx-after-prefix (count cycle)))]
        (+ (* (count state) (+ base (* cycle-count cycle-shift))) (reduce + state))))))

(def part-1 #(solve % 20))
(def part-2 #(solve % 50000000000))
