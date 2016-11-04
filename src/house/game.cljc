(ns house.game
  (:require [advenjure.game :as game]
            [advenjure.items :as item]
            [house.room-map :refer [room-map]]))


(defn run-game []
  (let [game-state (game/make room-map :street)
        finished? (fn [gs] false)]
      (game/run game-state finished? "We were easing into our forties with the unvoiced concept that the quiet, simple marriage of sister and brother was the indispensable end to a line established in this house by our grandparents. We would die here someday, obscure and distant cousins would inherit the place, have it torn down, sell the bricks and get rich on the building plot; or more justly and better yet, we would topple it ourselves before it was too late.")))
