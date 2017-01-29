(ns house.puzzles.candles
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.change-rooms :refer [change-rooms]]
            [advenjure.utils :as utils]))

(def candlestick (item/make ["candlestick" "candle stick"] "Bronze."
                             :take "There was no use in carrying that around."))

(def candlestick-with-candle (item/make ["candlestick" "candle stick" "candle"]
                                        "The candle faintly lighted the room."
                                        :take "There was no use in carrying that around."
                                        :lit true))

(defn pre-candlestick
  [gs item]
  (cond
    (and (some #{"candlestick"} (:names item)) (:lit item)) "It had a candle already."
    (some #{"candlestick"} (:names item)) true
    (some #{"candle"} (:names item)) "Maybe the lit one."
    :else "That couldn't hold a candle."))

(defn post-candlestick
  [old gs]
  (let [candle (utils/find-first gs "lit candle")
        candlestick (utils/find-first gs "candlestick")]
    (-> gs
      (utils/remove-item candle)
      (utils/replace-item candlestick candlestick-with-candle))))


(defn light-match [gs]
  (if (= :dark-room (:current-room gs))
    "The match briefly lighted what appeared to be a bedroom, but then it burned out."
    "I lighted a match, but it quickly burnt out."))

(defn pre-match [gs item]
  (cond
    (utils/find-first gs "lit candle") "I already had a lit candle."
    (some #{"candles"} (:names item)) true
    :else "I didn't want to burn that."))

(defn pre-candle [gs item]
  (cond
    (utils/find-first gs "lit candle") "I already had a lit candle."
    (some #{"box of matches"} (:names item)) true
    :else "That wouldn't light a candle."))

; light source for the dark plugin
(def lit-candle (item/make ["lit candle" "candle"]
                           "It wouldn't last forever."
                           :use "It was already on."
                           :light "It was already on."
                           :light-with "It was already on."
                           :lit true
                           :turns-left 5
                           :use-with {:pre `pre-candlestick
                                      :say "I put the candle on the candlestick."
                                      :post `post-candlestick}
                           :put {:pre `pre-candlestick
                                 :say "I put the candle on the candlestick."
                                 :post `post-candlestick}))

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


(def matches (item/make ["box of matches" "match box" "matchbox" "matches" "match" "box"]
                        "It was full of matches."
                        :look-in "It was full of matches."
                        :take true
                        :use `light-match
                        :light `light-match
                        :use-with {:pre `pre-match
                                   :post `post-light
                                   :say "I lit a candle with the match."}))

(def candles (item/make ["box of candles" "candles" "candle" "box"] "Fancy."
                        :take true
                        :use "That usually requires fire."
                        :light "That usually requires fire."
                        :light-with {:pre `pre-candle
                                     :post `post-light
                                     :say "I lit a candle with the match."}))
