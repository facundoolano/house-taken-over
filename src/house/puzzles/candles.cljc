(ns house.puzzles.candles
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.change-rooms :refer [change-rooms]]
            [advenjure.utils :as utils]))


(defn light-match [gs]
  (if (= :dark-room (:current-room gs))
    "The match briefly lighted what appeared to be a bedroom, but then it burned out."
    "I lighted a match, but it quickly burnt out."))

; FIXME not if already carrying one
(defn pre-match [gs item]
  (if (some #{"candles"} (:names item))
    true
    "I didn't want to burn that."))

; FIXME not if already carrying one
(defn pre-candle [gs item]
  (if (some #{"box of matches"} (:names item))
    true
    "That wouldn't light a candle."))

(def lit-candle (item/make ["lit candle" "candle"]
                           "It wouldn't last forever."
                           :use "It was already on."
                           :light "It was already on."
                           :light-with "It was already on."
                           :turns-left 10)) ; TODO use a middleware to decrease on each turn until it burns out

(defn post-light
  "Add a lit candle item to the inventory.
  If secret room already discovered, replace the dark room with the lit one.
  If it's currently there perform a room change."
  [old gs]
  (let [found-room? #((:events %) :found-hidden-room)
        is-in-room? #(= (:current-room %) :dark-room)]
    (cond-> gs
      (found-room? gs) (update-in [:room-map] room/connect :library :north :hidden-room)
      (is-in-room? gs) (change-rooms :hidden-room)
      :always (update-in [:inventory] conj lit-candle))))


(def matches (item/make ["box of matches" "match box" "matchbox" "matches" "match"]
                        "It was full of matches."
                        :look-in "It was full of matches."
                        :take true
                        :use `light-match
                        :light `light-match
                        :use-with {:pre `pre-match
                                   :post `post-light
                                   :say "I lit a candle with the match."}))

(def candles (item/make ["box of candles" "candles" "candle"] "Fancy."
                        :take true
                        :use "That usually requires fire."
                        :light "That usually requires fire."
                        :light-with {:pre `pre-candle
                                     :post `post-light
                                     :say "I lit a candle with the match."}))
