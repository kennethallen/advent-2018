(ns day-01)

(def pl #(Long/parseLong %))

(defn part-1 [lines]
  (->> lines
    (map pl)
    (reduce +)))

(defn part-2' [changes total history]
  (let [history (conj history total)
        total (+ total (first changes))]
    (if (history total)
      total
      (recur (rest changes) total history))))

(defn part-2 [lines]
  (part-2'
    (cycle (map pl lines))
    0
    #{}))
