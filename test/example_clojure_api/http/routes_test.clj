(ns example-clojure-api.http.routes-test
  (:require
    [clojure.test :refer :all]
    [example-clojure-api.http.records :refer [sort-by-email records]])
  (:import (java.time LocalDate)))

(defn database
  "Populates a database for use during testing, clears it when done."
  [f]
  (reset! records [[{:last-name "z" :email "a@b.com" :dob (.minusDays (LocalDate/now) 0)}
                    {:last-name "z" :email "z@b.com" :dob (.minusDays (LocalDate/now) 1)}
                    {:last-name "a" :email "a@b.com" :dob (.minusDays (LocalDate/now) 2)}
                    {:last-name "a" :email "z@b.com" :dob (.minusDays (LocalDate/now) 3)}]])
  (f)
  (reset! records []))

(use-fixtures :each database)

