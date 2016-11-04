(ns house.room-map
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.rooms.street :refer [street]]
            [house.rooms.vestibule :refer [vestibule]]))

(def house-side (room/make "Side of house"
                           " "
                           :known true))

(def corner (room/make "Wall corner"
                       " "))

(def alley (room/make "Alley"
                      " "
                      :known true))

(def garden (room/make "Garden"
                       " "))

(def shed (room/make "Shed"
                     " "
                     :known true))

(def house-back (room/make "Back of house"
                           " "
                           :known true))

(def west-passage (room/make "West passage"
                             " "))

(def east-passage (room/make "East passage"
                             " "))

(def living (room/make "Living room"
                       "There were bedrooms on either side of the central living room and a corridor leading to the back section."))

; TODO add sofa, bed, wardrobe, camphor chest, table, knitting basket
(def irene-bedroom (room/make "Woman's bedroom"
                              " "))


(def my-bedroom (room/make "Man's bedroom" " "))

(def front-hall (->
                 (room/make "Corridor"
                            "A corridor ending in a massive oak door and a narrow passageway turning west.")

                 (room/add-item (item/make ["door" "oak door"] "It was massive, I think I said that.") "")))

(def side-hall (room/make "Passageway" "This narrow passage led to the kitchen and the bath."))

; TODO bath, toilette, lavatory, cabinet
(def bathroom (room/make "Bath" "The bathroom."))

(def kitchen (room/make "Kitchen" " "))

(def back-hall1 (room/make "Passageway"
                           " "
                           :initial-description "When the oak door was open, you became aware of the size of the house; when it was closed, you had the impression of an apartment, like the ones they build today, with barely enough room to move around in."
                           :known true))

(def back-hall2 (room/make "Passageway"
                           " "
                           :initial-description "Irene and I hardly ever went beyond the oak door except to do the cleaning. Incredible how much dust collected on the furniture."
                           :known true))

(def back-hall3 (room/make "Passageway" ; FIXME add doors
                           " "))

; TODO add chairs and tables
(def dinning (room/make "Dinning room"
                        "A dinning room with a big table and smaller one on the side."
                        :initial-description "The dinning table was big enough to fit twenty people. There were chairs all around it and a smaller table on the side."
                        :known true))

(def tapestry (room/make "Tapestries room" "A living room with Gobelin tapestries." :known true))

; TODO favorite part? I did most of the reading in my bedroom, or Irene's
; I've spent a fair amount of time going through books back in the day. now only take in and out. nothing in argentina since 1939
; TODO add bookshelves
; TODO I should probably put this book in the library, I'm tired of carrying it around
; TODO desk and a couple of reading chairs with lamps
(def library (room/make "Library" " " :known true))

(def bedroom1 (room/make "Bedroom" " " :known true))
(def bedroom2 (room/make "Bedroom" " " :known true))
(def bedroom3 (room/make "Bedroom" " " :known true))


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
     :bedroom1 bedroom1
     :bedroom2 bedroom2
     :bedroom3 bedroom3
     :dinning dinning
     :tapestry tapestry
     :library library

     :front-hall front-hall
     :side-hall side-hall
     :bathroom bathroom
     :kitchen kitchen

     :living living
     :irene-bedroom irene-bedroom
     :my-bedroom my-bedroom}

    (room/connect :street :north :vestibule)
    (room/one-way-connect :vestibule :north "The gate door was closed.")
    (room/connect :street :west :house-side)
    (room/connect :house-side :north :corner)
    (room/connect :corner :east :alley)

    (room/one-way-connect :alley :up :garden) ; FIXME not really connected, use climb puzzle instead
    (room/connect :garden :east :shed)
    (room/connect :garden :south :house-back)
    (room/one-way-connect :house-back :southwest :west-passage)
    (room/one-way-connect :house-back :southeast :east-passage)
    (room/one-way-connect :west-passage :north :house-back)
    (room/one-way-connect :east-passage :north :house-back)
    (room/connect :house-back :south :back-hall3)

    (room/connect :back-hall3 :west :bedroom2)
    (room/connect :back-hall3 :east :bedroom3)
    (room/connect :back-hall3 :south :back-hall2)
    (room/connect :back-hall2 :west :bedroom1)
    (room/connect :back-hall2 :east :tapestry)
    (room/connect :back-hall2 :south :back-hall1)
    (room/connect :back-hall1 :west :dinning)
    (room/connect :back-hall1 :east :library)
    (room/connect :back-hall1 :south :front-hall) ; FIXME not really, this will be blocked

    (room/connect :front-hall :west :side-hall)
    (room/connect :side-hall :west :bathroom)
    (room/connect :side-hall :south :kitchen)
    (room/connect :front-hall :south :living) ; FIXME not really, this will be blocked

    (room/connect :living :west :my-bedroom)
    (room/connect :living :east :irene-bedroom)
    (room/one-way-connect :living :south :vestibule))) ; FIXME not really, when he gets here will be two way








