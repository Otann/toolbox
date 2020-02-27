(ns toolbox.auth
  (:require [ring.util.response :as ring-resp]
            [geheimtur.util.auth :as geheimtur]
            [wrench.core :as cfg]))

(cfg/def client-id {:require true})
(cfg/def client-secret {:require true})

(defn on-google-success
  [_ {:keys [identity return]}]
  (let [user {:user  identity
              :roles #{:user}}]
    (-> (ring-resp/redirect return)
        (geheimtur/authenticate user))))

(def providers
  {:google {:auth-url           "https://accounts.google.com/o/oauth2/auth"
            :client-id          client-id
            :client-secret      client-secret
            :callback-uri       "http://localhost:8080/oauth.callback"
            :scope              "profile email"
            :token-url          "https://accounts.google.com/o/oauth2/token"
            :user-info-url      "https://www.googleapis.com/oauth2/v1/userinfo?alt=json"
            :on-success-handler on-google-success}})
