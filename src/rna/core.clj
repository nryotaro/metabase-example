(ns rna.core
  (:require [abracad.avro :as avro]))


(defn deserialize
  [file-path]
  (with-open [adf (avro/data-file-reader file-path)]
    (doall (seq adf))))

