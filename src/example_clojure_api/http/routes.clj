(ns example-clojure-api.http.routes
  (:require
    [compojure.core :refer [context defroutes GET POST]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.middleware.defaults :refer [api-defaults wrap-defaults]]))

(defroutes routes
  (context "/records" []
    (GET "/email" [] (response/response {:email "email"}))
    (GET "/birthdate" [] "birthdate")
    (GET "/name" [] "name")
    (POST "/" [] "data")))

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
