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
                :dob            "05/05/2005"}]
      (testing "|"
        (is (= data
               (sut/parse-line "Trigueros | Jose | jvtrigueros@gmail.com | green | 05/05/2005"))))
      (testing ","
        (is (= data
               (sut/parse-line "Trigueros, Jose, jvtrigueros@gmail.com, green, 05/05/2005"))))
      (testing " "
        (is (= data
               (sut/parse-line "Trigueros Jose jvtrigueros@gmail.com green 05/05/2005"))))
      (testing "|, "
        (is (= data
               (sut/parse-line "Trigueros | Jose,jvtrigueros@gmail.com |green                05/05/2005"))))
      (testing "No matching delimiter"
        (is (nil? (sut/parse-line "TriguerosJosejvtrigueros@gmail.comgreen05/05/2005"))))))
  (testing "Incorrect parsing, but will not handle"
    (is (nil? (sut/parse-line "Trigueros | Jose , jvtrigueros@gmail.com * green 05/05/2005")))
    (is (nil? (sut/parse-line "Trigueros * Jose * jvtrigueros@gmail.com * green * 05/05/2005")))))
