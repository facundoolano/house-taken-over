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

; light source for the dark plugin
(def lit-candle (item/make ["lit candle" "candle"]
                           "It wouldn't last forever."
                           :use "It was already on."
                           :light "It was already on."
                           :light-with "It was already on."
                           :lit true
                           :turns-left 5))

(defn burn-candle-hook
  [game-state]
  (let [candle (utils/find-first game-state "lit candle")
        updated (update-in candle [:turns-left] dec)]
    (if (= 0 (:turns-left updated))
      (do
        (utils/say " ")
        (utils/say "The candle burnt out.")
        (utils/remove-item game-state candle))
      (utils/replace-item game-state candle updated))))

(def burn-candle-plugin {:hooks {:after-handler burn-candle-hook}})

(defn post-light
  "Add a lit candle item to the inventory."
  [old gs]
  (update-in gs [:inventory] conj lit-candle))


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
