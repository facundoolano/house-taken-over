(ns house.core
  (:require [house.game :refer [run-game]])
  (:gen-class))

(defn -main [& args] (run-game))
