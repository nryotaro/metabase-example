(ns rna.core
  (:require [abracad.avro :as avro]
            [clojure.spec.alpha :as s]
            [me.raynes.fs :as fs]
            [cheshire.core :as cheshire]
            [clojure.spec.test.alpha :as stest]
            [clj-time.format :as timef]
            [clj-time.coerce :as timec]
            [clojure.java.io :as io]))

(defn deserialize
  [file-path]
  (with-open [adf (avro/data-file-reader file-path)]
    (doall (seq adf))))

(s/fdef list-files
        :args (s/cat :dir-path string?))
(defn list-files
  [dir-path]
  (filter #(. % isFile) (-> dir-path io/file file-seq)))

(defmulti get-date #(->> % :publication-date type))

(defmethod get-date Long
  [news]
  (->> news :publication-date timec/from-long))

(def date-formatter (timef/formatter "yyyy/MM/dd"))

(defmethod get-date :default
  [news]
  (throw (RuntimeException. news)))


(defn create-dest-path
  [root-dir date news-id]
  (. (io/file root-dir
              (timef/unparse date-formatter date)
              (str news-id ".json"))
     getPath))

(defn store-news
  [news root-dir]
  (let [date (get-date news)
        news-id (:an news)
        dest-path (create-dest-path root-dir date news-id)]
    (-> dest-path fs/parent fs/mkdirs)
    (spit dest-path
          (cheshire/generate-string
           news
           {:pretty true}))))

(defn deserialize-news
  [src-dir dest-dir]
  (doall
   (for [avro (list-files src-dir)
         news (deserialize avro)]
     (store-news news dest-dir))))

(stest/instrument)
