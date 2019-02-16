(ns mlp.interactive
  "This is a dumping ground for interactive testing, just ignore."
  (:use [mlp.core]))

;; Each weight set needs to be a matrix with the dimensions:
;; (size of next layer) x (size of this layer)
(def training-data
  [[[[0 0 1]] [[0]]]
   [[[0 1 1]] [[1]]]
   [[[1 0 1]] [[1]]]
   [[[1 1 1]] [[0]]]])

(defn random-weights
  "Makes up some random weights."
  [in-size out-size]
  (let [row-fn #(repeatedly out-size (comp (partial - 1) (partial * 2) rand))]
    (vec (repeatedly in-size (comp vec row-fn)))))

(def weights
  [(random-weights 3 4)
   (random-weights 4 1)])

;; TODO train by running a batch of data without adjusting, but average all the
;; weight adjustments

(defn train-once
  [training-data initial-weights]
  (reduce
   (fn trainer [[weights-1 weights-2] [input output]]
     (:weights (train input weights-1 weights-2 output)))
   initial-weights
   training-data))

(train (first (first training-data)) (weights 0) (weights 1) [[1]])

(def trained-weights
  (iterate (partial train-once training-data) weights))

;; Should be drifting towards 0
(last
 (for [w (take 2500 trained-weights)]
   (run-forward [[1 0 1]] w)))

;; Should be drifting towards 1
(last
 (for [w (take 2500 trained-weights)]
   (run-forward [[1 0]] w)))
