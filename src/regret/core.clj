(ns regret.core
  (:require [regret.cli :as cli]))

(defn -main [& args]
  (try
    (cli/proc-args args)
    (catch IllegalArgumentException e
      (System/exit 1))))
