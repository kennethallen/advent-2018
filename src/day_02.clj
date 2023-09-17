(ns day-02)

(defn parse [l]
  (reduce
    (fn [counter char] (assoc counter char (inc (counter char 0))))
    {}
    (seq l)))

(defn part-1 [lines]
  (let [freq-sets (map vals (map parse lines))
        count-containing (fn [n]
                            (count (filter (fn [freqs] (some #(= n %) freqs)) freq-sets)))]
    (* (count-containing 2) (count-containing 3))))
