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

(defn solve' [players last-marble]
  (let [scores (long-array players)
        ls (long-array (inc last-marble))
        rs (long-array (inc last-marble))
        cur (atom 0)]
    ; ls[0],rs[0] initialized to 0,0
    (doseq [new (range 1 (inc last-marble))]
      (if (zero? (mod new 23))
        (let [player (mod new players)
              rem (nth (iterate #(aget ls %) @cur) 7)
              l (aget ls rem)
              r (aget rs rem)]
          (aset-long scores player (+ (aget scores player) new rem))
          (aset-long ls r l)
          (aset-long rs l r)
          (reset! cur r))
        (let [l (aget rs @cur)
              r (aget rs l)]
          (aset-long ls new l)
          (aset-long rs new r)
          (aset-long ls r new)
          (aset-long rs l new)
          (reset! cur new))))
    (apply max scores)))

(defn part-1 [line]
  (let [[players last-marble] (parse line)]
    (solve' players last-marble)))

(defn part-2 [line]
  (let [[players last-marble] (parse line)]
    (solve' players (* 100 last-marble))))
 