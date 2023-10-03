(ns day-04
  (:require [clojure.instant :refer [read-instant-date]]))

(defrecord Record [ts event]) ; Event is :sleep, :wake, or the ID of a guard beginning shift

(def pattern #"\[(\d{4}-\d\d-\d\d) (\d\d:\d\d)\] (Guard #(\d+) begins shift|falls asleep|wakes up)")
(defn parse [l]
  (let [[_ date time event id] (re-matches pattern l)]
    (Record.
      (read-instant-date (format "%sT%s" date time))
      (cond
        (= event "falls asleep") :sleep
        (= event "wakes up") :wake
        :else (Long/parseLong id)))))

(defn guard-minute-sleeps [records]
  (
    (reduce
      (fn [[guard asleep-since data] rec]
        (cond
          (= (:event rec) :sleep) [guard (.getMinutes (:ts rec)) data]
          (= (:event rec) :wake) [guard nil
            (reduce
              (fn [data minute] (update-in data [guard minute] #(inc (or % 0))))
              data
              (range asleep-since (.getMinutes (:ts rec))))]
          :else [(:event rec) nil data]))
      [nil nil {}]
      records)
    2))

(defn part-1 [lines]
  (let [records (sort-by :ts (map parse lines))
        gms (guard-minute-sleeps records)
        guard (key (apply max-key #(reduce + (vals (val %))) gms))
        minute (key (apply max-key val (gms guard)))]
    (* guard minute)))

(defn part-2 [lines]
  (let [records (sort-by :ts (map parse lines))
        gms (guard-minute-sleeps records)
        gms' (into {} (mapcat (fn [[g m-s]] (map (fn [[m s]] [[g m] s]) m-s)) gms))
        [guard minute] (key (apply max-key val gms'))]
    (* guard minute)))
