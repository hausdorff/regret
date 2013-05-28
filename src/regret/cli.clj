(ns regret.cli
  (:use [clojure.core.match :only [match]]))

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

(defn- dir-folders' [tree fullpath]
  "Recursive helper constructs list containing all the folders in directory.
  Guaranteed to list a directory *before* the files and folders in it, making
  it safe to use `map` to generate them in the order they appear."
  (let [pwd (str fullpath (first tree))
        subtree (rest tree)]
    (match [(count subtree)]
           [0] `(~pwd)
           :else (cons pwd (flatten (map #(dir-folders' % pwd) subtree))))))

(defn dir-folders [root]
  "Constructs a list containing all the folders in the directory"
  (let [tree (dirtree root)]
    (dir-folders' tree "")))

(defn invalid-cmd-err [msg]
  "Print error message, exit program"
  (do
    (println msg)
    (System/exit 1)))

(defn print-created [created folders]
  "Prints all successfully created directories in bold green; those that
  already existed are printed in normalface white."
  (doall (map #(if %1 (println "\033[1m\033[32m" %2 "\033[0m") (println %2))
              created folders))
  nil)

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

(defn new-proj [name]
  "Generates new regret module (eg, model, resource...). Takes a project name
  as a string, creates directory, returns."
  ; TODO: generate files in addition to just folders.
  (match [name]
         [nil] (invalid-cmd-err "usage: regret new [project name]")
         :else (let [pwd (str (System/getProperty "user.dir") "/" name "/")
                     fullpath-folders (dir-folders pwd) ; for calls to mkdir
                     relpath-folders (dir-folders (str name "/")) ;for printing
                     created (map #(.mkdir (java.io.File. %))
                                  fullpath-folders)]
                 (print-created created relpath-folders))))

(defn proc-args [args]
  "Poor man's cl processing. No flags, just looks for and processes commands"
  (let [command (first args)
        opts (rest args)]
    (match [command]
           ["generate"] (generate (first opts) (rest opts))
           ["new"] (new-proj (first opts))
           [nil] (invalid-cmd-err "regret requires a command to be entered")
           :else (invalid-cmd-err (str "unknown command " command)))))
