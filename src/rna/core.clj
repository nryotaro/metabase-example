(ns rna.core
  (:require [abracad.avro :as avro]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
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


#_(defn store-as-file
  [map]
  nil)


; (f/unparse (f/formatter "yyyyMMdd") (c/from-long 1450656000000))

(stest/instrument)
