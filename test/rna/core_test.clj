(ns rna.core-test
  (:require [clojure.test :refer :all]
            [clj-time.coerce :as timec]
            [clj-time.core :as t]
            [clojure.java.io :as io]
            [rna.core :refer :all]))


(deftest to-date-test
  (testing "Return published date of the specified news"
    (is (= (get-date {:publication-date 1463702400000})
           (timec/from-long 1463702400000)))))

(deftest create-dest-path-test
  (testing "Return a path to store news whose id is passed as an argument")
  (is (= "/home/user/2017/09/11/unique_id.json"
         (create-dest-path "/home/user"
                           (t/date-time 2017 9 11)
                           "unique_id"))))
