(ns util)

(defn counter [seq]
  (reduce
    (fn [counter char] (update counter char #(inc (or % 0))))
    {}
    seq))

(defn materialize [f xs]
  (zipmap xs (map f xs)))
