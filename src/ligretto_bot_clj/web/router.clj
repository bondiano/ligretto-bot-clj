(ns ligretto-bot-clj.web.router
  (:require [compojure.core :refer [defroutes context GET POST DELETE]]
            [compojure.route :as route]
            [ring.util.response :refer [response]]

            [ligretto-bot-clj.web.services.bot :as bot-service]

            [ligretto-bot-clj.web.views.home :refer [home-page]]
            [ligretto-bot-clj.web.middleware :refer [wrap-api wrap-page]]))

(defroutes router
  (wrap-page (GET "/" {:keys [ctx]} (home-page ctx)))

  (wrap-api
   (context "/api" {:keys [ctx]}
     (GET "/bots" [] (response (bot-service/get-all ctx)))
     (POST "/bots" {:keys [body]} (response (bot-service/create body ctx)))
     (GET "/bots/game/:game-id" [game-id] (response (bot-service/get-by-game-id game-id ctx)))
     (DELETE "/bots/game/:game-id/:bot-id" [game-id bot-id] (response (bot-service/remove-bot game-id bot-id ctx)))
     (DELETE "/bots/game/:game-id" [game-id] (response (bot-service/remove-game game-id ctx)))))

  (route/not-found "Not Found"))
