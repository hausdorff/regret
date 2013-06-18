(ns regret.cli
  (:require [clojure.core.match :refer [match]]))

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

(defn dirtree [root]
  "Generates tree of project directory rooted at `root`"
  (cons root (list app-dir)))

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
     (if (zero? (count subtree))
       `(~pwd)
       (cons pwd (flatten (map #(dir-folders % pwd) subtree)))))))


(defn invalid-cmd-err
  "Print error message, throw exception indicating illegal command line arg"
  [msg]
  (do
    (println msg)
    (throw (IllegalArgumentException. "invalid command line argument"))))

(defn generate
  "Generates module in current regret project"
  [module-type names]
  ; TODO: check that we're in a regret project!
  ; TODO: actually write code to generate all this
  (if (< (count names) 1)
    (invalid-cmd-err
     (str "invalid number of args\n"
          "usage: regret generate [type] [list of names]"))
    (case module-type
      "resource"  (println "generate resource!")
      "consumer"  (println "generate consumer!")
      "model"     (println "generate model!")
      "predictor" (println "generate predictor!"))))

(defn get-pwd []
  (str (System/getProperty "user.dir") "/"))

(defn make-folders [name]
  (doseq [relpath (dir-folders (str name "/"))]
    (if (.mkdir (java.io.File. (str (get-pwd) relpath)))
      (println-reset green relpath)
      (println relpath))))

(defn new-proj
  "Generates new regret module (eg, model, resource...). Takes a project name
  as a string, creates directory, returns."
  [name]
  ; TODO: generate files in addition to just folders.
  (if (nil? name)
    (invalid-cmd-err "usage: regret new [project name]")
    (make-folders name)))

(defn proc-args
  "Poor man's cl processing. No flags, just looks for and processes commands"
  [[command & opts]]
  (case command
    "generate" (generate (first opts) (rest opts))
    "new"      (new-proj (first opts))
    nil        (invalid-cmd-err (str "regret requires a command to be entered\n"
                                     "TODO: tell about different commands"))
    (invalid-cmd-err (str "unknown command " command))))
