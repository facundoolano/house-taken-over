(ns house.rooms.front
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))


(def living (room/make "Living room"
                       "There were bedrooms on either side of the central living room and a corridor leading to the back section."))

; TODO add sofa, bed, wardrobe, camphor chest, table, knitting basket
(def woman-bedroom (room/make "Woman's bedroom"
                              " "))


(def man-bedroom (room/make "Man's bedroom" " "))
