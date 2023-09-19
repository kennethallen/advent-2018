(ns day-09)

(defn pl [s] (Long/parseLong s))
(defn parse [l]
  (let [[_ players last-marble] (re-matches #"(\d+) players; last marble is worth (\d+) points" l)]
    [(pl players) (pl last-marble)]))

(defrecord Slot [l r])

(defn step [[players scores slots cur] new]
  (if (zero? (mod new 23))
    (let [player (mod new players)
          remove (nth (iterate #(:l (slots %)) cur) 7)
          remove-l (:l (slots remove))
          remove-r (:r (slots remove))
          slots (-> slots
            (assoc-in [remove-l :r] remove-r)
            (assoc-in [remove-r :l] remove-l))]
      [players
       (update scores player #(+ % remove new))
       slots
       remove-r])
    (let [l (:r (slots cur))
          r (:r (slots l))
          slots (-> slots
            (into (repeat (- new (count slots)) nil))
            (conj (Slot. l r))
            (assoc-in [l :r] new)
            (assoc-in [r :l] new))]
      [players scores slots new])))

(defn solve [players last-marble]
  (let [[_ scores _ _] (reduce step [players (into [] (repeat players 0)) [(Slot. 0 0)] 0] (range 1 (inc last-marble)))]
    (apply max scores)))

(defn part-1 [line]
  (let [[players last-marble] (parse line)]
    (solve players last-marble)))

(defn part-2 [line]
  (let [[players last-marble] (parse line)]
    (solve players (* 100 last-marble))))
 