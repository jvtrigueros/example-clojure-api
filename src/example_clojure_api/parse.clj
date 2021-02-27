(ns example-clojure-api.parse
  (:require
    [clojure.string :as str]))

(def column-count 5)

(defn parse-line
  "Given an input line, split line into a map with keys :last-name, :first-name, :email, :favorite-color, and :dob."
  [line]
  (let [re #"\s*(\||,|\s)\s*"                               ;; Zero or more spaces followed by pipe(|), comma(,), or space( )
        [last-name first-name email favorite-color dob :as result] (str/split line re)]
    (when (= column-count
             (count result))
      {:last-name      last-name
       :first-name     first-name
       :email          email
       :favorite-color favorite-color
       :dob            dob})))
