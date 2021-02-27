(ns example-clojure-api.http.routes-test
  (:require
    [cheshire.core :as json]
    [clojure.test :refer :all]
    [example-clojure-api.http.records :refer [records]]
    [example-clojure-api.http.routes :refer [create-app-handler]]
    [ring.mock.request :as mock])
  (:import
    (java.time
      LocalDate)))


(deftest records-get-test
  (let [app              (create-app-handler)
        result-count     (count @records)
        mock-get-request #(-> (mock/request :get (str "/records/" %))
                              (mock/header "Content-Type" "application/json"))]
    (testing "Ensure GET /records/email returns the correct number of results."
      (let [response (-> (mock-get-request "email")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))
    (testing "Ensure GET /records/birthdate returns the correct number of results."
      (let [response (-> (mock-get-request "birthdate")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))
    (testing "Ensure GET /records/name returns the correct number of results."
      (let [response (-> (mock-get-request "name")
                         app
                         :body
                         (json/parse-string true))]
        (is (= result-count
               (:size response)))
        (is (= result-count
               (count (:results response))))))))


(deftest records-post-test
  (let [app               (create-app-handler)
        mock-body         "Zenia,Molineux,zmolineux39@vkontakte.ru,Red,4/19/2020"
        mock-post-request (-> (mock/request :post "/records")
                              (mock/body mock-body))
        result-count      (count @records)]
    (testing "Ensure POST /records increases the number of records by one"
      ;; Side-effect request
      (app mock-post-request)
      (is (= (inc result-count)
             (count @records))))))


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

