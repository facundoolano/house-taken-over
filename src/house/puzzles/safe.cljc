(ns house.puzzles.safe
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.utils :as utils]
            [advenjure.ui.input :refer [prompt-value]]
            [clojure.string :as string]
            #?(:clj [advenjure.async :refer [alet]]))
  #?(:cljs (:require-macros [advenjure.async :refer [alet]])))

(defn enter-combination
  [game-state]
  (alet [combo (prompt-value "Enter safe combination: ")
         combo (string/trim combo)
         responses ["No luck." "That wasn't it." "Nope."]]
    (cond
      (not (re-matches #"^\d{4}$" combo)) "That's not a four-digit number."
      ;accept with any Roca-related year
      (#{"1843" "1914" "1880" "1898"} combo) true
      :else (get responses (rand-int (count responses))))))

(defn open-safe
  [old-gs new-gs]
  (let [safe (utils/find-first new-gs "safe")
        new-safe (merge safe {:closed false
                              :description "It was open."
                              :close "Better left open."})]
    (utils/replace-item new-gs safe new-safe)))

; FIXME turn into house will
(def papers (item/make ["pile of papers" "papers"] "Legal stuff." :take {:pre true
                                                                         :points 250}))

(def safe (item/make ["safe" "safe box" "strongbox" "strong box"]
                     "The safe required a four-digit numerical combination to open."
                     :items #{papers}
                     :closed true
                     :open {:pre `enter-combination :say "Gotcha! It was open." :post `open-safe}
                     :use {:pre `enter-combination :say "Gotcha! It was open." :post `open-safe}
                     :unlock "You mean open it?"
                     :locked false
                     :lock "It was already locked."))

(defn reveal-safe [old gs]
  ;; need to manually remove the points because there are multiple verbs to do it
  ;; FIXME remove when synonyms implemented
  (let [portrait (utils/find-first gs "portrait")
        no-points (-> portrait
                      (update :pull dissoc :points)
                      (update :move dissoc :points))]
    (-> gs
        (utils/replace-item portrait no-points)
        (update-in [:room-map :hidden-room] room/add-item safe "The portrait covered a wall mounted safe box."))))

(def safe-portrait (item/make ["portrait" "painting"] "It had a little plaque that read “President Julio A. Roca”."
                              :pull {:pre true
                                     :say "Unsurprisingly the portrait was covering a wall mounted safe box."
                                     :points 20
                                     :post `reveal-safe}
                              :move {:pre true
                                     :say "Unsurprisingly the portrait was covering a wall mounted safe box."
                                     :points 20
                                     :post `reveal-safe}
                              :push "It didn't move in that direction."
                              :take "Kind of big to fit in my bag."))
