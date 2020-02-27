(ns toolbox.main
  (:gen-class)
  (:require [clojure.core.async :as a]
            [mount.core :as mount]
            [toolbox.http-server :as http]
            [taoensso.timbre :as log]
            [timbre-ns-pattern-level :as ns-pattern]))

(def log-levels
  {"org.eclipse.jetty*" :info
   "io.pedestal.interceptor*" :info
   "io.pedestal.http*" :info})

(log/merge-config! {:level :debug
                    :middleware [(ns-pattern/middleware log-levels)]})

(defonce running (a/chan))

(defn -main [& _args]
  (println "Starting toolbox")
  (mount/start #'http/server)

  (a/<!! running))
