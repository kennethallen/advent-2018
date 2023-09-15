(ns day-01)

(defn parse [l] (Long/parseLong l))

(defn part-1 [lines]
  (->> lines
    (map parse)
    (reduce +)))

(defn part-2' [changes total history]
  (let [history (conj history total)
        total (+ total (first changes))]
    (if (contains? history total)
      total
      (recur (rest changes) total history))))

(defn part-2 [lines]
  (part-2'
    (cycle (map parse lines))
    0
    #{}))
