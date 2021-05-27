(ns open-cell-lab.simulation-graphic
  "UI of simulation"
  (:require [open-cell-lab.simulation-logic :as sl]
            [clojure2d.core :as c2d]
            [fastmath.core :as m]))

;; be sure everything is fast as possible
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(def main-canvas (c2d/canvas 1000 1000))

(def main-window (c2d/show-window main-canvas "Simulation"))

(defn circle "draw circle" [canvas x y ^long radius] (c2d/ellipse canvas x y (* radius 2) (* radius 2)))

(defn draw-cell
  [canvas cell]
  (->
   (c2d/set-color
    canvas
    (vals (:color cell)))
   (circle (:x cell)
           (:y cell)
           (:radius cell))))

(defn draw-several-cell
  [canvas cells]
  (loop [cnvs canvas objects-to-draw (count cells)]
    (if (> objects-to-draw 0) ;;(let [x [1 2 4]] (get x (dec (count x))))
      (recur (draw-cell cnvs (get cells (dec objects-to-draw)))
             (dec objects-to-draw)))))

(c2d/with-canvas-> main-canvas
  (c2d/set-background 0 0 0)
  (draw-cell {:x 0 :y 0 :radius 100 :color {:r 0 :g 100 :b 200}})
  (draw-several-cell sl/cells)
  )

(defn doubler [^long x] (* x 2))
