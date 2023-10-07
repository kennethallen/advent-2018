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
(def enemy? #(< (* %1 %2) 0))
(defn adjs [[y x]] [
  [(dec y) x]
  [y (dec x)]
  [y (inc x)]
  [(inc y) x]])
(def adj? #(= 1 (reduce + (map abs (map - %1 %2)))))

(defn debug-print-dests [height width wall? units dests]
  (dotimes [y height]
    (apply println
      (apply str
        (for [x (range width)] (cond
          (dests [y x]) (min 9 (first (dests [y x])))
          (= (units [y x]) -200) \G
          (= (units [y x]) 200) \E
          (< (units [y x] 0) 0) \g
          (> (units [y x] 0) 0) \e
          (wall? [y x]) \#
          :else \.)))
      (filter #(= y (first (key %))) units))))
(def debug-print #(debug-print-dests %1 %2 %3 %4 {}))

(defn solve [wall? wound units]
  (loop [round -1
         queue nil
         units units]
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
            (recur round queue units))
          [round units]))
      (recur (inc round) (apply sorted-set (keys units)) units))))

(defn prep [lines]
  (let [lines (vec lines)]
    [
      lines
      (into
        {}
        (for [[y row] (map-indexed vector lines)
              [x cell] (map-indexed vector row)
              :let [hp (init-hp cell)]
              :when hp]
          [[y x] hp]))]))

(def wall? #(= \# (get-in %1 %2)))

(defn wound [hp elf-dmg]
  (cond
    (> hp 3) (- hp 3)
    (> (- hp) elf-dmg) (+ hp elf-dmg)))

(defn outcome [round units]
  (* round (abs (reduce + (vals units)))))

(defn part-1 [lines]
  (let [[lines units] (prep lines)]
    (apply outcome (solve #(wall? lines %) #(wound % 3) units))))

(defn count-elves [units]
  (count (filter #(> % 0) (vals units))))
(defn part-2 [lines]
  (let [[lines units] (prep lines)
        play (fn [elf-dmg] (solve #(wall? lines %) #(wound % elf-dmg) units))
        elf-count (count-elves units)]
    (loop [n 4]
      (let [[round units] (play n)]
        (if (= elf-count (count-elves units))
          (outcome round units)
          (recur (inc n)))))))
