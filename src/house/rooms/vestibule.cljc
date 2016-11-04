(ns house.rooms.vestibule
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def gate-door (item/make ["door" "gate door"]
                          "A wrought‐iron gated door"
                          :locked true))

; TODO add tiles, street door, house?
(def vestibule (-> (room/make "Vestibule"
                    "A vestibule with enameled tiles, and a wrought‐iron gated door."
                    :initial-description " ")
                   (room/add-item gate-door "")))
