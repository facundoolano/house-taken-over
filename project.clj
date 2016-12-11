(defproject house "0.1.0-SNAPSHOT"
  :description "House Taken Over"
  :url "https://github.com/facundoolano/advenjure-example"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.4-7"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [advenjure "0.7.0"]]
  :cljsbuild
    {:builds
     {:main {:source-paths ["src"]
             :compiler {:output-to "resources/public/js/main.js"
                        :main house.core
                        :optimizations :simple
                        :pretty-print false
                        :optimize-constants true
                        :static-fns true}}

      :dev {:source-paths ["src" "checkouts/advenjure/src"]
            :figwheel {:load-warninged-code true
                       :before-jsload "advenjure.ui.input/figwheel-cleanup"}

            :compiler {:output-to "resources/public/js/main.js"
                       :output-dir "resources/public/js/out"
                       :main house.core
                       :parallel-build true
                       :asset-path "js/out"
                       :optimizations :none
                       :source-map true
                       :pretty-print true}}}}


  :main ^:skip-aot house.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
