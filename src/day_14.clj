(ns day-14)

(defn digits-rev [n]
  (if (< n 10)
    (list n)
    (cons (mod n 10) (digits-rev (quot n 10)))))
(def digits #(reverse (digits-rev %)))

(defn solve
  ([] (concat [3 7] (solve [3 7] [0 1])))
  ([state elves] (lazy-seq
    (let [curs (map state elves)
          news (digits (reduce + curs))
          state (into state news)]
      (concat news (solve state (map #(mod (+ %1 1 %2) (count state)) elves curs)))))))

(defn from-digits [ds] (reduce #(+ %2 (* 10 %1)) 0 ds))

(def part-1 #(from-digits (take 10 (drop % (solve)))))

(defn part-2 [target]
  (count
    (take-while
      #(not= % target)
      (partition (count target) 1 (solve)))))
