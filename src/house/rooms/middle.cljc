(ns house.rooms.middle
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def front-hall (->
                 (room/make "Passageway"
                            "A corridor ending in a massive oak door and a narrow passageway turning west.")

                 (room/add-item (item/make ["door" "oak door"] "It was massive, I think I said that.") "")))

(def side-hall (room/make "Passageway" "This narrow passage led to the kitchen and the bath."))

; TODO bath, toilette, lavatory, cabinet
(def bathroom (room/make "Bath" "The bathroom."))

(def kitchen (room/make "Kitchen" " "))
