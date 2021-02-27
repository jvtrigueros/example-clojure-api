(ns example-clojure-api.http.records
  (:require
    [example-clojure-api.parse :as parse]
    [example-clojure-api.comparator :refer [by-email-desc-lastname-asc by-dob-asc by-lastname-desc]]
    [ring.util.response :as response]))

(defonce records (atom []))

(defn by-fn
  "Get sorted records, sorted by fn, where fn is a comparator."
  [f]
  (let [results (->> records
                     deref
                     (sort f)
                     (mapv #(update % :dob parse/date->str)))]
    (response/response
      {:size    (count results)
       :results results})))

(defn by-email
  "Get a list of records sorted by email."
  []
  (by-fn by-email-desc-lastname-asc))

(defn by-birthdate
  "Get a list of records sorted by birthdate."
  []
  (by-fn by-dob-asc))


(defn by-lastname
  "Get a list of records sorted by last name."
  []
  (by-fn by-lastname-desc))


(defn create-record!
  "Create a new record and add to database."
  [arglist])

