(ns regret.core
  (:use [regret.cli :as cli]))

(defn -main [& args]
  (cli/proc-args args))
