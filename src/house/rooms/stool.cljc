(ns house.rooms.stool
  (:require [advenjure.items :as item]
            [advenjure.utils :as utils]))


(def piano-stool (item/make ["piano stool" "stool"] "About twenty inches, easy to move around."
                            :use "I didn't want to sit."
                            :take true))

(def after-stool (item/make ["piano stool" "stool"] "Good times."
                            :use "I didn't want to sit."
                            :take "I was done using that."))
