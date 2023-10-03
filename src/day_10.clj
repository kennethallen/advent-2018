(ns day-10)

(defrecord Light [pos vel])

(def pl #(Long/parseLong %))
(defn parse [l]
  (let [[_ px py vx vy] (re-matches #"position=< *(-?\d+), +(-?\d+)> velocity=< *(-?\d+), +(-?\d+)>" l)]
    (Light. [(pl px) (pl py)] [(pl vx) (pl vy)])))

(def vec+ #(mapv + %1 %2))

(defn step [l] (update l :pos #(vec+ % (:vel l))))

(defn bounds [ps] [
  (apply min (map first ps))
  (apply min (map second ps))
  (apply max (map first ps))
  (apply max (map second ps))])

(defn max-area [ls]
  (let [ps (map :pos ls)
        [min-x min-y max-x max-y] (bounds ps)]
    (* (- max-y min-y) (- max-x min-x))))

(defn view [ls]
  (let [ps (set (map :pos ls))
        [min-x min-y max-x max-y] (bounds ps)]
  (mapv (fn [y] (apply str (map #(if (ps [% y]) \. \ ) (range min-x (inc max-x))))) (range min-y (inc max-y)))))
  
(defn solve [lines]
  (let [init (mapv parse lines)
        skies (iterate #(mapv step %) init)
        numbered-sky-areas (map #(vector %1 %2 (max-area %2)) (range) skies)]
    (ffirst (filter (fn [[[_ _ a0] [_ _ a1]]] (< a0 a1)) (partition 2 1 numbered-sky-areas)))))

(def part-1 #(view (second (solve %))))
(def part-2 #(first (solve %)))
 