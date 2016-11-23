(ns house.game
  (:require [advenjure.game :as game]
            [advenjure.items :as item]
            [house.room-map :refer [room-map]]
            [advenjure.verbs :refer [make-item-handler make-compound-item-handler make-say-verb]]
            [advenjure.verb-map :refer [default-map add-verb]]))

(def break (make-item-handler "break" :break))
(def drop_ (make-say-verb "I had plenty of room in my bag, no need to be dropping stuff."))

(def light (make-item-handler "light" :light))
(def light-with (make-compound-item-handler "light" :light-with))

(def verb-map (-> default-map
                  (add-verb ["^break (?<item>.*)" "^break in (?<item>.*)"  "^break into (?<item>.*)" "^break$"] break)
                  (add-verb ["^drop (?<item>.*)" "^drop$"] drop_)
                  (add-verb ["^light (?<item>.*)" "^light$"
                             "^turn (?<item>.*)" "^turn on (?<item>.*)"
                             "^turn (?<item>.*) on" "^turn on$" "^turn$"] light)
                  (add-verb ["^light (?<item1>.*) with (?<item2>.*)" "^light (?<item1>.*) with$"
                             "^turn (?<item1>.*) with (?<item2>.*)"
                             "^turn on (?<item1>.*) with (?<item2>.*)"
                             "^turn (?<item1>.*) on with (?<item2>.*)"
                             "^turn (?<item1>.*) on with"
                             "^turn (?<item1>.*) with"
                             "^turn on (?<item1>.*) with$"] light-with)))

(def init-text (str "\nWe were easing into our forties with the unvoiced concept that the quiet, simple marriage of sister and brother was the indispensable end to a line established in this house by our grandparents. We would die here someday, obscure and distant cousins would inherit the place, have it torn down, sell the bricks and get rich on the building plot; or more justly and better yet, we would topple it ourselves before it was too late."
                    "\n \nJulio Cortázar, \"House Taken Over\"."
                    "\n \nWorks of fiction contain a single plot, with all its imaginable permutations. Those of a philosophical nature invariably include both the thesis and the antithesis, the rigorous pro and con of a doctrine. A book which does not contain its counterbook is considered incomplete."
                    "\n \nJorge Luis Borges."))

(defn run-game []
  (let [game-state (game/make room-map :back-hall3) ;FIXME :street)
        finished? (fn [gs] (:finished gs))]
      (game/run game-state finished? init-text verb-map)))
