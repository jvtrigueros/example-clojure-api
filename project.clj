(defproject example-clojure-api "0.1.0"
  :description "Example of a Clojure API as both a CLI and HTTP app."
  :dependencies [[org.clojure/clojure "1.10.2"]]
  :main ^:skip-aot example-clojure-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
