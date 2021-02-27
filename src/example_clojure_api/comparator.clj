(ns example-clojure-api.comparator
  "Custom comparator functions.
   Source: https://clojure.org/guides/comparators")


(defn by-email-desc-lastname-asc
  "Compare by Email descending, then LastName ascending"
  [x y]
  (compare [(:email y) (:last-name x)]
           [(:email x) (:last-name y)]))


(defn by-lastname-desc
  "Compare by LastName descending"
  [x y]
  (compare (:last-name y) (:last-name x)))


(defn by-dob-asc
  "Compare by DoB ascending"
  [x y]
  (compare (:dob x) (:dob y)))
