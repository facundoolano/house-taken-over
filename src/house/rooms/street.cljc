(ns house.rooms.street
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def street (->
              (room/make "Front of house"
                " "
                :initial-description " "
                :default-go " ")
              (room/add-item (item/make "street" "It was very quiet at the time.") "")
              (room/add-item (item/make "house"
                                        " "
                                        :enter :vestibule) "")
              (room/add-item (item/make ["door" "street door"]
                                        "They left it unlocked during the day."
                                        :open true
                                        :enter :vestibule) "")))

(def gate-door (item/make ["door" "gate door"]
                          "A wrought‐iron gated door"
                          :locked true))

; TODO add tiles, street door, house?
(def vestibule (-> (room/make "Vestibule"
                    "A vestibule with enameled tiles, and a wrought‐iron gated door."
                    :initial-description " ")
                   (room/add-item gate-door "")))


(def house-side (room/make "Side of house"
                           " "
                           :known true))

(def corner (room/make "Wall corner"
                       " "))

(def alley (room/make "Alley"
                      " "
                      :known true))
