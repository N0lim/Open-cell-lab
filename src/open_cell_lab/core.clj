(ns open-cell-lab.core
  "the game center, must load the rest of the components, can be used by the player directly"
  (:require [open-cell-lab.simulation-logic :as sim-log]
            [open-cell-lab.simulation-graphic :as sim-graph]))
(import java.util.Date)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn start-game
  "main init point"
  []
  (sim-log/start-simulation 0))
