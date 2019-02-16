(ns mlp.core
  (:require [prelude.core :refer [dot transpose]]))

;; Utility functions
(defn multipy-matrices
  "Emulates the numpy way of multipying matrices."
  [a b]
  (mapv (partial mapv *) a (repeat (first b))))

(defn sigmoid [x]
  (/ 1 (inc (Math/pow Math/E (- x)))))
(defn derivative-sigmoid [x]
  (-> 1 (- x) (* x)))



;; TODO Add biases
(defn feed-forward [layer weights]
  "Takes a layer and weights and creates the next layer."
  (-> layer
      (dot weights)
      (->> (mapv (partial mapv sigmoid)))))

(defn run-forward [input weights]
  "Reduces down all sets of weights onto the first layer, creating the final
  layer."
  (reduce feed-forward input weights))

(defn weight-error
  [delta weights]
  (dot delta (transpose weights)))

(defn weight-delta [layer error]
  (multipy-matrices error (mapv (partial mapv derivative-sigmoid) layer)))

(defn apply-delta [layer weights delta]
  "Applies a set of deltas to a set of weights."
  (let [adjusted (-> layer transpose (dot delta))]
    (mapv (partial mapv +) weights adjusted)))

(defn train [input weights-1 weights-2 desired-output]
  "Run an input through a set of weights.
  Returns the output generated and an adjusted set of weights.
  TODO Generalise to work with any number of layers"
  (let [l1 (feed-forward input weights-1)
        l2 (feed-forward l1 weights-2)
        l2-error (mapv (partial mapv -) desired-output l2)
        l2-delta (weight-delta l2 l2-error)
        l1-error (weight-error l2-delta weights-2)
        l1-delta (weight-delta l1 l1-error)]
    {:output l2
     :weights [(apply-delta input weights-1 l1-delta)
               (apply-delta l1 weights-2 l2-delta)]}))
