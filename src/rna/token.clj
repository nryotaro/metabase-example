(ns rna.token
  (:require [org.httpkit.client :as http]
            [cheshire.core :as cheshire]
            [rna.url :as url]))


(defn issue-ro-token
  [{:keys [client-id username password]}]
  (let [{body :body}
        @(http/post url/ro
                    {:form-params
                     {:scope "openid service_account_id"
                      :grant_type "password"
                      :connection "service-account"
                      :client_id client-id
                      :username username
                      :password password}})
        {:keys [id_token access_token]} (cheshire/parse-string body true)]
    {:id-token id_token
     :access-token access_token}))


;(println (issue-token rna.credential/credential))


(defn issue-jwt-token
  [id-token client-id]
  (let [{body :body}
        @(http/post url/delegation
                    {:form-params
                     {:id_token id-token
                      :client_id client-id
                      :scope "openid pib"
                      :grant_type "urn:ietf:params:oauth:grant-type:jwt-bearer"
                      :connection "service-account"}})
        {:keys [id_token]} (cheshire/parse-string body true)]
    id_token))


(defn generate-token
  [{:keys [client-id] :as cred}]
  (let [{:keys [id-token access-token]}
        (issue-ro-token cred)
        jwt-token
        (issue-jwt-token id-token client-id)]
    {:id-token id-token :access-token access-token :jwt-token jwt-token}))

(println (generate-token
          rna.credential/credential))
