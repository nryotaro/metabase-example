(ns rna.credential
  (:require [clojure.edn :as edn]))

(defn load-credential
  ([credential-file]
   (edn/read-string (slurp credential-file)))
  ([]
   (load-credential "dev-resources/credential.edn")))


(def credential (load-credential))







