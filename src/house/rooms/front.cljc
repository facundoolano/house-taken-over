(ns house.rooms.front
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.envelope :refer [mailbox]]
            [house.puzzles.oak-door :refer [large-key]]))


(def living (->
              (room/make "Living room"
                         "There were bedrooms on either side of the central living room and a corridor leading to the back section."
                         :known true)
              (room/add-item mailbox "Just by the gate door was a bronze mailbox.")))

; TODO add sofa, bed, wardrobe, camphor chest, table, knitting basket
(def woman-bedroom (room/make "Woman's bedroom"
                              " "))

; TODO add rolltop desk and put large-key inside of it
(def man-bedroom (->
                  (room/make "Man's bedroom" " ")
                  (room/add-item large-key)))
