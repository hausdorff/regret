(ns regret.core
  (:require [regret.cli :as cli]))

(defn -main [& args]
  (cli/proc-args args))
