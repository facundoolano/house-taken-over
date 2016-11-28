(ns house.puzzles.safe
  (:require [advenjure.items :as item]
            [advenjure.utils :as utils]))

; TODO prompt to open safe, add house will inside
(def safe (item/make ["safe" "safe box" "strongbox" "strong box"]
                     "The safe required a four-digit numerical combination to open."))

(defn reveal-safe [old gs]
  (update-in gs [:room-map :hidden-room] room/add-item safe "The portrait covered a wall mounted safe box."))

(def safe-portrait (item/make ["portrait" "painting"] "It had a little plaque that read “President Julio A. Roca”."
                              :pull {:pre true
                                     :say "Unsurprisingly the portrait was covering a wall mounted safe box."
                                     :post `reveal-safe}
                              :move {:pre true
                                     :say "Unsurprisingly the portrait was covering a wall mounted safe box."
                                     :post `reveal-safe}
                              :push "It didn't move in that direction."
                              :take "Kind of big to fit in my bag."))
