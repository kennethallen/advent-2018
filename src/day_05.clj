(ns day-05)

(def annihilate? #(= (abs (- (int %1) (int %2))) (- (int \a) (int \A))))

(defn collapse' [old i new]
  (if-some [c0 (get old i)]
    (let [c1 (get old (inc i))]
      (if (and c1 (annihilate? c0 c1))
        (recur old (+ i 2) new)
        (recur old (inc i) (conj new c0))))
    new))
(defn collapse [old] (collapse' old 0 []))

(defn max-collapse [polymer]
  (let [new (collapse polymer)]
    (if (= (count polymer) (count new))
      new
      (recur new))))
(defn part-1 [polymer] (count (max-collapse (vec polymer))))

(defn part-2 [polymer]
  (let [base (max-collapse (vec polymer))
        units (set (map #(Character/toUpperCase %) base))]
    (apply min (map
      (fn [u]
        (count (max-collapse (filterv #(not= u (Character/toUpperCase %)) base))))
      units))))
