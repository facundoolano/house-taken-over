(ns house.rooms.garden
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.bath-window :refer [bath-window screwdriver west-wall]]
            [house.puzzles.back-door :refer [back-door crowbar]]))

(def garden (->
              (room/make "Garden"
                         "The garden ended on a long wall, partially covered by a creeping fig."
                         :initial-description "I made sure that no one was looking, and climbed up the fig.\n \nI got down on the other side of the wall, in a garden."
                         :points 100)
              (room/add-item (item/make "wall" "A plastered brick wall, about twice my height." :climb-up "The fig wasn't strong enough to climb up on this side of the wall.") "")
              (room/add-item (item/make ["creeping fig" "fig"] "It was a bit more under control than on the other side." :climb-up "The fig wasn't strong enough to climb up on this side of the wall.") "")
              (room/add-item (item/make ["path" "stone path"] "It led back to the house.") "A stone path led south to the back entrance of the house.")
              (room/add-item (item/make ["shed" "wooden shed"] "It looked a bit neglected." :enter :shed) "There was an old wooden shed on the east corner.")))

(def hammer (item/make "hammer" "The head was a bit loose but still usable."
                       :take true
                       :use-with "Everything looking like a nail, huh?"))

(defn post-open
  [old gs]
  (let [drawer (utils/find-first gs "drawer")
        new-drawer (update-in drawer [:open] dissoc :say)]
    (utils/replace-item gs drawer new-drawer)))

(def drawer (item/make ["drawer"] "Just a drawer."
                       :closed true
                       :open {:pre true
                              :say "There was a lot of useless junk inside the drawer, and also a rusty screwdriver."
                              :post `post-open}
                       :items #{screwdriver}))

(def shed (->
            (room/make "Shed"
                       "The shed was barely big enough for me to stand inside. There was a brief work table with a drawer against one of the walls and a tool board against the other. Right by the door was an old lawn mower."
                       :known true)
            (room/add-item (item/make ["tool board" "tools" "board" "toolset"]
                                      "Only a mid-size hammer and a crowbar were left of what appeared to have been a big toolset."
                                      :take "Not the entire thing!") "")
            (room/add-item (item/make ["table" "work table"] "Seemed too small to get any real work done. It had one drawer.") "")
            (room/add-item drawer "")
            (room/add-item hammer "")
            (room/add-item crowbar "")
            (room/add-item (item/make ["lawn mower" "mower"]
                                      "That thing surely had seen better days."
                                      :take "The lawn mower was too big to carry around, not to mention useless.") "")))

(defn can-enter
  [gs]
  (let [door (utils/find-first gs "door")]
    (if (:locked door) "The door was locked." :back-hall3)))

(def house-back (->
                  (room/make "Back of house"
                             "The stone path crossed the garden up to the back door of the house. There was a weedy passage on either side, between the building and the garden wall."
                             :initial-description "It was getting dark outside."
                             :visited true ; I make it visited by default b/c I'll save initial-description for later on, when it's dark.
                             :known true)
                  (room/add-item back-door "")
                  (room/add-item (item/make "house" "It looked even bigger from this side."
                                                    :enter `can-enter
                                                    :break "Perhaps with the proper tooling.") "")))

; FIXME add wall, maybe accept use with stool
(def west-passage (->
                    (room/make "West passage"
                               "The western passage ended up in a wall with a high window, probably of a bathroom."
                               :up "There was that window, but I couldn't reach it from the ground level.")
                    (room/add-item bath-window "")
                    (room/add-item west-wall "")))

(def room-window (item/make "window"
                            "It was a woman’s bedroom by the look of the furniture. She was on a sofa, knitting. A man was reading next to her. They didn’t seem to pay much attention to each other."
                            :look-in "It was a woman’s bedroom by the look of the furniture. She was on a sofa, knitting. A man was reading next to her. They didn’t seem to pay much attention to each other."
                            :break "I didn't want to draw too much attention."
                            :enter "I didn't want to draw too much attention."))

(def east-passage (->
                    (room/make "East passage"
                               "The eastern passage ended up in the back of one of the bedrooms I saw from the street. It had a window.")
                    (room/add-item room-window "")))
