(ns house.rooms.back
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def back-hall1 (room/make "Passageway"
                           " "
                           :known true))

(def back-hall2 (room/make "Passageway"
                           " "
                           :known true))

(def back-hall3 (room/make "Passageway" ; FIXME add doors
                           " "))

; TODO add chairs and tables
(def dinning (room/make "Dinning room"
                        "A dinning room with a big table and smaller one on the side."
                        :initial-description "The dinning table was big enough to fit twenty people. There were chairs all around it and a smaller table on the side."))

(def tapestry (room/make "Tapestries room" "A living room with Gobelin tapestries."))

; TODO add bookshelves
; TODO desk and a couple of reading chairs with lamps
(def library (room/make "Library" " "))

(def bedroom1 (room/make "Bedroom" " "))
(def bedroom2 (room/make "Bedroom" " "))
(def bedroom3 (room/make "Bedroom" " "))
