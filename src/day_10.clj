(ns day-10)

(defrecord Light [pos vel])

(def pl #(Long/parseLong %))
(defn parse [l]
  (let [[_ px py vx vy] (re-matches #"position=< *(-?\d+), +(-?\d+)> velocity=< *(-?\d+), +(-?\d+)>" l)]
    (Light. [(pl px) (pl py)] [(pl vx) (pl vy)])))

(def vec+ #(mapv + %1 %2))

(defn step [l] (update l :pos #(vec+ % (:vel l))))

(defn longest-run' [bs run max-run]
  (if-some [b (first bs)]
    (let [run (if b (inc run) 0)] (recur (rest bs) run (max run max-run)))
    max-run))
(def longest-run #(longest-run' % 0 0))

(defn legible? [ls]
  (let [ps (set (map :pos ls))
        min-x (apply min (map first ps))
        min-y (apply min (map second ps))
        max-x (apply max (map first ps))
        max-y (apply max (map second ps))
        cols (map (fn [x] (map #(contains? ps [x %]) (range min-y (inc max-y)))) (range min-x (inc max-x)))
        line-cols (filter #(<= 8 (longest-run %)) cols)
        line-col-count (bounded-count 3 line-cols)]
  (println line-col-count)
  (<= 3 line-col-count)))

(defn view [ls]
  (let [ps (set (map :pos ls))
        min-x (apply min (map first ps))
        min-y (apply min (map second ps))
        max-x (apply max (map first ps))
        max-y (apply max (map second ps))
        ]
  (mapv (fn [y] (apply str (map #(if (contains? ps [% y]) \. \ ) (range min-x (inc max-x))))) (range min-y (inc max-y)))))
  
(defn part-1 [lines]
  (let [init (mapv parse lines)]
    (view
    (first (filter legible? (take 100 (iterate #(mapv step %) init)))))))
 