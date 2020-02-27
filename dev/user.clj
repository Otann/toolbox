(ns user
  (:require [clojure.tools.namespace.repl :as ns-tools]
            [toolbox.http-server :as http-server]
            [io.pedestal.http :as http]
            [mount.core :as mount]
            [wrench.core :as cfg]))

(cfg/reset! :env (cfg/from-file "dev/config.edn"))

(defn dev-server []
  (-> http-server/service
      http/default-interceptors
      http/dev-interceptors
      http/create-server))

(mount/defstate server
  :start (http/start (dev-server))
  :stop (http/stop server))

(defn start []
  (mount/start #'server))


(defn stop []
  (mount/stop)
  :stopped)


(defn go []
  (start)
  :ready)


(defn reset []
  (stop)
  (ns-tools/refresh :after 'user/go))
