(ns day-00)

(defn part-1 [lines]
  (->> lines
    (map #(Long/parseLong %))
    (reduce +)))
