(ns rna.request
  (:require [org.httpkit.client :as http]
            [rna.credential :as cred]
            [cheshire.core :as cheshire]            
            [rna.token :as tkn]))

(defn get-with-auth
  ([url options]
   (let [{jwt-token :jwt-token} (tkn/generate-token cred/credential)
         {body :body}
         @(http/get url
                    (merge {:headers {"Authorization" (str "Bearer " jwt-token)}}
                           options))]
     (cheshire/parse-string body true)))
  ([url] (get-with-auth url {})))
