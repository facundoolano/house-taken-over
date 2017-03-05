(ns house.puzzles.envelope
  (:require [advenjure.items :as item]
            [advenjure.utils :as utils]))

(def letter (item/make ["letter"] "It was a letter from a lawyer."
                       :read "…I appreciate that you don't want to stir up the memory of your father, but without the deed you can't claim ownership of the house, or any of the properties for that matter. I insist that you must search through his papers…"))

(defn open-envelope [old gs]
  (let [envelope (utils/find-first gs "envelope")]
    (utils/replace-item gs envelope letter)))

(def inv-envelope (item/make "envelope"
                             "The sender was a lawyer from downtown."
                             :read "I needed to open it first."
                             :open {:pre true :say "It contained a letter from a lawyer." :post `open-envelope}))

(defn take-envelope
  "Remove the envelope from both the vestibule and the living, and replace the
  one in the inventory."
  [old gs]
  (let [slot-envelope (utils/find-first gs "envelope")
        slot (first (item/get-from (get-in gs [:room-map :vestibule :items]) "slot"))
        new-slot (assoc slot :look-in "It seemed empty.")]
    (println new-slot)
    (-> gs
      (update-in [:room-map :vestibule :items] item/replace-from slot new-slot)
      (update-in [:room-map :vestibule :items] item/remove-from slot-envelope)
      (update-in [:room-map :living :items] item/remove-from slot-envelope)
      (utils/replace-item slot-envelope inv-envelope))))

(def slot-envelope (item/make ["envelope" "paper"] "I couldn't see it from there."
                              :take {:pre true :post `take-envelope}))

(defn add-envelope [old gs]
  (update-in gs [:room-map :vestibule :items] conj slot-envelope))

(def mail-slot (item/make ["mail slot" "slot"] "Bronze."
                          :id "mail-slot"
                          :open "I couldn't open the slot, but maybe reach inside of it."
                          :close false
                          :take "I couldn't take the slot, but maybe reach inside of it."
                          :reach {:pre true
                                  :say "I could reach a piece of paper, probably an envelope, near the edge of the slot."
                                  :post `add-envelope}
                          :look-in {:pre true
                                    :say "I could reach a piece of paper, probably an envelope, near the edge of the slot."
                                    :post `add-envelope}))

(def mailbox (item/make ["mailbox" "mail box"]
                        "A bronze mailbox."
                        :closed true
                        :items #{slot-envelope}))
