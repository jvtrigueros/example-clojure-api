(ns example-clojure-api.http.routes-test
  (:require
    [clojure.test :refer :all]
    [ring.mock.request :as mock]
    [example-clojure-api.http.routes :refer [create-app-handler]]
    [example-clojure-api.http.records :refer [records]]
    [cheshire.core :as json])
  (:import (java.time LocalDate)))

(deftest records-get-test
  (let [app          (create-app-handler)
        result-count (count @records)
        mock-get-request #(-> (mock/request :get (str "/records/" %))
                              (mock/header "Content-Type" "application/json"))]
    (testing "Ensure /records/email returns the correct number of results."
      (let [response (-> (mock-get-request "email")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))
    (testing "Ensure /records/birthdate returns the correct number of results."
      (let [response (-> (mock-get-request "birthdate")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))
    (testing "Ensure /records/name returns the correct number of results."
      (let [response (-> (mock-get-request "name")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))))

(defn database
  "Populates a database for use during testing, clears it when done."
  [f]
  (reset! records [{:last-name "z" :email "a@b.com" :dob (.minusDays (LocalDate/now) 0)}
                   {:last-name "z" :email "z@b.com" :dob (.minusDays (LocalDate/now) 1)}
                   {:last-name "a" :email "a@b.com" :dob (.minusDays (LocalDate/now) 2)}
                   {:last-name "a" :email "z@b.com" :dob (.minusDays (LocalDate/now) 3)}])
  (f)
  (reset! records []))

(use-fixtures :each database)

