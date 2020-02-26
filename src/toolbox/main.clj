(ns toolbox.main
  (:gen-class)
  (:require [clojure.core.async :as a]
            [mount.core :as mount]
            [toolbox.http-server :as http]))

(defonce running (a/chan))

(defn -main [& _args]
  (println "Starting toolbox")
  (mount/start #'http/server)

  (a/<!! running))
