(ns rna.taxonomy
  (:require [rna.request :as req]
            [rna.url :as url]))

(defn industries
  []
  (req/get-with-auth rna.url/industries))


(defn companies
  [code]
  (req/get-with-auth rna.url/companies
                     {:query-params {:filter.search_string code}}))
