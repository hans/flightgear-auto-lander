(defproject flightgear-auto-lander "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.novemberain/monger "1.5.0-rc1"]]
  :git-dependencies [["https://github.com/hans/flightgear.git"]]
  :source-paths [".lein-git-deps/flightgear/src"])
