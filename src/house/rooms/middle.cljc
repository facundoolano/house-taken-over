(ns house.rooms.middle
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.candles :refer [matches]]
            [house.puzzles.oak-door :refer [bolted-door bolt]]))

(def front-hall (->
                 (room/make "Corridor"
                            "The main passage carried on the other side of the oak door, connecting to the front of the house."
                            :initial-description "Standing at the end of the corridor I saw the hand pulling the gate door, across the living. The slam of the iron grating resounded for a second and then there was nothing else to be heard."
                            :known true)
                 (room/add-item bolted-door "")
                 (room/add-item bolt "")))

(def side-hall (room/make "Passageway"
                          "A narrow passageway connected the bathroom and kitchen with the main corridor of the house."
                          :initial-description "Just as they must have heard me walking out of the bathroom, I heard the muffled noise of their steps rushing through the living, just around the passageway."))

(def bathroom (->
                (room/make "Bathroom" "A bathroom with a lavatory, a toilet, a bathtub and an armoire."
                           :initial-description "I landed with my hands in a large bathtub.\n \nThe bathroom was much more spacious than actually needed; other than the lavatory, the toilet and the bathtub, the only piece of furniture was a half-empty armoire."
                           :points 150)
                (room/add-item (item/make ["bathtub" "bath" "thub"] "Pretty big." :use "I was good, thanks.") "")
                (room/add-item (item/make "armoire" "Soap and some towels, nothing of interest." :open "Soap and some towels, nothing of interest.") "")
                (room/add-item (item/make ["lavatory" "sink"] "It had been recently used." :use "I was good, thanks." :open "I was good, thanks.") "")
                (room/add-item (item/make "toilet" "There was nothing special about it." :look-in "No, thanks" :use "I was good, thanks.") "")
                (room/add-item (item/make "window" "The window I had used to get in. It was open."
                                          :closed false
                                          :close "Better left open."
                                          :use "I'd use doors from then on."
                                          :enter "I'd use doors from then on.") "")))

(def fridge (item/make "fridge" "It looked like it could hold a lot of food."
                       :closed true
                       :open "Taking over the house was one thing, but I don’t mess with someone else’s fridge."))

(def kitchen (->
              (room/make "Kitchen" "A modest kitchen."
                         :initial-description "The kitchen wasn't especially big, considering the rest of the house. Then again, it had likely been designed for the servants' use.")
              (room/add-item fridge "There were the fridge, the oven, a counter with a sink and some cabinets.")
              (room/add-item (item/make "sink" "There was nothing special about it." :open "I didn’t need water at the moment." :use "I didn’t need water at the moment.") "")
              (room/add-item (item/make ["kettle" "mate" "mate cup" "cup"] "Didn't knew elegant people drank mate." :use "No time for drinking mate." :take "No time for drinking mate.") "On the counter were a kettle and a mate cup.")
              (room/add-item (item/make "oven" "" :closed true :open "Nah." :use "Didn't feel like cooking.") "")
              (room/add-item (item/make "cabinet" "Silverware and the like." :closed true :open "Silverware and the like.") "")
              (room/add-item matches "Next to the oven was a box of matches.")))
