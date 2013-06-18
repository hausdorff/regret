(ns regret.cli
  (:require [clojure.core.match :refer [match]]
            [regret storm-test]))

;(storm-test/run-local!)

;; shell utilities, possible to be moved to another file
(def red "\033[31m")
(def green "\033[32m")
(def bold "\033[1m")
(def resetter "\033[0m")

(defn println-reset [& strings]
  (doseq [s strings]
    (print s))
  (println resetter))

;; Tree representing the app directory
(def app-dir `("app/" ("resources/")
                      ("consumers/")
                      ("models/")
                      ("predictors/")))

;; List of all the folders in the project
(def proj-folders `(~app-dir))

(defn dirtree [root]
  "Generates tree of project directory rooted at `root`"
  (cons root proj-folders))

(defn dir-folders
  "Constructs list containing all the folders in directory.
  Guaranteed to list a directory *before* the files and folders in it, making
  it safe to use `map` to generate them in the order they appear."
  ([root]
   (let [tree (dirtree root)]
     (dir-folders tree "")))
  ([tree fullpath]
   (let [pwd (str fullpath (first tree))
         subtree (rest tree)]
     (match [(count subtree)]
            [0] `(~pwd)
            :else (cons pwd (flatten (map #(dir-folders % pwd) subtree)))))))


(defn invalid-cmd-err [msg]
  "Print error message, exit program"
  (do
    (println msg)
    (System/exit 1)))

(defn generate [module-type names]
  "Generates module in current regret project"
  ; TODO: check that we're in a regret project!
  ; TODO: actually write code to generate all this
  (match [module-type (< (count names) 1)]
         [_ true] (invalid-cmd-err
                (str "invalid number of args\n"
                     "usage: regret generate [type] [list of names]"))
         ["resource" false] (println "generate resource!")
         ["consumer" false] (println "generate consumer!")
         ["model" false] (println "generate model!")
         ["predictor" false] (println "generate predictor!")))

(defn get-pwd []
  (str (System/getProperty "user.dir") "/"))

(defn make-folders [name]
  (doseq [relpath (dir-folders (str name "/"))]
    (if (.mkdir (java.io.File. (str (get-pwd) relpath)))
      (println-reset green relpath)
      (println relpath))))

(defn new-proj [name]
  "Generates new regret module (eg, model, resource...). Takes a project name
  as a string, creates directory, returns."
  ; TODO: generate files in addition to just folders.
  (if (nil? name)
    (invalid-cmd-err "usage: regret new [project name]")
    (make-folders name)))

(defn proc-args [[command & opts]]
  "Poor man's cl processing. No flags, just looks for and processes commands"
  (case command
    "generate" (generate (first opts) (rest opts))
    "new"      (new-proj (first opts))
    nil        (invalid-cmd-err "regret requires a command to be entered")
    (invalid-cmd-err (str "unknown command " command))))
