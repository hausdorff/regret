(ns regret.cli-test
  (:use midje.sweet
        regret.cli))

(fact "dir-folders generates folders list correctly"
      (dir-folders "foo/") => ["foo/"
                               "foo/app/"
                               "foo/app/resources/"
                               "foo/app/consumers/"
                               "foo/app/models/"
                               "foo/app/predictors/"])