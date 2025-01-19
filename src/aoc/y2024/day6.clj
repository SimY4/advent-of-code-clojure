(ns aoc.y2024.day6
  (:require 
   [aoc.coord :as coord]
   [clojure.string :as str]))

(defn- parse-field [input]
  [
   (first (for [[y line] (map-indexed vector (str/split-lines input))
                x (range 0 (count line))
                :when (= (.charAt line x) \^)]
            [x y]))
   (set (for [[y line] (map-indexed vector (str/split-lines input))
              x (range 0 (count line))
              :when (= (.charAt line x) \#)]
          [x y]))
   ])

(defn- path [guard direction field]
  (let [maxX (apply max (mapv first field))
        maxY (apply max (mapv second field))]
    (->>
      (iterate 
        (fn [[guard direction]]
          (let [new-guard (coord/+ guard direction)]
            (if (.contains field new-guard) [guard (coord/left direction)] [new-guard direction])))
        [guard direction])
      (take-while (fn [[[x y] _]] (and (<= 0 x) (<= 0 y) (<= x maxX) (<= y maxY)))))))

(defn solve [input]
  (let [[guard field] (parse-field input)]
    (->> (path guard [0 -1] field)
      (map first)
      (set)
      (count))))

(defn solve2 [input] 
  (let [[guard field] (parse-field input)
        cache (java.util.HashSet.)]
    (count 
      (for [[pos direction] (path guard [0 -1] field)
            :let [_ (.add cache [pos direction])
                  obstacle (coord/+ pos direction)]
            :when (and 
                    (not (= guard obstacle)) 
                    (not (.contains field obstacle)) 
                    (not-any? #(= obstacle (first %)) cache))
            :let [cache2 (java.util.HashSet. cache)]
            :when (some 
                    (fn [[g d]] (not (.add cache2 [g d])))
                    (path pos (coord/left direction) (conj field obstacle)))]
        obstacle))))

(comment
  (solve input)
  (solve2 input))

(def input "......#........#.............#.##..........................................#.......#...#........#......................#..........
.......................#...#................................#..#.........................................#.....#.....#............
.......................................#.................#..................#....#........................................#.......
.............#...................#.#...............................#....#....#.#.......................................#..#.......
...............................#........##.#..............................#..............................#..#...#..#..............
........................#......................................................................................#..#...............
............................#..........#.......#...................#.....#..................#.....................#..##...........
......................................................##........................#..............#........#..............#..#...#...
............................#.............................................##..........................................#...........
..........#............#....................................#...........#......................................................#..
....#......#.........................................#.......#...............................#..#.##..................#........#..
........................#.#..........##.#..........#......#.......#............................................................#..
..........................................................................................#.#..........................#..........
.............................#............#.......................#................#....................................#.........
...................................#.........................#.............#......#...#................................#.....#....
..........#..................#..#.....................#...#...#.........................................#................#........
....#....#...........#...#..........................................#.............#..#......#................#.....#..............
....................................................................................#......#....##..##....#.......................
...........................#......................#.........##................#............#.......................#...#..........
...........................#....................#............................................#........#.........................#.
..#......#.......#.#...........................#......#....#.#...#.............................#............#..............#......
.......#................#...................................#.........................#..................................#........
..................................................................#...........................................................#...
......................#....#........................................................................................#.............
...#..................#.......#......................................................................#.#....................#.....
................#....................................#.........#..#.........................#...............#.......#.............
.......................................#.................#..................................#...#...........#.....................
....#.....###..................#........#...#..............#............#...............................................#.........
...................#.......................................................#...................................#..................
....#......................................#...##.....#........#......#......#..........................................#.........
......................#............#...............#................#...............................#.............................
......................#..............................................#...........#............#.................................#.
#.#..............................#..#......#.............#.......#......................#...........#........#..........#.........
....#....#.......................................#.......................................................#........................
.......#............................#...........................#.........................#...............#.......................
.....................................#........#.....#........#.......................#...............#..........................#.
....#.......................................#........#......................................................#.................#...
............#..............#...#.......#..#.................................................................................##...#
...#..................#...................#....#............................................................................#.....
.....#....................#...............#.............................................#.....#...............................#...
..............................#.......................#.................#.......................................................#.
.........#....................#..............................................................................#.................#..
......#..................#.........................#.......#.....#...........#....#...................................#......#....
.........#...#......................#......#.................#.........................................#...................#......
....#....#.................................#..#..#........#............................................#.......#..................
....................#...........................#...#............#...........................#....................................
................#................#....#.......................................#....#..........#.#.#..........#....................
....#............................#...................................#.....................................................#.#....
....................................#............#......................#......................##.....................#.......#...
.........................#...................................#.....#................................................#.............
...........#.#..#............................................................................#.#..................................
....#......#.#..................................................#..#....#...#..#...................................#..............
#.........................#.#.................................................................#.#.............................#...
..................#...#........#.......................................................#....#..............................#...#..
.........#..........#.............................................................................................#...............
....................................................................#.......................#..#............#............#........
.................#.................#.............................................#..................................#.............
.#...................................................................................................#................#...........
...#....#.#.#.........#............#..............................................................................................
..................#...#......................................#...#.............#....#....#................#.......................
.....#................................##....................................................................#.....................
............#..................#......#....................#.....#.#.........................##............#............#.......#.
............................#..#.................#.............................................#..................................
............#...............#..........................................................#........#.................................
.......#.......#.......................................##..............##.........................................................
....................#.........#.....#..............#....#.......................................................#.................
...#............................................................#.............................................#...#............#..
....#..............................#...............................#..........................................#...................
............................................................................................................#....................#
..#.............................................................................................................#............#....
..........#...............#.........................................................#...........#.................................
...............#.....................#....................................#....................#.....#...#.....................#..
.#......#.........................................................................................#..#............................
......#..............#........#........#........#.........................................................#.......................
............................#...........#..#.............................................................................#........
..................#..............#.......................................#.........#...............#.....................#.#......
...........................................#.##...................................................................................
.......................#.............................................................................................#............
..........#................................#......#..............................#.................#........#......#.#.........#..
#.......#........................#.....#..........##......#................#......................................#..#..#......#..
........................##........................#....................#......................................#.................#.
..........................#.#........................................................................................#............
..........#.........#.....#......#............#..#................................................##.................#............
........#.....#............##...........................................#..#...............................................#.....#
..............#.......#......................#.....#.......................#.........#........#.................#...#....#........
..............#.................................#.................................................##..............................
................................#........#...........#........................................................#...................
..#.#..................#....#......#..............................#.....#....#.#.............#...............................#....
.....................................................................#......#.........#.......#.........................#....#..#.
.#..........................#.....#...........................................##..^.#..........................#....#....#....#...
...........................#..#................................................................#...#........#.........#...........
......#.................#..............#................#.........#........................#..#........#......#...................
.....#..................#..........#............................................................................#.#...........#...
..........#.........#.................................#......#....#...............#.....................#...#.....................
...........#.......#...........#..#...............#...#..............#...........................................#................
..............................................................#...................................................................
................#..............#.....................#.......#.....................................................#..............
#................#.......#.#..............................#..........................#.............................#..........#...
.#...#.........##..........#......................#....#.........#....................#...............................#.....#.....
....#......#.................#....................................#...............................................................
......#..........#...............................................................#....#...............#.....#.##..................
............................................................#..................#..#...................#.....#.....................
......................#.......#...#....#....................................#.............................#............#..........
.#.............#.....................#...............#.......#...............##.....#..................##...#.....................
........#........................................................###.#..........................................#.................
...##.#................................................................................................#..........................
.............##................#.............#....................#....#......................#........................#.....#....
..#.......#............................................#...............................................#..........................
.................#....#.......................................................#............#..#........................#.......#.#
...#..#......................#............#........#...........................................................................#..
....................................#.......................#.....................................#.....#..#......................
.#...............#......#...........#.....#........##....#.......#.....#...................................................#......
...................#...#............................#..##........#...........................#...........#........................
....................#.#.....................................................................................#......#..............
....#...........#....#...#...#.....................#............................................#..............#..................
......#.#......#................#......................................#...............#..........................................
.......#.#................#..............#.....................#.#......#..................#......................................
.....#................................#..............#.......#......##........#................................#..........#.......
.....................................#......#.........#...#.....#..........#..........................#...........................
......#.......#............................................................#..#....................#...............#.........#....
.........................#...........#.............#......#..#..............................................................#.....
.........#.......##.....#....................................#.....#..............#......#.......#..................##.........#..
.........................#...................#...#.....#...........................................#.................#..#.........
...........#..........#.......................................................................................................#..#
.....#................................................#...........................................................#....#..........
..............#.................................#.............#.........................................................#.........
............#.........................................................#.....#...............................................#.....
..................................................#.................#.............................#....#...#.........#.......#....
........#................#................................................................#................#....................#.
..................##............................###.........#.......#................#........#......#............#...............")
