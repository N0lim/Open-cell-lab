(ns open-cell-lab.simulation-logic
  "Logic of simulation")
(import java.util.Date)

(defn cell
  "create cell"
  [x y radius red green blue]
  {:x x :y y :radius radius :color {:r red :g green :b blue}})

(def cells (conj [] (cell 0 0 100 0 100 200)
                 (cell 50 50 100 0 100 200)))

(def basic-world [])

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
      (recur (+ world 1) (- (get-time) time-start)))))
