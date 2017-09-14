(ns rna.core-test
  (:require [clojure.test :refer :all]
            [clj-time.coerce :as timec]            
            [rna.core :refer :all]))


(deftest to-date-test
  (testing "Return published date of the specified news"
    (is (= (get-date {:publication-date 1463702400000})
           (timec/from-long 1463702400000)))))

