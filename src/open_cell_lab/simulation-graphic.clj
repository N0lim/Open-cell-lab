(ns open-cell-lab.simulation-graphic
  "UI of simulation"
  (:require [open-cell-lab.simulation-logic :as sl]
            [clojure2d.core :as c2d]
            [fastmath.core :as m]))

;; be sure everything is fast as possible
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(def main-canvas (c2d/canvas 500 500))

(def main-window (c2d/show-window main-canvas "Simulation"))

(defn circle
  "draw circle"
  [canvas x y ^long radius]
  (c2d/ellipse canvas x y (* radius 2) (* radius 2)))

(def radius-of-nucleus 10)

(def membrane-thickness (/ ^long radius-of-nucleus 2.5))

(defn draw-cell
  "draw basic cell"
  [canvas cell]
  (->
   (c2d/set-color
    canvas
    (vals (:color cell))
     128) ; alfa 50%
   (circle (:x cell) ; cytoplasm of cell
           (:y cell)
           (:radius cell))
   (c2d/set-color
    (- ^long (:r (:color cell)) 2)
    (/ ^long (:g (:color cell)) 2)
    (/ ^long (:b (:color cell)) 2))
   (circle (:x cell) ; cell nucleus
           (:y cell)
           radius-of-nucleus)
   (c2d/set-stroke membrane-thickness)
   (c2d/ellipse (:x cell) ; membrane
           (:y cell)
           (- (* ^long (:radius cell) 2) (/ ^long membrane-thickness 2))
           (- (* ^long (:radius cell) 2) (/ ^long membrane-thickness 2))
           true)
   (c2d/set-stroke)
   ))

(defn draw-several-cells
  [canvas cells]
  (loop [cnvs canvas objects-to-draw (count cells)]
    (if (> objects-to-draw 0) ;; (let [x [1 2 4]] (get x (dec (count x))))
      (recur (draw-cell cnvs (get cells (dec objects-to-draw)))
             (dec objects-to-draw))
      cnvs)))

(c2d/with-canvas-> main-canvas
  (c2d/set-background 255 255 255)
  (c2d/set-color 0 0 0)
  (c2d/rect 100 100 50 50)
  (draw-several-cells sl/cells)
  )
