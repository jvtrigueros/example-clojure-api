(defproject example-clojure-api "0.1.0"
  :description "Example of a Clojure API as both a CLI and HTTP app."
  :dependencies [[compojure "1.6.2"]
                 [http-kit "2.5.3"]
                 [org.clojure/clojure "1.10.2"]
                 [org.clojure/data.csv "1.0.0"]
                 [ring "1.9.1"]]
  :main ^:skip-aot example-clojure-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
