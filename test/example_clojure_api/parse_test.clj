(ns example-clojure-api.parse-test
  (:require
    [clojure.test :refer :all]
    [example-clojure-api.parse :as sut]))


(deftest parse-line-test
  (testing "Ensure proper data is returned"
    (let [data {:last-name      "Trigueros"
                :first-name     "Jose"
                :email          "jvtrigueros@gmail.com"
                :favorite-color "green"
                :dob            (sut/str->date "5/5/2005")}]
      (testing "|"
        (is (= data
               (sut/parse-line "Trigueros | Jose | jvtrigueros@gmail.com | green | 5/5/2005"))))
      (testing ","
        (is (= data
               (sut/parse-line "Trigueros, Jose, jvtrigueros@gmail.com, green, 5/5/2005"))))
      (testing " "
        (is (= data
               (sut/parse-line "Trigueros Jose jvtrigueros@gmail.com green 5/5/2005"))))
      (testing "|, "
        (is (= data
               (sut/parse-line "Trigueros | Jose,jvtrigueros@gmail.com |green                5/5/2005"))))
      (testing "No matching delimiter"
        (is (nil? (sut/parse-line "TriguerosJosejvtrigueros@gmail.comgreen5/5/2005"))))))
  (testing "Incorrect parsing, but will not handle"
    (is (nil? (sut/parse-line "Trigueros | Jose , jvtrigueros@gmail.com * green 5/5/2005")))
    (is (nil? (sut/parse-line "Trigueros * Jose * jvtrigueros@gmail.com * green * 5/5/2005")))))
