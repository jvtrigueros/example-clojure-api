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

(defn -main [& args]
  (let [handler (routes/create-app-handler)
        port    (Integer/parseInt (or (System/getenv "PORT")
                                      "8080"))]
    (run-server
      handler
      {:port port})))
