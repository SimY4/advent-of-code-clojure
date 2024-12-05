(ns aoc.coord)

(def hv-directions [[0 1] [0 -1] [1 0] [-1 0]])
(def directions (concat hv-directions [[1 1] [1 -1] [-1 1] [-1 -1]]))

(defn + [& more] 
  (reduce 
    (fn [[x y] [x' y']] [(clojure.core/+ x x') (clojure.core/+ y y')])
    [0 0]
    more))

(defn opposite [[x y]] [(- x) (- y)])

(defn left [coord]
  (cond 
    (= [0 1] coord) [-1 0]
    (= [0 -1] coord) [1 0]
    (= [1 0] coord) [0 1]
    (= [-1 0] coord) [0 -1]
    (= [1 1] coord) [-1 1]
    (= [1 -1] coord) [1 1]
    (= [-1 1] coord) [-1 -1]
    (= [-1 -1] coord) [1 -1]))

(defn right [coord] (opposite (left coord)))

(defn get [str-vec [x y]]
  (nth (nth str-vec y nil) x nil))
