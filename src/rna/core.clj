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


#_(defn to-date)

(defmulti get-date #(->> % :publication-date type))

(defmethod get-date Long
  [news]
  #_(timef/unparse (timef/formatters :basic-date-time)
                 (->> news :publication-date timec/from-long))  
  (->> news :publication-date timec/from-long))

(defmethod get-date :default
  [news]
  (throw (RuntimeException. news)))

(comment 
  (defn store-news
    [news root-dir]
    (let [date (get-date news)
          news-id (:an news)
          dest-path (build-dest-path root-dir date news-id)]
      (-> dest-path fs/parent fs/mkdirs)
      (scheshire/generate-stream news
                                 (clojure.java.io/writer dest-path)
                                 {:pretty true}))))

(stest/instrument)
