(defproject toolbox "0.1.0-SNAPSHOT"
  :description "A collection of tools for everyday life"
  :url "https://github.com/Otann/toolbox"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.0.567"]
                 [org.clojure/data.json "1.0.0"]

                 [io.pedestal/pedestal.service "0.5.7"]
                 [io.pedestal/pedestal.jetty "0.5.7"]

                 [mount "0.1.16"]
                 [wrench "0.3.3"]
                 [clj-http "3.10.0"]]

  :min-lein-version "2.0.0"

  :main ^:skip-aot toolbox.main
  :uberjar-name "toolbox.jar"

  :resource-paths ["resources"]

  :profiles {:uberjar {:aot :all}
             :dev     {:source-paths ["dev"]
                       :repl-options {:init-ns user}
                       :dependencies [[org.clojure/tools.namespace "0.2.11"]]}})
