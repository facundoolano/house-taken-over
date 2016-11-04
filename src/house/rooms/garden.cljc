(ns house.rooms.garden
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def garden (room/make "Garden"
                       " "))

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
