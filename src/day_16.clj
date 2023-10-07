(ns day-16)

(defn op-reg [f] (fn [regs a b c] (assoc regs c (f (regs a) (regs b)))))
(defn op-imm [f] (fn [regs a b c] (assoc regs c (f (regs a) b))))
(defn op-cond [p] (fn [regs a b c] (assoc regs c (if (p regs a b) 1 0))))
(def ops {
  :addr (op-reg +)
  :addi (op-imm +)
  :mulr (op-reg *)
  :muli (op-imm *)
  :banr (op-reg bit-and)
  :bani (op-imm bit-and)
  :borr (op-reg bit-or)
  :bori (op-imm bit-or)
  :setr (fn [regs a _ c] (assoc regs c (regs a)))
  :seti (fn [regs a _ c] (assoc regs c a))
  :gtir (op-cond (fn [regs a b] (> a (regs b))))
  :gtri (op-cond (fn [regs a b] (> (regs a) b)))
  :gtrr (op-cond (fn [regs a b] (> (regs a) (regs b))))
  :eqir (op-cond (fn [regs a b] (= a (regs b))))
  :eqri (op-cond (fn [regs a b] (= (regs a) b)))
  :eqrr (op-cond (fn [regs a b] (= (regs a) (regs b))))})

(def pl #(Long/parseLong %))
(def parse-data #(mapv pl (rest %)))
(def op-ptrn #"(\d+) (\d+) (\d+) (\d+)")
(defn parse [lines]
  (loop [examples []
         lines lines]
    (let [[[before op after _] trail] (split-at 4 lines)]
      (if-some [before (re-matches #"Before: \[(\d+), (\d+), (\d+), (\d+)\]" before)]
        (recur
          (conj examples (mapv parse-data [
            before
            (re-matches op-ptrn op)
            (re-matches #"After:  \[(\d+), (\d+), (\d+), (\d+)\]" after)]))
          trail)
        [
          examples
          (map #(parse-data (re-matches op-ptrn %)) (drop 2 lines))]))))

(defn part-1 [lines]
  (let [[examples prog] (parse lines)]
    (count (filter
      (fn [[before op after]]
        (<= 3 (count (filter
          #(= after (apply % before (rest op)))
          (vals ops)))))
      examples))))
