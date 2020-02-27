(ns toolbox.http-server
  (:require [wrench.core :as cfg]
            [mount.core :as mount]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [geheimtur.impl.oauth2 :as oauth2]
            [geheimtur.interceptor :as auth-interceptor]
            [ring.middleware.session.cookie :as cookie]
            [toolbox.auth :as auth]
            [toolbox.http-pages :as page]
            [ring.util.response :as r]
            [hiccup.page :as h]))


(cfg/def port {:name "PORT", :spec int?, :default 8080})

(def routes
  (route/expand-routes
    {"/" {:get              `page/ok
          "/me"             {:get          `page/me
                             :interceptors [(auth-interceptor/guard)
                                            http/json-body]}
          "/login"          {:get          `page/login
                             :interceptors [http/html-body]}
          "/unauthorized"   {:get          `page/unauthorized
                             :interceptors [http/html-body]}
          "/oauth.login"    {:get (oauth2/authenticate-handler auth/providers)}
          "/oauth.callback" {:get (oauth2/callback-handler auth/providers)}}}))


(def service
  {::http/join?          false
   ::http/port           port
   ::http/host           "0.0.0.0"
   ::http/resource-path  "/public"
   ::http/routes         routes
   ::http/type           :jetty
   ::http/enable-session {:cookie-name "SID"
                          :store       (cookie/cookie-store)}})


(mount/defstate server
  :start (-> service
             http/default-interceptors
             http/create-server
             http/start)
  :stop (http/stop server))
