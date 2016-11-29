(ns house.puzzles.catalog
  (:require [clojure.string :as string]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [advenjure.ui.request :refer [request]]
            [advenjure.ui.input :refer [prompt-value]]
            #?(:clj [advenjure.async :refer [alet]]))
  #?(:cljs (:require-macros [advenjure.async :refer [alet]])))

(def roca-topics #{"julio argentino roca"
                   "alejo julio argentino roca paz"
                   "julio roca"
                   "julio a roca"
                   "julio a. roca"})

(def roca-text "Alejo Julio Argentino Roca Paz (July 17, 1843 – October 19, 1914) was an army general who served as President of Argentina from 12 October 1880 to 12 October 1886 and again from 12 October 1898 to 12 October 1904.")

(def desert-topics #{"the conquest of the desert"
                     "conquest of the desert"
                     "conquest of desert"
                     "the conquest of desert"
                     "conquista del desierto"
                     "la conquista del desierto"})

(def desert-text "The Conquest of the Desert (Spanish: Conquista del desierto) was a military campaign directed mainly by General Julio Argentino Roca in the 1870s with the intent to establish Argentine dominance over Patagonia, which was inhabited by indigenous peoples. Under General Roca, the Conquest of the Desert extended Argentine power into Patagonia and ended the possibility of Chilean expansion there.")

(defn wiki-url [topic]
  (str "https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&namespace=0&format=json&redirects=resolve&origin=*&search="
       (string/trim topic)))

(defn print-result [extract] (str "Browsing the books I found this:\n \n" extract))

(defn search-catalog
  [gs]
  (alet [topic (prompt-value "Topic to search for: ")
         result (request (wiki-url topic) {:response-format :json})
         extract (get-in result [2 0])
         clean-topic (string/lower-case (string/trim topic))]
    (cond
      (not (string/blank? extract)) (print-result extract)
      ; default non accurate enough foro wikipedia or no internet connection
      (roca-topics clean-topic) (print-result roca-text)
      (desert-topics clean-topic) (print-result desert-text)
      :else "I found nothing about that in the catalog.")))

(def catalog (item/make ["card cabinet" "cabinet" "catalog" "card catalog"]
                        "It was a card catalog of the library, consisting of a set of labeled small drawers. I could use it to search the books for a specific topic."
                        :use `search-catalog
                        :open `search-catalog))
