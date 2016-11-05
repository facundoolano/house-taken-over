(ns house.rooms.garden
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))


(def garden (->
              (room/make "Garden"
                         "The garden ended on a long wall, partially covered by a creeping fig."
                         :initial-description "I made sure that no one was looking, and climbed up the fig.\nI got down on the other side of the wall, in a garden.")
              (room/add-item (item/make ["path" "stone path"] "It led back to the house.") "A stone path led south to the back entrance of the house.")
              (room/add-item (item/make ["shed" "wooden shed"] "It looked a bit neglected." :enter :shed) "There was an old wooden shed on the east corner.")))

(def shed (room/make "Shed"
                     " "
                     :known true))

(def house-back (room/make "Back of house"
                           " "
                           :known true))

(def west-passage (room/make "West passage"
                             " "))

(def east-passage (room/make "East passage"
                             " "))
