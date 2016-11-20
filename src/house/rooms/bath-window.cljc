(ns house.rooms.stool
  (:require [advenjure.items :as item]
            [advenjure.rooms :as room]
            [advenjure.utils :as utils]))

;;; BATHROOM WINDOW
(def bath-window (item/make "window"
                            "It was too high to look inside from where I was standing."
                            :look-in "It was too high to look inside from where I was standing."
                            :open "I could reach the border of the window but not open it from the ground level."
                            :open-with "I could reach the border of the window but not open it from the ground level."
                            :use "I could reach the border of the window but not open it from the ground level."
                            :enter "I could reach the border of the window but not open it from the ground level."
                            :break "There was no need to break the window, just find a way to reach it."
                            :id "bath-window"))

(def bath-window-open (item/make ["window" "bathroom window"]
                                 "It led to a bathroom and was now open."
                                 :look-in "It led to the bathroom."
                                 :open "It was already open."
                                 :close "After all that effort?"
                                 :use "You mean enter it?"
                                 :enter :bathroom
                                 :break "Why?"
                                 :id "bath-window-open"))

(defn pre-open-with
  "Only used to override default message for crowbar, as the opens already
  decides what item gets to open it."
  [gs item]
  (if (some #{"crowbar"} (:names item))
    "The crowbar didn't have the proper grip for the job, it was too long."
    true))

(defn post-open-with
  "Change the window to open version and remove the screwdriver."
  [old gs]
  (let [screwdriver (utils/find-first gs "screwdriver")
        window (utils/find-first gs "window")]
    (if-not (:closed window) ; make sure it was called with the proper item
      (-> gs
        (utils/remove-item screwdriver)
        (utils/replace-item window bath-window-open)))))

(def bath-window-reachable (item/make ["window" "bathroom window"]
                                      "Standing on the stool I could look inside the window. It was a bathroom, all right. I could have probably made it through the window, but it was closed from inside."
                                      :look-in "Standing on the stool I could look inside the window. It was a bathroom, all right. I could have probably made it through the window, but it was closed from inside."
                                      :open "I couldn't with my bare hands."
                                      :use "I couldn't open it with my bare hands."
                                      :enter "It was closed from the inside."
                                      :break "Right, and then enter through the broken glasses."
                                      :id "bath-window-reachable"
                                      :open-with {:pre `pre-open-with
                                                  :say "The screwdriver did the trick, but also broke in the effort."
                                                  :post `post-open-with}))

;;; PIANO STOOL
(def wall-stool (item/make ["piano stool" "stool"] "I put it against the wall, just below the window."
                            :use "I didn't want to sit."
                            :take "It was good there."))

(defn is-bath-window
  [gs item]
  (or (= (:id item) "bath-window") "No use there."))

(defn put-stool
  [old gs]
  (let [old-stool (utils/find-first gs "stool")]
    (-> gs
        (utils/remove-item old-stool)
        (utils/replace-item bath-window bath-window-reachable)
        (update-in [:room-map :west-passage] room/add-item wall-stool "Below the window was a piano stool."))))


(def piano-stool (item/make ["piano stool" "stool"] "About twenty inches, easy to move around."
                            :use "I didn't want to sit."
                            :use-with {:pre `is-bath-window
                                       :say "I put the stool against the wall, just below the window."
                                       :post `put-stool}
                            :take true))

(def after-stool (item/make ["piano stool" "stool"] "Good times."
                            :use "I didn't want to sit."
                            :take "I was done using that."))


;;; SCREWDRIVER
(defn is-reachable-window
  [gs item]
  (cond
    (= (:id item) "bath-window-reachable") true
    (= (:id item) "bath-window") "I couldn't from the ground level."
    :else "That didn't have any screws in it."))

; TODO totally duplicated, think about ways to avoid it
(defn post-use-with
  "Change the window to open version and remove the screwdriver."
  [old gs]
  (let [screwdriver (utils/find-first gs "screwdriver")
        window (utils/find-first gs "window")]
    (-> gs
      (utils/remove-item screwdriver)
      (utils/replace-item window bath-window-open))))

(def screwdriver (item/make "screwdriver" "the dust and the rust were competing to see who'd take over the thing first."
                            :use-with {:pre `is-reachable-window
                                       :say "The screwdriver did the trick, but also broke in the effort."
                                       :post `post-use-with}
                            :opens bath-window-reachable
                            :take true))
