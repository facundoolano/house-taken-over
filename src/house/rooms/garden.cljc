(ns house.rooms.garden
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def garden (->
              (room/make "Garden"
                         "The garden ended on a long wall, partially covered by a creeping fig."
                         :initial-description "I made sure that no one was looking, and climbed up the fig.\nI got down on the other side of the wall, in a garden.")
              (room/add-item (item/make "wall" "A plastered brick wall, about twice my height." :climb-up "The fig wasn't strong enough to climb up on this side of the wall.") "")
              (room/add-item (item/make ["creeping fig" "fig"] "It was a bit more under control than on the other side." :climb-up "The fig wasn't strong enough to climb up on this side of the wall.") "")
              (room/add-item (item/make ["path" "stone path"] "It led back to the house.") "A stone path led south to the back entrance of the house.")
              (room/add-item (item/make ["shed" "wooden shed"] "It looked a bit neglected." :enter :shed) "There was an old wooden shed on the east corner.")))


(defn crow-pre
  "If trying to open the back door do it, otherwise default message."
  [gs item]
  (if (some #{"back door"} (:names item))
    true
    "I didn't want to break into that."))

(defn crow-post
  "Open the door and connect the rooms."
  [old gs]
  (let [door (utils/find-first gs "door")
        new-door (item/make ["back door" "door"]
                            "You could hardly tell that I, um, \"unlocked\" it."
                            :open true
                            :enter :back-hall3)
        new-map (room/connect (:room-map gs) :house-back :south :back-hall3)]
    (if-not (:locked door)
      (-> gs
        (assoc :room-map new-map)
        (utils/replace-item door new-door)))))

(def back-door (item/make ["door" "back door"]
                          "It didn't look particularly tough."
                          :break "Perhaps with the proper tooling."
                          :enter "It was locked."
                          :unlock {:say "Sure. I unlocked the hell out of it. The door was now open."
                                   :post `crow-post}
                          :open-with {:say "Easy job for the crowbar. The door was now open."
                                      :post `crow-post}
                          :locked true))

(def crowbar (item/make "crowbar" "satisfy all your breaking-into-places needs."
                        :take true
                        :unlocks back-door
                        :use-with {:pre `crow-pre
                                   :say "Easy job for the crowbar. The door was now open."
                                   :post `crow-post}))

(def screwdriver (item/make "screwdriver" "the dust and the rust were competing to see who'd take over the thing first."
                            :use-with "I didn't see any screws."
                            :take true))

(def hammer (item/make "hammer" "The head was a bit loose but still usable."
                       :take true
                       :use-with "Everything looking like a nail, huh?"))

; FIXME add drawer with screwdriver
; FIXME add table
; TODO custom text when opening the drawer
(def shed (->
            (room/make "Shed"
                       "The shed was barely big enough for me to stand inside. There was a brief work table with a drawer against one of the walls and a tool board against the other. Right by the door was an old lawn mower."
                       :known true)
            (room/add-item (item/make ["tool board" "tools" "board" "toolset"]
                                      "Only a mid-size hammer and a crowbar were left of what appeared to have been a big toolset."
                                      :take "Not the entire thing!") "")
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
                             :known true)
                  (room/add-item back-door "")
                  (room/add-item (item/make "house" "It looked even bigger from this side."
                                                    :enter `can-enter
                                                    :break "Perhaps with the proper tooling."))))
; TODO react to crowbar?
(def bath-window (item/make "window"
                            "It was too high to look inside from where I was standing."
                            :look-in "It was too high to look inside from where I was standing."
                            :open "I could reach the border of the window but not open it from the ground level."
                            :use "I could reach the border of the window but not open it from the ground level."
                            :enter "I could reach the border of the window but not open it from the ground level."
                            :break "There was no need to do that."))
(def west-passage (->
                    (room/make "West passage"
                               "The western passage ended up in a wall with a high window, probably of a bathroom.")
                    (room/add-item bath-window "")))

; TODO react to crowbar?
(def east-passage (->
                    (room/make "East passage"
                               "The eastern passage ended up in the back of one of the bedrooms I saw from the street. It had a window.")
                    (room/add-item (item/make "window"
                                              "It was a woman’s bedroom by the look of the furniture. She was on a sofa, knitting. A man was reading next to her. They didn’t seem to pay much attention to each other."
                                              :look-in "It was a woman’s bedroom by the look of the furniture. She was on a sofa, knitting. A man was reading next to her. They didn’t seem to pay much attention to each other."
                                              :break "I didn't want to draw too much attention."
                                              :enter "I didn't want to draw too much attention.")
                                  "")))
