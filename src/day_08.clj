(ns day-08)

(defrecord Node [children metadata])

(defn parse [l] (map #(Long/parseLong %) (re-seq #"\d+" l)))

(defn read-node [stream]
  (let [[[num-child num-meta] stream] (split-at 2 stream)
        [children stream] (nth (iterate (fn [[children stream]] (let [[n stream] (read-node stream)] [(conj children n) stream])) [[] stream]) num-child)
        [metadata stream] (split-at num-meta stream)]
    [(Node. children metadata) stream]))

(defn sum-metadata [n]
  (+ (reduce + (:metadata n)) (reduce + (map sum-metadata (:children n)))))

(defn part-1 [line]
  (let [[root stream] (read-node (parse line))]
    (sum-metadata root)))

(defn node-val [n]
  (if (empty? (:children n))
    (reduce + (:metadata n))
    (reduce + (map (fn [i] (node-val (get (:children n) (dec i)))) (:metadata n)))))

(defn part-2 [line]
  (let [[root stream] (read-node (parse line))]
    (node-val root)))
