(ns open-cell-lab.simulation-graphic
  "UI of simulation"
  (:require [open-cell-lab.simulation-logic :as sl]
            [clojure2d.core :as c2d]
            [fastmath.core :as m]))

;; be sure everything is fast as possible
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(def main-canvas (c2d/canvas 200 200))

(def main-window (c2d/show-window main-canvas "Simulation"))

(defn circle "draw circle" [canvas x y radius] (c2d/ellipse canvas x y (* radius 2) (* radius 2)))

(defn draw-cell
  [canvas cell]
  (do
    (c2d/set-color
     canvas
     ((:color cell) :r)
     ((:color cell) :g)
     ((:color cell) :b))
    (circle canvas
            (:x cell)
            (:y cell)
            (:radius cell))))

(c2d/with-canvas-> main-canvas
  (c2d/set-background 0 0 0)
  (draw-cell (sl/cell 0 0 100 0 100 200))
  (c2d/set-color 100 100 100)
  (c2d/ellipse 0 100 100 100))
