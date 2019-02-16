(defproject org.clojars.sulami/mlp "0.0.1-SNAPSHOT"
  :description "Multi-Level Perceptron Library"
  :url "https://github.com/sulami/mlp"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojars.sulami/prelude "0.1.0"]]
  :plugins [[lein-cloverage "1.0.13"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.6.15"]
            [lein-changelog "0.3.2"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]]}}
  :deploy-repositories [["releases" :clojars]]
  :aliases {"update-readme-version" ["shell" "sed" "-i" "s/\\\\[mlp \"[0-9.]*\"\\\\]/[mlp \"${:version}\"]/" "README.md"]})
