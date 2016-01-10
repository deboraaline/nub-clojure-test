(ns centrality-service.handler
  (:use compojure.core
        ring.middleware.json)
  (:require [compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [compojure.route :as route]
            [centrality-service.core]))

(defn add-edge [v1 v2]
  (let [edge (list (read-string v1) (read-string v2))]
    (centrality-service.core/add-another-edge edge)))

(defn mark-fraudulent [client]
  (centrality-service.core/mark-as-fraudulent (read-string client)))

(defroutes app-routes
  (GET "/ranking" [] (response (centrality-service.core/rank-the-clients)))
  (PUT "/add-edge/:v1/to/:v2" [v1 v2] (response (add-edge v1 v2)))
  (PUT "/mark-as-fraudulent/:client" [client] (response (mark-fraudulent client)))
  
  (route/not-found (response {:message "Page not found"})))

(def app
  (-> app-routes
    wrap-json-response
    wrap-json-body))



