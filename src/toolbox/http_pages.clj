(ns toolbox.http-pages
  (:require [hiccup.page :as h]
            [ring.util.response :as r]
            [geheimtur.util.auth :as auth]))

(defn ok [_request]
  {:status 200
   :body   "ok"})

(defn me [request]
  {:status 200
   :body   {:user (auth/get-identity request)}})

(defn login-link []
  [:a {:href "/oauth.login?provider=google&return=/me"} "Login via Google"])

(defn login [_request]
  (r/response
    (h/html5 {:lang "en"}
             [:head
              [:title "Login page"]
              [:meta {:charset "UTF-8"}]]
             [:body
              [:h1 "Login"]
              (login-link)])))

(defn unauthorized [_request]
  (r/response
    (h/html5 {:lang "en"}
             [:head
              [:title "Unauthorized"]
              [:meta {:charset "UTF-8"}]]
             [:body
              [:h1 "Unauthorized"]
              (login-link)])))
