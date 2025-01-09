(ns aoc.coord)

(def hv-directions [[0 1] [0 -1] [1 0] [-1 0]])
(def directions (concat hv-directions [[1 1] [1 -1] [-1 1] [-1 -1]]))

(defn + [& more] 
  (reduce #(mapv clojure.core/+ %1 %2) [0 0] more))

(defn opposite [coord] (mapv - coord))

(defn left [coord]
  (case coord 
    [0 1] [-1 0]
    [0 -1] [1 0]
    [1 0] [0 1]
    [-1 0] [0 -1]
    [1 1] [-1 1]
    [1 -1] [1 1]
    [-1 1] [-1 -1]
    [-1 -1] [1 -1]))

(defn right [coord] (opposite (left coord)))

(defn get [str-vec [x y]]
  (nth (nth str-vec y nil) x nil))
