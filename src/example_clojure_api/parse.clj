(ns example-clojure-api.parse
  (:require
    [clojure.string :as str])
  (:import
    (java.time.format DateTimeFormatter)
    (java.time LocalDate)))

(def column-count 5)

(def date-formatter (DateTimeFormatter/ofPattern "M/d/yyyy"))

(defn ^LocalDate str->date
  [s]
  (try
    (LocalDate/parse s date-formatter)
    (catch Exception e_
      ;; Ignore Exception, but return nil instead of bubbling up the Exception
      (println "Failed to parse" s "as LocalDate!"))))

(defn parse-line
  "Given an input line, split line into a map with keys :last-name, :first-name, :email, :favorite-color, and :dob."
  [line]
  (let [re  #"\s*(\||,|\s)\s*"                              ;; Zero or more spaces followed by pipe(|), comma(,), or space( )
        [last-name first-name email favorite-color dob :as result] (str/split line re)
        dob (str->date dob)]
    (when (and (= column-count
                  (count result))
               dob)
      {:last-name      last-name
       :first-name     first-name
       :email          email
       :favorite-color favorite-color
       :dob            dob})))
