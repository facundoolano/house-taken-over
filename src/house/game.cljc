(ns house.game
  (:require [advenjure.game :as game]
            [advenjure.items :as item]
            [house.room-map :refer [room-map]]
            [advenjure.verbs :refer [make-item-handler make-say-verb]]
            [advenjure.verb-map :refer [default-map add-verb]]))

(def break (make-item-handler "break" :break))

(def verb-map (-> default-map
                  (add-verb ["^break (?<item>.*)" "^break in (?<item>.*)"  "^break into (?<item>.*)" "^break$"] break)))

(def init-text (str "\nWe were easing into our forties with the unvoiced concept that the quiet, simple marriage of sister and brother was the indispensable end to a line established in this house by our grandparents. We would die here someday, obscure and distant cousins would inherit the place, have it torn down, sell the bricks and get rich on the building plot; or more justly and better yet, we would topple it ourselves before it was too late."
                    "\n \nJulio Cortázar, \"House Taken Over\"."
                    "\n \nWorks of fiction contain a single plot, with all its imaginable permutations. Those of a philosophical nature invariably include both the thesis and the antithesis, the rigorous pro and con of a doctrine. A book which does not contain its counterbook is considered incomplete."
                    "\n \nJorge Luis Borges."))

(defn run-game []
  (let [game-state (game/make room-map :back-hall3) ;FIXME :street)
        finished? (fn [gs] (:finished gs))]
      (game/run game-state finished? init-text verb-map)))
