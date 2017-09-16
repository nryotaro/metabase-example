(ns rna.industries
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn flat
  ([industry parent_code]
   (let [{:keys [code descriptor code_status industries]} industry
         {:keys [is_active is_visible]} code_status
         line [[code parent_code descriptor is_active is_visible]]]
     (if industries
       (into line (reduce #(into %1 %2) (map #(flat % code) industries)))
       line)))
  ([industry] (flat industry "")))

(defn chain-idsts
  [industries]
  (reduce #(into %1 %2) (map flat industries)))

(defn write-idsts-as-csv
  [industries dest-path]
  (with-open [writer (io/writer dest-path)]
    (csv/write-csv
     writer
     (into [["code" "parent_code" "descriptor" "active" "visible"]]
           (chain-idsts industries)))))
 
