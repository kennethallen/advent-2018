(ns day-08)

(defrecord Node [children metadata])

(defn parse [l] (map #(Long/parseLong %) (re-seq #"\d+" l)))

(defn read-node' [stream]
  (let [[[num-child num-meta] stream] (split-at 2 stream)
        [children stream] (nth (iterate (fn [[children stream]] (let [[n stream] (read-node' stream)] [(conj children n) stream])) [[] stream]) num-child)
        [metadata stream] (split-at num-meta stream)]
    [(Node. children metadata) stream]))
(def read-node #(first (read-node' %)))

(defn sum-metadata [n]
  (reduce + (concat (:metadata n) (map sum-metadata (:children n)))))

(def part-1 #(sum-metadata (read-node (parse %))))

(defn node-val [n]
  (if (seq (:children n)) (println (map #(get ()))))
  (reduce +
    (if (empty? (:children n))
      (:metadata n)
      (map #(node-val (get (:children n) (dec %))) (:metadata n)))))

(def part-2 #(node-val (read-node (parse %))))
