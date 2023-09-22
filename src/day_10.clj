(ns day-10)

(defrecord Light [pos vel])

(def pl #(Long/parseLong %))
(defn parse [l]
  (let [[_ px py vx vy] (re-matches #"position=< *(-?\d+), +(-?\d+)> velocity=< *(-?\d+), +(-?\d+)>" l)]
    (Light. [(pl px) (pl py)] [(pl vx) (pl vy)])))

(def vec+ #(mapv + %1 %2))

(defn step [l] (update l :pos #(vec+ % (:vel l))))

(defn max-area [ls]
  (let [ps (map :pos ls)
        min-x (apply min (map first ps))
        min-y (apply min (map second ps))
        max-x (apply max (map first ps))
        max-y (apply max (map second ps))]
    (* (- max-y min-y) (- max-x min-x))))

(defn view [ls]
  (let [ps (set (map :pos ls))
        min-x (apply min (map first ps))
        min-y (apply min (map second ps))
        max-x (apply max (map first ps))
        max-y (apply max (map second ps))
        ]
  (mapv (fn [y] (apply str (map #(if (contains? ps [% y]) \. \ ) (range min-x (inc max-x))))) (range min-y (inc max-y)))))
  
(defn part-1 [lines]
  (let [init (mapv parse lines)
        skies (iterate #(mapv step %) init)
        sky-areas (map #(vector % (max-area %)) skies)
        ]
    (view
    (first (map ffirst (filter (fn [[[_ a0] [_ a1]]] (< a0 a1)) (partition 2 1 sky-areas)))))))
 