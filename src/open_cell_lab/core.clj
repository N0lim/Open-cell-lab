(ns open-cell-lab.core
  "The game center, must load the rest of the components, can be used by the player directly"
  (:require [open-cell-lab.simulation-logic :as sim-log]
            [open-cell-lab.simulation-graphic :as sim-graph]
            [clojure.set :as s]))
(import java.util.Date)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn start-game
  "main init point"
  []
  (sim-log/start-simulation 0))

(defn u [x y]
  (let [{a1 :a b1 :b} x {a2 :a b2 :b} y]
    (cond (< a1 a2) true
          (< b1 b2) true
          :else false)))

(def z (sorted-set-by u {:a 1 :b 1} {:a 0 :b 0}))


