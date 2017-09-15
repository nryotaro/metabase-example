(ns rna.industries-test
  (:require [clojure.test :refer :all]
            [rna.industries :refer :all]))


(deftest flat-test
  (testing "flat"
    (is (= (flat {:code "c"
                  :descriptor "desc"
                  :code_status {:is_active true :is_visible false}})
           [["c" "desc" true false]])))
  (testing "flat2"
    (is (= (flat {:code "c"
                  :descriptor "desc"
                  :code_status {:is_active true :is_visible false}
                  :industries [{:code "c2"
                                :descriptor "desc2"
                                :code_status {:is_active true :is_visible false}}
                               {:code "c22"
                                :descriptor "desc22"
                                :code_status {:is_active true :is_visible false}}]})
           [["c" "desc" true false]
            ["c2" "desc2" true false]
            ["c22" "desc22" true false]]))))

(deftest industries-test
  (testing "flat-map"
    (with-redefs [flat (fn [industry]
                         [["code"] ["code"]])]
      (is (= (chain-idsts [1 2])
              [["code"] ["code"] ["code"] ["code"]])))))
