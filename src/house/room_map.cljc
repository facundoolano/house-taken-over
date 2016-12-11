(ns house.room-map
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.rooms.street :refer [street vestibule house-side corner alley]]
            [house.rooms.garden :refer [garden shed house-back west-passage east-passage]]
            [house.rooms.back :refer [back-hall1 back-hall2 back-hall3 hidden-room
                                      bedroom2 bedroom3 tapestry library dining
                                      back-bedroom-post]]

            [house.rooms.middle :refer [front-hall side-hall kitchen bathroom]]
            [house.rooms.front :refer [living woman-bedroom man-bedroom]]
            [house.puzzles.oak-door :refer [oak-unlocked-back oak-unlocked-front]]
            [house.puzzles.gate-door :refer [gate-unlocked]]))

;; define a room map and then set the connections between rooms
(def room-map
  (->
    {:street street
     :vestibule vestibule
     :house-side house-side
     :corner corner
     :alley alley

     :garden garden
     :shed shed
     :house-back house-back
     :west-passage west-passage
     :east-passage east-passage

     :back-hall3 back-hall3
     :back-hall2 back-hall2
     :back-hall1 back-hall1
     :hidden-room hidden-room
     :bedroom2 bedroom2
     :bedroom3 bedroom3
     :dining dining
     :tapestry tapestry
     :library library

     :front-hall front-hall
     :side-hall side-hall
     :bathroom bathroom
     :kitchen kitchen

     :living living
     :woman-bedroom woman-bedroom
     :man-bedroom man-bedroom}

    (room/connect :street :north :vestibule)
    (room/one-way-connect :vestibule :north "The gate door was closed.")
    (room/connect :street :west :house-side)
    (room/connect :house-side :north :corner)
    (room/connect :corner :east :alley)

    ; alley is connected to garden by climbing a fig
    (room/connect :garden :east :shed)
    (room/connect :garden :south :house-back)
    (room/one-way-connect :house-back :southwest :west-passage)
    (room/one-way-connect :house-back :southeast :east-passage)
    (room/one-way-connect :west-passage :north :house-back)
    (room/one-way-connect :east-passage :north :house-back)
    (room/one-way-connect :house-back :south "The back door was locked.")

    (room/one-way-connect :back-hall3 :west {:pre :bedroom2 :post `back-bedroom-post})
    (room/one-way-connect :back-hall3 :east {:pre :bedroom3 :post `back-bedroom-post})
    (room/one-way-connect :bedroom2 :east :back-hall3)
    (room/one-way-connect :bedroom3 :west :back-hall3)
    (room/connect :back-hall3 :south :back-hall2)
    (room/connect :back-hall2 :west "I couldn't open the west door, it didn't have a doorknob.")
    (room/one-way-connect :hidden-room :east :back-hall2) ;only visible when room is lit
    (room/connect :back-hall2 :east :tapestry)
    (room/connect :back-hall2 :south :back-hall1)
    (room/connect :dining :north :tapestry)
    (room/connect :back-hall1 :west :library)
    (room/connect :back-hall1 :east :dining)
    (room/one-way-connect :back-hall1 :south `oak-unlocked-back)

    (room/one-way-connect :front-hall :north `oak-unlocked-front)

    (room/connect :front-hall :west :side-hall)
    (room/connect :side-hall :west :bathroom)
    (room/connect :side-hall :south :kitchen)
    (room/connect :front-hall :south :living)

    (room/connect :living :west :man-bedroom)
    (room/connect :living :east :woman-bedroom)
    (room/one-way-connect :living :south `gate-unlocked)))








