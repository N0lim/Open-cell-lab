(ns open-cell-lab.simulation-logic)
(import java.util.Date)

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
