(ns house.puzzles.oak-door
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.utils :as utils]))

(def unlocked-door (item/make ["oak door" "door"]
                              "It was massive, I think I said that."
                              :closed true))

(defn pre-bolt-unlock
  [gs item]
  (if (some #{"bolt"} (:names item))
    true
    "It was locked with a big bolt."))

(defn post-key-unlock
  [old gs]
  (let [door (utils/find-first gs "door")
        large-key (utils/find-first gs "large key")
        back-door (first (item/get-from (get-in gs [:room-map :back-hall1 :items]) "oak door"))]
    (if-not (:locked door)
      (-> gs
        (update-in [:room-map :back-hall1 :items] item/replace-from back-door unlocked-door)
        (utils/remove-item large-key)))))

(def locked-door (item/make ["oak door" "door"]
                            "The massive oak door was locked, I had to find the key."
                            :locked true
                            :unlock {:pre true
                                     :say "The large key did the trick. I left it in the lock."
                                     :post `post-key-unlock}
                            :open-with {:pre true
                                        :say "The large key did the trick. I left it in the lock."
                                        :post `post-key-unlock}))

(def moved-bolt (item/make "bolt" "It wasn't blocking the door anymore."
                           :move "No need to move it back."
                           :use "No need to move it back."))

(defn post-move-bolt
  [old gs]
  (let [bolt (utils/find-first gs "bolt")
        door (utils/find-first gs "door")]
    (-> gs
      (utils/replace-item bolt moved-bolt)
      (utils/replace-item door locked-door))))

(def bolted-door (item/make ["oak door" "door"]
                            "It was massive, I think I said that."
                            :locked true
                            :unlock {:pre `pre-bolt-unlock
                                     :say "I removed the bolt but the door seemed to be locked still, I had to find the key."
                                     :post `post-move-bolt}
                            :open-with {:pre `pre-bolt-unlock
                                        :say "I removed the bolt but the door seemed to be locked still, I had to find the key."
                                        :post `post-move-bolt}))
(def bolt (item/make "bolt"
                     "The big bolt was locking the door."
                     :unlocks bolted-door
                     :use {:pre true
                           :say "I removed the bolt but the door seemed to be locked still, I had to find the key."
                           :post `post-move-bolt}
                     :move {:pre true
                            :say "I removed the bolt but the door seemed to be locked still, I had to find the key."
                            :post `post-move-bolt}))

(defn key-pre
  [gs item]
  (if (some #{"oak door"} (:names item))
    true
    "That didn't work."))

(defn key-post
  "set unlocked and call the unlock postcondition"
  [old gs]
  (let [door (utils/find-first gs "door")
        new-gs (utils/replace-item gs door unlocked-door)]
    (post-key-unlock old new-gs)))

(def large-key (item/make ["large key" "key"] "Iron, I thought."
                          :take true
                          :unlocks locked-door
                          :use-with {:pre `key-pre
                                     :say "The large key did the trick. I left it in the lock."
                                     :post `key-post}))

; for use in room map
(defn oak-unlocked-front
  [gs]
  (let [door (utils/find-first gs "door")]
    (if (:locked door) "The oak door was locked."
      :back-hall1)))

(defn oak-unlocked-back
  [gs]
  (let [door (utils/find-first gs "oak door")]
    (if (:locked door) "The door was locked from the other side."
      :front-hall)))

(def oak-door-back (item/make ["oak door" "door"]
                              "It was massive, I think I said that. I had to find another way around."
                              :break "It was massive, I think I said that. I had to find another way around."
                              :closed true
                              :open "The door was locked from the other side."
                              :locked true))
