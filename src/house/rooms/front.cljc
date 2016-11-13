(ns house.rooms.front
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.rooms.common :refer [mailbox]]))


(def living (->
              (room/make "Living room"
                         "There were bedrooms on either side of the central living room and a corridor leading to the back section.")
              (room/add-item mailbox "Just by the gate door was a bronze mailbox.")))

; TODO add sofa, bed, wardrobe, camphor chest, table, knitting basket
(def woman-bedroom (room/make "Woman's bedroom"
                              " "))


(def man-bedroom (room/make "Man's bedroom" " "))
