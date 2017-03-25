(ns house.puzzles.gate-door
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.utils :as utils]))

(defn key-post
  "set unlocked."
  [old gs]
  (let [door (utils/find-first gs "door")
        unlocked-door (assoc door :locked false)]
    (utils/replace-item gs door unlocked-door)))

(def gate-door (item/make ["door" "gate door"]
                          "A wrought‐iron gated door."
                          :locked true
                          :unlock {:say "After some trial and error, one of the keys opened the door."
                                   :points 500}
                          :open-with {:say "After some trial and error, one of the keys opened the door."
                                      :points 500}))

(def key-set (item/make ["key set" "set of keys" "keys"]
                        "A ring with about twenty keys of differt sizes."
                        :take true
                        :unlocks gate-door
                        :use-with {:say "After some trial and error, one of the keys opened the door."
                                   :points 500
                                   :post `key-post}))

(defn gate-unlocked
  [gs]
  (let [door (utils/find-first gs "gate door")]
    (if (:locked door) "The gate door was locked."
      :vestibule)))
