(ns flightgear-auto-lander.logger
  (:require [monger.core :as m]
            [monger.collection :as mc]
            [flightgear.api :as f]))

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
   :velocities (f/velocities)})

(defn add-log
  "Log the current FlightGear state."
  []

  (mc/insert *mongodb-document-name* (flightgear-data)))
