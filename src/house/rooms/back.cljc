(ns house.rooms.back
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.bath-window :refer [piano-stool]]
            [house.puzzles.catalog :refer [catalog]]
            [house.puzzles.safe :refer [safe-portrait]]
            [house.puzzles.candles :refer [candles candlestick]]))

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
                         :break "It was massive, I think I said that. I had to find another way around."
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

(def top-drawer (item/make ["drawer" "top drawer"]
                           "There was nothing special about it."
                           :open "Tablecloths, placemats and napkins."
                           :look-in "Tablecloths, placemats and napkins."))

(defn post-open
  [old gs]
  (let [drawer (utils/find-first gs "bottom drawer")
        new-drawer (update-in drawer [:open] dissoc :say)]
    (utils/replace-item gs drawer new-drawer)))

(def bottom-drawer (item/make ["drawer" "bottom drawer"]
                              "There was nothing special about it."
                              :closed true
                              :open {:pre true
                                     :say "A lot of silverware; utensils I didn’t knew existed. Also a box of candles."
                                     :post `post-open}
                              :items #{candles}))

(def dining (->
              (room/make "Dining room"
                         "A dining room with a table big enough to fit twenty people. There were ornate chairs all around it and a smaller table on the side. By the wall was a dinnerware cabinet with two drawers.")
              (room/add-item (item/make ["cabinet" "dinnerware cabinet"] "It had three shelves full of dinnerware and two drawers." :open "You mean a drawer?") "")
              (room/add-item top-drawer "")
              (room/add-item bottom-drawer "")
              (room/add-item (item/make ["dining table" "table"] "I lived in places smaller than that table.") "")
              (room/add-item (item/make ["table" "small table"] "I guessed it was used to hold dishes during dinner.") "")
              (room/add-item (item/make ["chair" "chairs"] "Dark wood mathcing the table. Arms and legs had elaborate carvings." :take "Those chairs were way too heavy to carry around.") "")))

; TODO react to using matches on fireplace?
(def tapestry (-> (room/make "Tapestries room"
                             "A living room with a huge tapestry covering the north wall, and two smaller ones on the opposite side, left and right of a door. There was a set of sofas around a fireplace, a drinks cabinet on a corner, and a piano.")
                  (room/add-item (item/make ["tapestry" "tapestries"] "All three depicted battles, historical events, I supposed." :take "I could carry infinite things in my bag, but not infinitely big things.") "")
                  (room/add-item (item/make "piano" "The lid was down and had a macramé tablecloth on top" :take "Funny." :open "Better left closed.") "")
                  (room/add-item piano-stool "By the piano was a little stool.")
                  (room/add-item (item/make ["tablecloth" "macramé" "macramé tablecloth" "cloth"] "Some diamond pattern." :take "It was useless.") "")
                  (room/add-item (item/make "fireplace" "The fireplace was empty, it probably hadn't been lit in years.") "")
                  (room/add-item (item/make ["sofa" "sofas"] "There were three of them, the larger in front of the fireplace and too shorter ones on the sides." :use "I wasn't tired.") "")
                  (room/add-item (item/make ["drinks cabinet" "drinks" "drink" "cabinet"] "It had a wide selection of bottles and glasses of all sorts."
                                            :take "There was no drinking on the job."
                                            :drink "There was no drinking on the job."
                                            :open "There was no drinking on the job."
                                            :use "There was no drinking on the job.") "")))

(def pipe (item/make ["pipe" "briar pipe"] "The wood was shiny."
                     :take "I wasn't touching that thing, it probably had several generations of germs growing in it."
                     :use "I wasn't touching that thing, it probably had several generations of germs growing in it."))

; TODO add the easter egg
(def parks-book (item/make ["book" "book on the table" "book on table"] "It was open a few pages before the end."
                           :take "No need to take it."
                           :read "TL;DR"))

(def armchair (item/make ["armchair" "green velvet armchair" "velvet armchair"]
                         "Green velvet. It had its back toward the door, as to avoid the possibility of an interruption."
                         :use "I wasn't tired."
                         :take "Way too heavy to carry around."))

(defn reveal-room [old gs]
  (update-in gs [:room-map] room/connect :library :north :hidden-room))


(def portrait (item/make ["portrait" "painting"]
                         "It depicted a man in that very same room, sitting on the green velvet armchair, smoking a pipe with a severe look in his eyes. It must have been painted by the end of the 19th century, judging by the clothes."
                         :pull {:pre true
                                :say "Pulling the frame of the portrait revealed it was fixed to a door that led to a hidden room."
                                :post `reveal-room}
                         :move {:pre true
                                :say "Pulling the frame of the portrait revealed it was fixed to a door that led to a hidden room."
                                :post `reveal-room}
                         :push "It didn't move in that direction."
                         :take "Kind of big to fit in my bag."))

(def shelves (item/make ["book" "books on the shelf" "books on shelf" "book on the shelf" "book on shelf" "books" "shelves" "shelf" "bookshelves" "bookcase"]
                        "More books than a man could possibly read in a lifetime."
                        :take "There were too many of them, better to use the catalog."))

(def library (->
              (room/make "Library"
                         "A library with bookshelves floor to ceiling, only interrupted by a window, the door to the corridor and a large portrait of a man."
                         :initial-description "A quick glimpse was enough to realize that the library was a prominent room in the house. The bookshelves went floor to ceiling on each wall, only interrupted by a window —notably larger than in other rooms—, the door to the corridor and a full-size portrait of a man on the northern wall.")
              (room/add-item catalog "Next to the door was a card cabinet.")
              (room/add-item (item/make ["side table" "table"] "On the side table were a briar pipe and an open book.") "Facing the window: a green velvet armchair with a side table.")
              (room/add-item (item/make "window" "The window was notably bigger than in the other rooms." :open "That was pointless.") "")
              (room/add-item pipe "")
              (room/add-item armchair "")
              (room/add-item portrait "")
              (room/add-item shelves "")
              (room/add-item parks-book "")))

; TODO add front door keys to drawer
(def hidden-room (->
                  (room/make "Hidden room" "An old and musty study."
                             :initial-description "The candlelight exposed an old study which was evidently excluded from the house cleaning routines; a musty smell and a thick layer of dust made it clear that no one had walked through the door in a long time."
                             :known true
                             :dark true)
                  (room/add-item (item/make "desk" "It had one drawer.") "There was a wide desk in the center of the room and behind it a portrait of an old man.")
                  (room/add-item (item/make "door" "The same as in the other rooms.") "A door led east to the corridor.")
                  (room/add-item (item/make ["drawer" "desk drawer"] "Nothing special about it"
                                            :closed true :item #{}) "")
                  (room/add-item safe-portrait "")
                  (room/add-item candlestick "On the desk were a candlestick and book.")
                  (room/add-item (item/make ["book" "book on desk"]
                                            "The book was titled “The Conquest of the Desert”."
                                            :read "It was a history book about some 19th century military campaign: “The Conquest of the Desert”."
                                            :take "I had no plans to read it.") "On the desk were a candlestick and book.")))

; TODO add mentioned items
; bedroom 2 and 3 are mirrored
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
