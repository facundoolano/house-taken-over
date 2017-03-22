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

(def window (item/make ["window" "street"] "It was dark outside."
                       :break "I couldn’t, the window had bars on it."
                       :open "There was no benefit in doing that."))

;; TODO add camphor chest, table
(def armoire (item/make "armoire" "It must have contained several generations of women's clothes."
                        :open "I'm not the search women's clothes type of guy."))
(def vest (item/make "vest" "It was grey." :take "I wasn’t into knitting."))
(def basket (item/make ["basket" "knitting basket"] "It contained a halfway done vest."
                       :take "I wasn’t into knitting."))
(def needles (item/make ["needle" "needles" "knitting needles"] "The needles were scattered across the floor"
                        :take "I had enough pointy tools already."))
(def woman-bedroom (-> (room/make "Woman's bedroom"
                                  "A woman’s bedroom with a big sofa and scattered knitting tools."
                                  :initial-description "The woman’s bedroom was the first place in the house that gave me the clear impression of being inhabited. There was a big sofa near the window, where the woman had been working; the knitting tools demonstrated the interrupted activity: needles were scattered in opposite directions away from the basket, which contained a halfway done vest.")
                       (room/add-item armoire "There was also a bed with its night table and an old armoire.")
                       (room/add-item window "")
                       (room/add-item vest "")
                       (room/add-item basket "")
                       (room/add-item needles "")))

(def papers (item/make ["pile of papers" "papers"] "A pile of written papers."
                       :read "We like the house because, apart from its being old and spacious (in a day when old houses go down for a profitable auction of their construction materials), it keeps the memories of great‐grandparents, our paternal grandfather, our parents and the whole of childhood. Irene and I got used to staying in the house by ourselves, which is crazy, eight people could live in this place and not get in each other's way. We rise at seven in the morning and get the cleaning done, and about eleven I leave Irene to finish off whatever rooms and go to the kitchen. We lunch at noon precisely; then there is nothing left to do but a few dirty plates. It is pleasant to take lunch and commune with the great hollow, silent house, and it is enough for us just to keep it clean…"
                       :take "No need to carry that around."))

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

(def book (item/make "book" "L'école des indifférents by Jean Giraudoux." :read "I don't do french… books." :take "No, thanks."))

(def album (item/make ["stamps album" "album" "stamps"] "I guess with enough free time you can turn anything into a hobby."
                :take "Not a chance."
                :open "Not a chance."
                :read "Nothing to read in it."))

(def drawers (item/make ["desk drawers" "drawers"] "The rolltop desk was full of them."
                        :open "I had no time to go over the desk drawers, there were too many of them."))

(defn post-open
  [old gs]
  (let [box (utils/find-first gs "box")
        new-box (update-in box [:open] dissoc :say)]
    (utils/replace-item gs box new-box)))

(def cash (item/make ["bunch of cash" "cash" "money" "wads" "wads of cash"] "Around 15 thousand pesos, I estimated." :take true))
(def box (item/make "box" "Shoes, I guessed."
                    :take "I wasn’t interested in the box, but maybe in its contents."
                    :open {:pre true
                           :say "The box was full of wads of cash that I quickly estimated in around 15 thousand pesos."
                           :post `post-open}
                    :items #{cash}))
(def wardrobe (item/make "wardrobe" "Big, old and expensive, like everything in the house."
                         :closed true
                         :open {:pre true
                                :say "The wardrobe was full of man clothes. There was also a little box on its base."}
                         :items #{box}))

(def man-bedroom (->
                  (room/make "Man's bedroom" "A man’s bedroom with a bed, a night table, an wardrobe and a rolltop desk."
                             :initial-description "The man’s bedroom was less furnished than the rest of the rooms: there were an old bed with a night table, a big wardrobe and a rolltop desk on the opposite side of the room.")
                  (room/add-item rolltop-desk "")
                  (room/add-item window "On the southern wall was the window I saw from the street.")
                  (room/add-item book "There was a stamps album on the desk and a little book on the night table.")
                  (room/add-item (item/make ["night table" "table"]) "")
                  (room/add-item album "")
                  (room/add-item drawers "")
                  (room/add-item wardrobe "")))
