(ns aoc.commons)

(def hex-array "0123456789ABCDEF")

(defn pairs [as] 
  (if (< (count as) 1) []
    (cons [(first as) (last as)] (map vector as (next as)))))

(defn hex-binary [bytes]
  (let [bytes-len (count bytes)
        hex-chars (char-array (* 2 bytes-len))]
    (->> 
      (range bytes-len)
      (map #(nth % bytes)))
    (str hex-chars)))
