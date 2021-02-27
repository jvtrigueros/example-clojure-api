(ns example-clojure-api.http.routes
  (:require
    [compojure.core :refer [context defroutes GET POST]]
    [example-clojure-api.http.records :as records]
    [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))


(defroutes routes
  (context "/records" []
           (GET "/email" [] (records/by-email))
           (GET "/birthdate" [] (records/by-birthdate))
           (GET "/name" [] (records/by-lastname))
           (POST "/" {body :body} (records/create-record! body))))


(defn debug-middleware
  "Dummy middleware for quick inspecting of values in the middleware chain."
  [handler]
  (fn [request]
    (println (:headers request))
    (let [response (handler request)]
      (println (:headers response))
      response)))


(defn create-app-handler
  []
  (-> routes
      (wrap-json-response)
      (wrap-defaults api-defaults)))
