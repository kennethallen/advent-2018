(ns day-15
  (:require [clojure.data.priority-map :refer [priority-map]]))

(defn greedy-search [origin adjs-fn accept?]
  (loop [dists (transient {})
         q (priority-map origin [0 origin nil])]
    (if-some [[pos [dist _ pred]] (peek q)]
      (let [dists (assoc! dists pos [dist pred])]
        (if (accept? pos)
          [pos (persistent! dists)]
          (recur
            dists
            (into
              (pop q)
              (for [n (adjs-fn pos)
                    :when (not (dists n))
                    :let [prio [(inc dist) n pos]]
                    :when (if-some [old-prio (q n)]
                      (< (compare prio old-prio) 0)
                      true)]
                [n prio])))))
      [nil (persistent! dists)])))

(defn path-back [dists start]
  (loop [path (transient [])
         cur start]
    (if cur
      (recur (conj! path cur) (second (dists cur)))
      (persistent! path))))

(def init-hp {\G -200 \E 200})
(def dmg 3)
(def enemy? #(< (* %1 %2) 0))
(defn adjs [[y x]] [
  [(dec y) x]
  [y (dec x)]
  [y (inc x)]
  [(inc y) x]])
(def adj? #(= 1 (reduce + (map abs (map - %1 %2)))))
(def wound #(cond
  (> % dmg) (- % dmg)
  (> (- %) dmg) (+ % dmg)))

(defn debug-print-dests [height width wall? units dests]
  (dotimes [y height]
    (apply println
      (apply str
        (for [x (range width)] (cond
          (dests [y x]) (first (dests [y x]))
          (= (units [y x]) -200) \G
          (= (units [y x]) 200) \E
          (< (units [y x] 0) 0) \g
          (> (units [y x] 0) 0) \e
          (wall? [y x]) \#
          :else \.)))
      (filter #(= y (first (key %))) units))))
(def debug-print #(debug-print-dests %1 %2 %3 %4 {}))

(defn solve [wall? round queue units]
  (if-some [pos (first queue)]
    (let [queue (disj queue pos)
          hp (units pos)]
      (if-some [targets (keys (filter #(enemy? hp (val %)) units))]
        (let [avail? #(or (= pos %) (not (or (wall? %) (units %))))
              avail-adjs #(filter avail? (adjs %))
              dests (set (mapcat avail-adjs targets))
              [dest dists] (greedy-search pos avail-adjs dests)
              [pos units]
                (if dest
                  (let [pos' (first (take-last 2 (path-back dists dest)))]
                    [pos' (if (= pos pos') units (assoc (dissoc units pos) pos' hp))])
                  [pos units])
              [units queue]
                (if-some [[victim v-hp]
                            (first (sort-by
                              (fn [[victim v-hp]] [(abs v-hp) victim]) 
                              (for [victim (adjs pos)
                                    :let [v-hp (units victim)]
                                    :when v-hp
                                    :when (enemy? hp v-hp)]
                                [victim v-hp])))]
                  (if-some [v-hp (wound v-hp)]
                    [(assoc units victim v-hp) queue]
                    [(dissoc units victim) (disj queue victim)])
                  [units queue])]
          (recur wall? round queue units))
        [round units]))
    (recur wall? (inc round) (apply sorted-set (keys units)) units)))

(defn part-1 [lines]
  (let [lines (vec lines)
        units
          (into
            {}
            (for [[y row] (map-indexed vector lines)
                  [x cell] (map-indexed vector row)
                  :let [hp (init-hp cell)]
                  :when hp]
              [[y x] hp]))
        [round units]
          (solve
            #(= \# (get-in lines %))
            0
            (apply sorted-set (keys units))
            units)]
    (* round (abs (reduce + (vals units))))))
