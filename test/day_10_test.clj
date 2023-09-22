(ns day-10-test
  (:require [clojure.test :refer :all]
            [day-10 :refer [part-1 part-2]]))

(def sample [
  "position=< 9,  1> velocity=< 0,  2>"
  "position=< 7,  0> velocity=<-1,  0>"
  "position=< 3, -2> velocity=<-1,  1>"
  "position=< 6, 10> velocity=<-2, -1>"
  "position=< 2, -4> velocity=< 2,  2>"
  "position=<-6, 10> velocity=< 2, -2>"
  "position=< 1,  8> velocity=< 1, -1>"
  "position=< 1,  7> velocity=< 1,  0>"
  "position=<-3, 11> velocity=< 1, -2>"
  "position=< 7,  6> velocity=<-1, -1>"
  "position=<-2,  3> velocity=< 1,  0>"
  "position=<-4,  3> velocity=< 2,  0>"
  "position=<10, -3> velocity=<-1,  1>"
  "position=< 5, 11> velocity=< 1, -2>"
  "position=< 4,  7> velocity=< 0, -1>"
  "position=< 8, -2> velocity=< 0,  1>"
  "position=<15,  0> velocity=<-2,  0>"
  "position=< 1,  6> velocity=< 1,  0>"
  "position=< 8,  9> velocity=< 0, -1>"
  "position=< 3,  3> velocity=<-1,  1>"
  "position=< 0,  5> velocity=< 0, -1>"
  "position=<-2,  2> velocity=< 2,  0>"
  "position=< 5, -2> velocity=< 1,  2>"
  "position=< 1,  4> velocity=< 2,  1>"
  "position=<-2,  7> velocity=< 2, -2>"
  "position=< 3,  6> velocity=<-1, -1>"
  "position=< 5,  0> velocity=< 1,  0>"
  "position=<-6,  0> velocity=< 2,  0>"
  "position=< 5,  9> velocity=< 1, -2>"
  "position=<14,  7> velocity=<-2,  0>"
  "position=<-3,  6> velocity=< 2, -1>"])

(def sample-res [
  ".   .  ..."
  ".   .   . "
  ".   .   . "
  ".....   . "
  ".   .   . "
  ".   .   . "
  ".   .   . "
  ".   .  ..."])

(def input-res [
  ".....   .....    ....   ......    ..    ......  .....   ..... "
  ".    .  .    .  .    .       .   .  .   .       .    .  .    ."
  ".    .  .    .  .            .  .    .  .       .    .  .    ."
  ".    .  .    .  .           .   .    .  .       .    .  .    ."
  ".....   .....   .          .    .    .  .....   .....   ..... "
  ".  .    .    .  .         .     ......  .       .       .     "
  ".   .   .    .  .        .      .    .  .       .       .     "
  ".   .   .    .  .       .       .    .  .       .       .     "
  ".    .  .    .  .    .  .       .    .  .       .       .     "
  ".    .  .....    ....   ......  .    .  ......  .       .     "])

(deftest test-part-1
  (is (= sample-res (part-1 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/10.txt")]
    (is (= input-res (part-1 (line-seq rdr))))))

(deftest test-part-2
  (is (= 3 (part-2 sample)))
  (with-open [rdr (clojure.java.io/reader "test/input/10.txt")]
    (is (= 10076 (part-2 (line-seq rdr))))))
