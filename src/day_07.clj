(ns day-07
  (:require [util :refer [materialize]]
            [clojure.data.priority-map :refer [priority-map]]))

(defn parse [l]
  (let [[_ s0 s1] (re-matches #"Step (.) must be finished before step (.) can begin." l)]
    [(.charAt s0 0) (.charAt s1 0)]))

(defn advance [deps workers done in-prog waiting t get-time]
  (if (and (empty? waiting) (empty? in-prog))
    [done t]
    (let [[next-done next-when] (peek in-prog)]
      (if (and next-done (= next-when t))
        (recur
          (materialize #(disj (deps %) next-done) waiting)
          (inc workers)
          (conj done next-done)
          (pop in-prog)
          waiting
          t
          get-time)
        (let [nexts (take workers (sort (filter #(empty? (deps %)) waiting)))
              in-prog' (into in-prog (map (fn [step] [step (+ t (get-time step))]) nexts))]
          (recur
            deps
            (- workers (count nexts))
            done
            in-prog'
            (apply disj waiting nexts)
            (second (first in-prog'))
            get-time))))))

(defn solve [lines workers get-time]
  (let [rules (map parse lines)
        steps (set (mapcat identity rules))
        deps (reduce (fn [deps [blocker blocked]] (update deps blocked #(conj (or % #{}) blocker))) {} rules)]
  (advance
    deps
    workers
    []
    (priority-map)
    steps
    0
    #(get-time (inc (- (int %) (int \A)))))))

(defn part-1 [lines] (apply str (first (solve lines 1 (fn [_] 1)))))

(defn part-2 [lines workers base-time] (second (solve lines workers #(+ base-time %))))
