(ns flightgear-auto-lander.logger
  (:require [monger.core :as m]
            [monger.collection :as mc]
            [flightgear.api :as f])
  (:import [java.util.concurrent Executors TimeUnit]))

(def *mongodb-db-name* "flightgear-log")
(def *mongodb-document-name* "log")

(defn setup-mongodb-connection [db-name]
  (m/connect!)
  (m/set-db! (m/get-db db-name)))

(def *flightgear-host* "localhost")
(def *flightgear-port* 5401)

(defn setup-flightgear-connection [host port]
  (f/connect host port))

(setup-mongodb-connection *mongodb-db-name*)
(setup-flightgear-connection *flightgear-host* *flightgear-port*)

(defn flightgear-data []
  {:position (f/position)
   :orientation (f/orientation)
   :velocities (f/velocities)

   :flight-controls (f/flight-controls)
   :engine-controls (f/engine-controls)})

(defn add-log
  "Log the current FlightGear state."
  []

  (mc/insert *mongodb-document-name* (flightgear-data)))

(defn run-on-interval [fn msecs]
  (let [executor (Executors/newScheduledThreadPool 1)]
    (.scheduleAtFixedRate executor fn 0 msecs TimeUnit/MILLISECONDS)
    executor))

(def *log-interval* 250)
(def ^:dynamic *log-executor* nil)

(defn start-logging [interval]
  (def *log-executor* (run-on-interval add-log interval)))

(defn stop-logging []
  (when (not (nil? *log-executor*))
    (.shutdownNow *log-executor*)))