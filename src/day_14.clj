(ns day-14)

(defn digits-rev [n]
  (if (< n 10)
    (list n)
    (cons (mod n 10) (digits-rev (quot n 10)))))
(def digits #(reverse (digits-rev %)))

(defn part-1 [num-recipes]
  (let [state
          (loop [state [3 7]
                 elves [0 1]]
            (if (>= (count state) (+ 10 num-recipes))
              state
              (let [curs (map state elves)
                    state (into state (digits (reduce + curs)))]
                (recur state (map #(mod (+ %1 1 %2) (count state)) elves curs)))))]
    (reduce
      #(+ %2 (* 10 %1))
      0
      (take 10 (drop num-recipes state)))))