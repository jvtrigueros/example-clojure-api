(ns example-clojure-api.http.main
  (:require
    [example-clojure-api.http.routes :as routes]
    [org.httpkit.server :refer [run-server]]))


(defonce server (atom nil))


(defn stop-server
  "Stops a running server, used in development."
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))


(defn -main
  [& args]
  (let [handler (routes/create-app-handler)
        port    (Integer/parseInt (or (System/getenv "PORT")
                                      "3000"))]
    (println "HTTP Server running on port:" port)
    (run-server
      handler
      {:port port})))


(comment
  ;; Interactive Testing playground

  (require '[org.httpkit.client :as http]
           '[example-clojure-api.http.records :refer [records]]
           '[clojure.java.io :as io]
           '[clojure.string :as str])
  (import '(java.time LocalDate))

  (reset! records
          [{:last-name "z" :email "a@b.com" :dob (.minusDays (LocalDate/now) 0)}
           {:last-name "z" :email "z@b.com" :dob (.minusDays (LocalDate/now) 1)}
           {:last-name "a" :email "a@b.com" :dob (.minusDays (LocalDate/now) 2)}
           {:last-name "a" :email "z@b.com" :dob (.minusDays (LocalDate/now) 3)}])

  (do
    (stop-server)
    (reset! server
            (run-server
              (routes/create-app-handler)
              {:port 3000})))

  @(http/get "http://localhost:3000/records/email"
             {:headers {"Content-Type" "application/json"}})

  @(http/post "http://localhost:3000/records"
              {})

  (let [lines (-> "test-data.csv" io/resource slurp str/split-lines)]
    (doseq [line (take 10 lines)]
      @(http/post
         "http://localhost:3000/records"
         {:body line}))))
