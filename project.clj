(def VERSION (slurp "VERSION"))

(defproject regret VERSION
  :description "Simple framework for developing and deploying machine learning systems"
  :url "http://www.regret.io"
  :license {:name "MIT"
            :url "http://github.com/hausdorff/regret/blob/master/LICENSE"}
  :dependencies [[org.apache.mahout/mahout-core "0.7"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/core.match "0.2.0-alpha12"]]
  :main regret.core)
