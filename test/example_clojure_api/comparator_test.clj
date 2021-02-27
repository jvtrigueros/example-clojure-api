(ns example-clojure-api.comparator-test
  (:require
    [clojure.test :refer :all]
    [example-clojure-api.comparator :as sut])
  (:import (java.time LocalDate)))

(deftest parse-line-test
  (let [unsorted-data [{:last-name "z" :email "a@b.com" :dob (.minusDays (LocalDate/now) 0)}
                       {:last-name "z" :email "z@b.com" :dob (.minusDays (LocalDate/now) 1)}
                       {:last-name "a" :email "a@b.com" :dob (.minusDays (LocalDate/now) 2)}
                       {:last-name "a" :email "z@b.com" :dob (.minusDays (LocalDate/now) 3)}]]
    (testing "Ensure data is sorted by Email descending and Last Name ascending"
      (let [sorted-data (sort sut/by-email-desc-lastname-asc unsorted-data)]
        (is (= {:last-name "a", :email "z@b.com"}
               (dissoc (first sorted-data) :dob)))
        (is (= {:last-name "z", :email "a@b.com"}
               (dissoc (last sorted-data) :dob)))))
    (testing "Ensure data is sorted by Date of Birth asc"
      (let [sorted-data (sort sut/by-dob-asc unsorted-data)]
        (is (= {:last-name "a", :email "z@b.com"}
               (dissoc (first sorted-data) :dob)))
        (is (= {:last-name "z", :email "a@b.com"}
               (dissoc (last sorted-data) :dob)))))
    (testing "Ensure data is sorted by Last Name desc"
      (let [sorted-data (sort sut/by-lastname-desc unsorted-data)]
        (is (= {:last-name "z", :email "a@b.com"}
               (dissoc (first sorted-data) :dob)))
        (is (= {:last-name "a", :email "z@b.com"}
               (dissoc (last sorted-data) :dob)))))))





