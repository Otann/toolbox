(ns toolbox.http-server
  (:require [wrench.core :as cfg]
            [mount.core :as mount]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))


(cfg/def port {:name "PORT", :spec int?, :default 8080})

(defn ok [request]
  {:status 200
   :body   "ok"})


(def routes
  (route/expand-routes
    {"/" {:get `ok}}))


(def service
  {::http/join?         false
   ::http/port          port
   ::http/host          "0.0.0.0"
   ::http/resource-path "/public"
   ::http/routes        routes
   ::http/type          :jetty})


(mount/defstate server
  :start (-> service
             http/default-interceptors
             http/create-server
             http/start)
  :stop (http/stop server))
