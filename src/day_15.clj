(ns day-15
  (:require [clojure.data.priority-map :refer [priority-map]]))

(defn djikstra [origin adjs-fn]
  (loop [dists {}
         q (priority-map origin [0 nil])]
    (if-some [[pos [dist pred]] (peek q)]
      (recur
        (assoc dists pos [dist pred])
        (into
          (pop q)
          (for [n (adjs-fn pos)
                :when (not (dists n))
                :let [prio [(inc dist) pos]]
                :when (if-some [old-prio (q n)]
                  (< (compare prio old-prio) 0)
                  true)]
            [n prio])))
      dists)))

(defn path-back [dists start]
  (if start (cons start (path-back dists (second (dists start))))))

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

(defn debug-print [height width wall? units]
  (dotimes [y height]
    (apply println
      (apply str
        (for [x (range width)] (cond
          (= (units [y x]) -200) \G
          (= (units [y x]) 200) \E
          (< (units [y x] 0) 0) \g
          (> (units [y x] 0) 0) \e
          (wall? [y x]) \#
          :else \.)))
      (filter #(= y (first (key %))) units))))

(defn solve [wall? round queue units]
  (assert (< round 200));debug
  (if (and (empty? queue) (<= 23 (inc round) 28))
    (do
      (println "finishing" round)
      (debug-print 7 7 wall? units)))
  (if-some [pos (first queue)]
    (let [queue (disj queue pos)]
      (if-some [hp (units pos)]
        (if-some [targets (keys (filter #(enemy? hp (val %)) units))]
          (let [avail? #(or (= pos %) (not (or (wall? %) (units %))))
                avail-adjs #(filter avail? (adjs %))
                dists (djikstra pos avail-adjs)
                [pos units]
                  (if-some [reachable-dests (seq (filter dists (set (mapcat avail-adjs targets))))]
                    (let [;_ (println "Targets" targets)
                          ;_ (println "Dests" (set (mapcat avail-adjs targets)))
                          ;_ (println "Reachables" reachable-dests)
                          min-dest-dist (apply min (map first (map dists reachable-dests)))
                          min-dist-dests (filter #(= min-dest-dist (first (dists %))) reachable-dests)
                          ;_ (println "Considering move to" min-dist-dests "distance" min-dest-dist)
                          dest (first (sort min-dist-dests))
                          units (dissoc units pos) ; optimization: no changes to units if pos'=pos
                          pos (first (take-last 2 (path-back dists dest)))
                          units (assoc units pos hp)]
                      [pos units])
                    [pos units])
                units
                  (if-some [[victim v-hp] (first (sort-by (fn [[victim v-hp]] [(abs v-hp) victim]) 
                              (for [victim (adjs pos)
                                    :let [v-hp (units victim)]
                                    :when v-hp
                                    :when (enemy? hp v-hp)]
                                [victim v-hp])))]
                    ;(do (println "Attacking" victim)
                    (if-some [v-hp (wound v-hp)]
                      (assoc units victim v-hp)
                      (dissoc units victim))
                    ;)
                    units)]
            (recur wall? round queue units))
          [round (abs (reduce + (vals units))) units]);(* round (abs (reduce + (vals units)))))
        (recur wall? round queue units)))
    (recur wall? (inc round) (apply sorted-set (keys units)) units)))

(defn part-1 [lines]
  (let [lines (vec lines)
        units
          (into (sorted-map)
            (for [[y row] (map-indexed vector lines)
                  [x cell] (map-indexed vector row)
                  :let [hp (init-hp cell)]
                  :when hp]
              [[y x] hp]))
        [round total-hp units]
          (solve
            #(= \# (get-in lines %))
            0
            (apply sorted-set (keys units))
            units)]
    (debug-print (count lines) (count (first lines)) #(= \# (get-in lines %)) units)
    (println)
    (* round total-hp)))
