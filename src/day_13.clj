(ns day-13)

(def carts {\> 0, \v 1, \< 2, \^ 3})
(def next-turn {0 1, 1 3, 3 0})
(def init-turn 3)
(def move [
  (fn [[y x]] [y (inc x)])
  (fn [[y x]] [(inc y) x])
  (fn [[y x]] [y (dec x)])
  (fn [[y x]] [(dec y) x])])

(defn step [lines coord [dir turn]]
  (let [track (get-in lines coord)
        [dir turn]
          (cond
            (or
              (contains? carts track)
              (and (= track \|) (odd? dir))
              (and (= track \-) (even? dir)))
              [dir turn]
            (= track \/) [(- 3 dir) turn]
            (= track \\) [([1 0 3 2] dir) turn]
            (= track \+) [(mod (+ dir turn) 4) (next-turn turn)]
            :else (throw (Exception. (format "Invalid dir/track combo: %s %s" dir track))))]
    [((move dir) coord) [dir turn]]))

(defn solve [lines to-move moved]
  (if-some [[coord state] (first to-move)]
    (let [[coord' state'] (step lines coord state)]
      (if (or (contains? to-move coord') (contains? moved coord'))
        coord'
        (recur lines (dissoc to-move coord) (assoc moved coord' state'))))
    (recur lines moved (sorted-map))))

(defn part-1 [lines]
  (let [lines (vec lines)
        init-carts
          (for [y (range (count lines))
                :let [row (get lines y)]
                x (range (count row))
                :let [cart (get row x)]
                :when (contains? carts cart)]
            [[y x] [(carts cart) init-turn]])]
    (vec (reverse (solve lines (into (sorted-map) init-carts) (sorted-map))))))