(ns aoc.y2024.day11
  (:require [clojure.math :as math]))

(defn- iter [stone iteration]
  (cond
    (= 0 iteration) 1
    (= 0 stone) (m-iter 1 (dec iteration))
    :else (let [n (inc (int (math/log10 stone)))]
            (if (even? n)
              (+
                (m-iter (rem stone (long (math/pow 10 (/ n 2)))) (dec iteration))
                (m-iter (quot stone (long (math/pow 10 (/ n 2)))) (dec iteration)))
              (m-iter (* stone 2024) (dec iteration))))))

(def ^:private m-iter (memoize iter))

(defn solve 
  ([input] (solve input 25))
  ([input times]
   (let [stones (map parse-long (re-seq #"\d+" input))]
     (reduce + (map #(m-iter % times) stones)))))

(defn solve2 [input] (solve input 75))

(comment
  (solve input)
  (solve2 input))

(def input "4189 413 82070 61 655813 7478611 0 8")
