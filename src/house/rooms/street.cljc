(ns house.rooms.street
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [advenjure.change-rooms :refer [change-rooms]]
            [house.puzzles.envelope :refer [mail-slot]]))

(def street (->
              (room/make "Front of the house"
                         "The house, big and old, was on the corner of the block. On every side of the door was a large window."
                         :initial-description "I arrived on foot, without being noticed. The house, big and old, was placed on a quiet corner of a not-so-quiet street.\n \nThe house was big and old and I had to take it over."
                         :default-go "I didn't want too go too far away from the house.")

              (room/add-item (item/make "street" "It was very quiet at the time.") "")
              (room/add-item (item/make ["window" "left window"]
                                        "Through the left window I saw a bedroom. There was a middle-aged man inside, reading a book."
                                        :look-in "Through the left window I saw a bedroom. There was a middle-aged man inside, reading a book.") "")
              (room/add-item (item/make ["window" "right window"]
                                        "I saw a bedroom through the right window. There was a woman on a sofa, knitting."
                                        :look-in "I saw a bedroom through the right window. There was a woman on a sofa, knitting.") "")
              (room/add-item (item/make "man" "He was reading." :talk "He couldn't hear me.") "")
              (room/add-item (item/make "woman" "She was knitting." :talk "She couldn't hear me.") "")
              (room/add-item (item/make "house"
                                        "One of those aristocratic buildings that could easily accommodate four families but was barely inhabited instead."
                                        :break "Why? The street door was open."
                                        :enter :vestibule) "")
              (room/add-item (item/make ["door" "street door"]
                                        "They left it unlocked during the day."
                                        :open true
                                        :break "Why? It was open."
                                        :enter :vestibule) "")))

(def gate-door (item/make ["door" "gate door"]
                          "A wrought‐iron gated door."
                          :enter "It was locked."
                          :break "I couldn't break through the front door in broad daylight. Besides the door seemed pretty strong."
                          :locked true))

(def v-house (item/make ["house"] "The vestibule painted a good picture of what the insides were probably like."
                        :enter "The gate door was locked."
                        :break "I couldn't break through the front door in broad daylight. Besides the door seemed pretty strong."))

(def vestibule (-> (room/make "Vestibule"
                    "A vestibule with enameled tiles, and a wrought‐iron gated door."
                    :initial-description "The street door gave way to a vestibule with enameled tiles, and a wrought‐iron gated door.")
                   (room/add-item gate-door "")
                   (room/add-item v-house "")
                   (room/add-item (item/make ["street door"] "They left it unlocked during the day.") "")
                   (room/add-item (item/make ["tiles" "enameled tiles"] "I imagined those tiles could be easily removed and sold at a high price.") "")
                   (room/add-item mail-slot "By the door was a bronze mail slot.")))

(def wall (item/make "wall"
                     "A plastered brick wall, about twice my height."
                     :climb-up "I needed to hold on to something to climb it."))

(def house-side (->
                  (room/make "Side of the house"
                             "The western side of the house, just around the corner, turned into a high wall that stretched north."
                             :initial-description "Walking by the side of the house I realized it was even bigger than it appeared from the front."
                             :known true
                             :up "Maybe there was a way to climb up that wall.")

                  (room/add-item wall "")))

(def corner (->
              (room/make "Wall corner"
                         "The high wall turned east into a narrow alley."
                         :up "Maybe there was a way to climb up that wall.")
              (room/add-item wall "")))

(defn post-hold [_ gs] (change-rooms gs :garden))

(def fig (item/make ["creeping fig" "fig" "branch" "branches"]
                    "The fig was uneven, as if it hadn't been taken care of in a while. Strong branches were reachable behind the outer layer of leaves."
                    :climb-up :garden
                    :take "It was strongly attached to the wall."
                    :hold {:post `post-hold}))

(def alley (->
             (room/make "Alley"
                        "The back wall followed the alley for about twenty meters. A thick creeping fig covered the farther part of the wall."
                        :known true
                        :up "Maybe there was a way to climb up that wall.")
             (room/add-item fig "")
             (room/add-item wall "")))
