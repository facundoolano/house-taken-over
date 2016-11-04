(ns house.rooms.street
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]))

(def street (->
              (room/make "Front of house"
                " "
                :initial-description " "
                :default-go " ")
              (room/add-item (item/make "street" "It was very quiet at the time.") "The street door was unlocked.")
              (room/add-item (item/make "house"
                                        " "
                                        :enter :vestibule) "")
              (room/add-item (item/make ["door" "street door"]
                                        "They left it unlocked during the day."
                                        :open true
                                        :enter :vestibule) "")))
