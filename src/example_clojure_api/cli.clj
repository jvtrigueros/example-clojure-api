(ns example-clojure-api.cli
  (:gen-class)
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [example-clojure-api.comparator :refer [by-dob-asc by-email-desc-lastname-asc by-lastname-desc]]
    [example-clojure-api.parse :as parse]))

(defn write-sorted-data!
  [sorted-data output-filename]
  (with-open [writer (io/writer output-filename)]
    (println "Writing Output to" output-filename)
    (->> sorted-data
         (mapv (fn [{:keys [last-name first-name email favorite-color dob]}]
                 ;; ensures order
                 [last-name first-name email favorite-color (.format dob parse/date-formatter)]))
         (cons ["Last Name" "First Name" "Email" "Favorite Color" "Date of Birth"])
         (csv/write-csv writer))))

(defn generate-output
  "Given a list of input strings, combine and generate three output files with data sorted by:
   - Email Desc, Last Name Asc
   - Date of Birth Asc
   - Last Name, Desc"
  [parsed-data]
  (write-sorted-data! (sort by-email-desc-lastname-asc parsed-data) "sorted-by-email-desc-by-lastname-asc.csv")
  (write-sorted-data! (sort by-lastname-desc parsed-data) "sorted-by-lastname-desc.csv")
  (write-sorted-data! (sort by-dob-asc parsed-data) "sorted-by-dob-asc.csv"))

(defn -main
  [& input]
  (let [test-data   (mapcat
                      #(-> % io/file slurp str/split-lines)
                      input)
        parsed-data (->> test-data (map parse/parse-line) (remove nil?))]
    (generate-output parsed-data)))
