(ns house.rooms.front
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.envelope :refer [mailbox]]
            [house.puzzles.gate-door :refer [gate-door]]
            [house.puzzles.oak-door :refer [large-key]]))

;; FIXME turn radio off
(def radio (item/make "radio" "It was making an annoying noise: the transmission was over."
                      :light "It was already on."
                      :use "I didn't know my way around those things"))

(def wool (item/make ["thread of wool" "thread" "wool"] "It was coming from the east bedroom." :take "I had no use for that."))
(def clock (item/make ["clock" "longcase clock" "grandfather clock"] "The clock was stopped at five past nine." :open "This is not Day of the Tentacle."))

(def living (->
              (room/make "Living room"
                         "The central living room with bedrooms on either side, the gate door to the vestibule and the passage to the back of the house."
                         :initial-description "At the core of the building was the central living room, with bedrooms on either side. South was the wrought-iron gated door that led to the vestibule, and the street, and north the passage leading to the back section of the house."
                         :known true)
              (room/add-item mailbox "Just by the gate door was a bronze mailbox.")
              (room/add-item (item/make ["Passage" "passageway" "corridor"] "it led to the back section of the house" :enter :front-hall) "")
              (room/add-item (item/make ["table" "dining table"] "Not as big as the one in the back.") "There was a longcase clock and a dining table and not far from it a big radio.")
              (room/add-item radio "The radio was on, making a static noise.")
              (room/add-item wool "\n \nA thread of wool stupidly crossed the room, coming out of one of the bedrooms and disappearing under the gated door.")
              (room/add-item clock "")
              (room/add-item gate-door "")))

; TODO add sofa, bed, wardrobe, camphor chest, table, knitting basket
(def woman-bedroom (room/make "Woman's bedroom"
                              " "))

(def papers (item/make ["pile of papers" "papers"] "A pile of written papers."
                       :read "We like the house because, apart from its being old and spacious (in a day when old houses go down for a profitable auction of their construction materials), it keeps the memories of great‐grandparents, our paternal grandfather, our parents and the whole of childhood. Irene and I got used to staying in the house by ourselves, which is crazy, eight people could live in this place and not get in each other's way. We rise at seven in the morning and get the cleaning done, and about eleven I leave Irene to finish off whatever rooms and go to the kitchen. We lunch at noon precisely; then there is nothing left to do but a few dirty plates. It is pleasant to take lunch and commune with the great hollow, silent house, and it is enough for us just to keep it clean..."))

(defn post-desk-open
  [_ gs]
  (let [desk (utils/find-first gs "desk")
        new-desk (assoc desk
                        :description "a wooden rolltop desk, with the cover up."
                        :close "Better left open.")]
    (utils/replace-item gs desk new-desk)))

(def rolltop-desk (item/make ["rolltop desk" "desk"] "A wooden rolltop desk, with the cover down."
                             :closed true
                             :open {:pre true :say "I rolled up the cover." :post `post-desk-open}
                             :items #{papers large-key}))

(def man-bedroom (->
                  (room/make "Man's bedroom" " ")
                  (room/add-item rolltop-desk)))
