(ns house.puzzles.back-door
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.utils :as utils]))

(defn crow-pre
  "If trying to open the back door do it, otherwise default message."
  [gs item]
  (cond
    (some #{"back door"} (:names item)) true
    (some #{"oak door"} (:names item)) "That would most likely have broken the crowbar."
    (= (:id item) "bath-window") "There was no need to break the window, just find a way to reach it."
    (= (:id item) "bath-window-reachable") "The crowbar didn't have the proper grip for the job, it was too long."
    (some #{"window"} (:names item)) "I didn't want to draw too much attention."
    :else "I didn't want to break into that."))

(defn can-go-out
  [gs]
  (if (get-in gs [:room-map :back-hall1 :visited] gs)
    :house-back
    "I had just got in, there was no point in going back."))

(defn crow-post
  "Open the door and connect the rooms."
  [old gs]
  (let [door (utils/find-first gs "door")
        new-door (item/make ["door" "back-door"]
                            "You could hardly tell that I, um, \"unlocked\" it."
                            :open true
                            :enter :back-hall3)
        new-map (-> (:room-map gs)
                    (room/one-way-connect :house-back :south :back-hall3)
                    (room/one-way-connect :back-hall3 :north `can-go-out))]
    (if-not (:locked door)
      (-> gs
        (assoc :room-map new-map)
        (utils/replace-item door new-door)))))

(defn crow-use-post
  "set door as unlocked and call crow-post"
  [old gs]
  (let [door (utils/find-first gs "door")
        new-door (dissoc door :locked)]
    (crow-post old (utils/replace-item gs door new-door))))

(def back-door (item/make ["door" "back door"]
                          "It didn't look particularly tough."
                          :break "Perhaps with the proper tooling."
                          :enter "It was locked."
                          :unlock {:say "Sure. I unlocked the hell out of it. The door was now open."
                                   :post `crow-post}
                          :open-with {:say "Easy job for the crowbar. The door was now open."
                                      :post `crow-post}
                          :locked true))

(def crowbar (item/make "crowbar" "satisfy all your breaking-into-places needs."
                        :take true
                        :unlocks back-door
                        :use-with {:pre `crow-pre
                                   :say "Easy job for the crowbar. The door was now open."
                                   :post `crow-use-post}))
