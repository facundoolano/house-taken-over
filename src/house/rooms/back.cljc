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

(defn make-door [extra-name goes-to]
   (item/make ["door" extra-name]
      "A wooden door."
      :closed true
      :enter goes-to))

(def back-hall3 (->
                  (room/make "Corridor"
                             "The end of a corridor with rooms to the east and west, and the door to the back garden"
                             :initial-description "My steps resounded throughout a long corridor, with a series of rooms on each side. On the other end was an oak door, ajar.")
                  (room/add-item (make-door "west door" :bedroom2) "")
                  (room/add-item (make-door "east door" :bedroom3) "")
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

(def fake-door (item/make ["door" "west door"]
                          "It was the same as the other doors in the corridor, except it didn't have a doorknob."
                          :open "I couldn't open the west door, it didn't have a doorknob."))

(def back-hall2 (->
                  (room/make "Corridor"
                             "The middle section of a corridor. There were doors to the east and west, but the latter didn't have a doorknob."
                             :initial-description "The rug that traversed the room wasn't enough to silence the creak of the floor, as if the house denounced my irruption."
                             :known true)
                  (room/add-item fake-door "")
                  (room/add-item (make-door "east door" :tapestry) "")
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

(def oak-door (item/make ["oak door" "door"]
                         "It was massive, I think I said that. I had to find another way around."
                         :closed true
                         :open "The door was locked from the other side."
                         :locked true))

(def back-hall1 (->
                  (room/make "Corridor"
                             "The massive oak door was blocking my way to the front section of the house. There were rooms to the east and west."
                             :initial-description "The massive oak door separated the corridor from the front section of the house; it was slightly ajar. As I reached for the knob, the door was suddenly pulled back and slammed shut; sounds of a key and a bolt followed immediately.\n \nThey heard me coming and they locked me out."
                             :known true)
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")
                  (room/add-item (make-door "west door" :library) "")
                  (room/add-item (make-door "east door" :dining) "")
                  (room/add-item oak-door "")))

; TODO add chairs and tables
(def dining (room/make "Dining room"
                       "A dining room with a big table and smaller one on the side."
                       :initial-description "The dining table was big enough to fit twenty people. There were chairs all around it and a smaller table on the side."))

(def tapestry (room/make "Tapestries room" "A living room with Gobelin tapestries."))

; TODO add bookshelves
; TODO desk and a couple of reading chairs with lamps
(def library (room/make "Library" " "))

(def bedroom1 (room/make "Bedroom" " "))

; TODO add mentioned items
; bedroom 2 and three are mirrored
(def base-bedroom (room/make "Bedroom"
                             "An uninhabited bedroom. It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."
                             :initial-description "The bedroom was impeccably clean and far from being empty, yet I got the clear impression that it hadn't been occupied in years… It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."))

(defn back-bedroom-post
   "The intial description of the second mirror entered room references the other."
   [old gs]
   (let [current (:current-room gs)
         otherkw (get {:bedroom2 :bedroom3 :bedroom3 :bedroom2} current)]
      (-> gs
         (assoc-in [:room-map otherkw :initial-description] "The room was a perfect mirror of the one across the hall: same furniture, same immaculacy and lifelessness")
         ; drop conditions
         (assoc-in [:room-map :back-hall3 :west] :bedroom2)
         (assoc-in [:room-map :back-hall3 :east] :bedroom3))))

(def bedroom2 (assoc base-bedroom :id :bedroom2))
(def bedroom3 (assoc base-bedroom :id :bedroom3))
