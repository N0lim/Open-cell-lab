(ns open-cell-lab.simulation-logic
  "Logic of simulation"
  (:require [fastmath.core :as m]
            [clojure.set :as s]))
(import java.util.Date)

;; be sure everything is fast as possible
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(defn create-cell
  "create cell"
  [x y radius red green blue]
  {:x x :y y :radius radius :color {:r red :g green :b blue}})

(def cells (conj [] (create-cell 101 101 50 0 0 255)
                 (create-cell 100 100 50 0 0 255)
                 (create-cell 0 0 30 50 100 200)
                 (create-cell 100 101 50 0 0 255)
                 (create-cell 101 100 50 0 0 255)))

(def substrate {:cells cells :other 0})

(defn point-distance
  "return distance between points"
  [point1 point2]
  (let [x1 (:x point1) x2 (:x point2)
        y1 (:y point1) y2 (:y point2)]
    (m/sqrt (+ (m/sq (- ^long x1 ^long x2))
               (m/sq (- ^long y1 ^long y2))))))

(defn circle-distance
  [circle1 circle2]
  "return distance between cells taking the radius into account"
  (- ^long (point-distance circle1 circle2)
     ^long (:radius circle1) ^long (:radius circle2)))

(defn circle-collide?
  "checks if the cells collide"
  [circle1 circle2]
  (<= ^long (circle-distance circle1 circle2) 0))

(defn sort-cell-by-6vals
  [cell1 cell2]
  (let [{x1 :x y1 :y radius1 :radius {r1 :r g1 :g b1 :b} :color} cell1
        {x2 :x y2 :y radius2 :radius {r2 :r g2 :g b2 :b} :color} cell2]
    (cond (< ^long x1 ^long x2) true
          (< ^long y1 ^long y2) true
          (< ^long radius1 ^long radius2) true
          (< ^long r1 ^long r2) true
          (< ^long g1 ^long g2) true
          (< ^long b1 ^long b2) true
          :else false)))

(defn recursive-conj
  "add all elements from one array to another"
  [array-to array-from]
  (loop [array-from array-from array-to array-to]
    (if (not (empty? array-from))
      (recur (drop-last array-from)
             (conj array-to (last array-from)))
      array-to)))

(defn cell-brutforce-for-one-cell
  "finding couple for 1 cell"
  [cell-to-find cell-array]
   (for [i (filter
    #(circle-collide? cell-to-find %)
    cell-array)]
     (conj (sorted-set-by sort-cell-by-6vals) i cell-to-find)))

(defn cell-brutforce
  "method for processing a small number of cells"
  [cells]
  (loop [cells cells array #{}]
    (if (not (empty? (pop cells)))
      (recur (pop cells)
             (let [result (cell-brutforce-for-one-cell
                           (last cells) (drop-last cells))]
               (if (not (empty? result))
                 (recursive-conj array result)
                 (conj array (last cells)))))
      (set array))))

(defn collisions
  [world]
  (if true (cell-brutforce (:cells world)))) ; must change the algorithm for a different number of objects

(defn start-new-iter
  "main game cycle"
  [world]
  (do (Thread/sleep (rand-int 10))
      (println world)))

(defn get-time
  "get time in ms"
  []
  (.getTime (java.util.Date.)))

(defn start-simulation
  "start main game cycle"
  [world]
  (loop [world world
         time 0]
    (if (< time 1000) (Thread/sleep (- 1000 time)))
    (let [time-start (get-time)]
      (start-new-iter world)
      (recur (+ ^long world 1) (- ^long (get-time) ^long time-start)))))
