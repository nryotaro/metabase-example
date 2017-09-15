(ns rna.industries
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))


#_(with-open [writer (io/writer "out-file.csv")]
  (csv/write-csv writer
                 [[:abc :def]
                  ["ghi" "jkl"]]))


;(def a (slurp "/Users/nryotaro/news/industries.json"))

#_(defn flat
  [{:keys [code descriptor code_status industries]}]
  (let [{:keys [is_active is_visible]} code_status
        line [[code descriptor is_active is_visible]]]
    (if (seq industries)
      line
      (into line (map flat industries)))))

(defn flat
  [industry]
  (let [{:keys [code descriptor code_status industries]} industry
        {:keys [is_active is_visible]} code_status
        line [[code descriptor is_active is_visible]]]
    (if industries
      (into line (reduce #(into %1 %2) (map flat industries)))
      line)))

(defn chain-idsts
  [industries]
  (reduce #(into %1 %2) (map flat industries)))

 
