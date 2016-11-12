(ns house.rooms.back
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]))

; common corridor items
(def corridor (item/make ["corridor" "passage" "passageway" "hall" "hallway"]
                         "The corridor had a high roof and a squeaky wooden floor, covered by a rug. There were some paintings between the doors."))

(def paintings (item/make ["paintings" "painting" "portraits" "portraits"]
                          "I didn't know much about arts, but those looked expensive."
                          :take "They were good on the wall."))

(def floor (item/make "floor" "Old, wooden, noisy. Very much like the rest of the house."))

(def rug (item/make "rug" "An ornate long rug."))

; TODO add doors
(def back-hall3 (->
                  (room/make "Corridor"
                             "The end of a corridor with rooms to the east and west, and the door to the back garden"
                             :initial-description "My steps resounded throughout a long corridor, with a series of rooms on each side. On the other end was an oak door, ajar.")
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

; TODO add doors
(def back-hall2 (->
                  (room/make "Corridor"
                             "The middle section of a corridor with rooms to the east and west."
                             :initial-description "The rug that traversed the room wasn't enough to silence the creak of the floor, as if the house denounced my irruption."
                             :known true)
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

(def oak-door (item/make ["oak door" "door"]
                         "It was massive, I think I said that. I had to find another way around."
                         :closed true
                         :open "The door was locked from the other side."
                         :locked true))

; TODO add other doors
(def back-hall1 (->
                  (room/make "Corridor"
                             "The massive oak door was blocking my way to the front section of the house. There were rooms to the east and west."
                             :initial-description "The massive oak door separated the corridor from the front section of the house; it was slightly ajar. As I reached for the knob, the door was suddenly pulled back and slammed shut; sounds of a key and a bolt followed immediately.\n \nThey heard me coming and locked me out."
                             :known true)
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")
                  (room/add-item oak-door "")))

; TODO add chairs and tables
(def dinning (room/make "Dinning room"
                        "A dinning room with a big table and smaller one on the side."
                        :initial-description "The dinning table was big enough to fit twenty people. There were chairs all around it and a smaller table on the side."))

(def tapestry (room/make "Tapestries room" "A living room with Gobelin tapestries."))

; TODO add bookshelves
; TODO desk and a couple of reading chairs with lamps
(def library (room/make "Library" " "))

(def bedroom1 (room/make "Bedroom" " "))

; bedroom 2 and three are mirrored
; FIXME add mirror initial description
; FIXME add items
(def base-bedroom (room/make "Bedroom"
                             "An uninhabited bedroom. It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."
                             :initial-description "The bedroom was impeccably clean and far from being empty, yet I got the clear impression that it hadn't been occupied in years… It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."))

(def bedroom2 (assoc base-bedroom :id :bedroom2))
(def bedroom3 (assoc base-bedroom :id :bedroom3))
