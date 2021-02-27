(ns example-clojure-api.comparator)

(defn by-email-desc-lastname-asc
  "Sort by Email descending, then LastName ascending
   Source: https://clojure.org/guides/comparators"
  [x y]
  (compare [(:email y) (:last-name x)]
           [(:email x) (:last-name y)]))
