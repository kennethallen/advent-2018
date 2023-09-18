(ns util)

(defn counter [seq]
  (reduce
    (fn [counter char] (assoc counter char (inc (counter char 0))))
    {}
    seq))

(defn materialize [f xs]
  (into {} (map (fn [x] [x (f x)]) xs)))
