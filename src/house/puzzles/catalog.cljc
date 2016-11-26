(ns house.puzzles.catalog
  (:require [clojure.string :as string]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [advenjure.ui.request :refer [request]]
            [advenjure.ui.input :refer [prompt-value]]
            #?(:clj [advenjure.async :refer [alet]]))
  #?(:cljs (:require-macros [advenjure.async :refer [alet]])))


(defn wiki-url [topic]
  (str "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&redirects=1&exintro=&explaintext=&exsentences=2&origin=*&titles="
       (string/trim topic)))

; TODO return the needed hints even if offline
(defn search-catalog
  [gs]
  (alet [topic (prompt-value "Topic to search for: ")
         result (request (wiki-url topic) {:response-format :json})
         extract (get (first (vals (get-in result ["query" "pages"]))) "extract")]
    (if-not (string/blank? extract)
      (str "Browsing the books I found this:\n \n" extract)
      "I found nothing about that in the catalog.")))

(def catalog (item/make ["card cabinet" "cabinet" "catalog" "card catalog"]
                        "It was a card catalog of the library, consisting of a set of labeled small drawers. I could use it to search the books for a specific topic."
                        :use `search-catalog
                        :open `search-catalog))
