(ns aoc.y2015.day4
  (:import
   (java.security MessageDigest)))

(defn solve
  ([input] (solve input "00000"))
  ([input prefix]
   (let [md5 (MessageDigest/getInstance "MD5")]
     (->>
      (next (range))
      (filter (fn [i]
                (.update md5 (bytes (+ input i)))
                (let [hex (.printHexBinary)])))
      (first)))))

(defn solve2 [input]
  (let [first (->>
               (take-nth 2 input)
               (reductions navigate [0 0]))
        second (->>
                (take-nth 2 (next input))
                (reductions navigate [0 0]))]
    (count (distinct (concat first second)))))

(comment
  (solve input)
  (solve2 input))
