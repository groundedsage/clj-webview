(ns binder
  (:require [com.phronemophobic.clong.clang :as clang]
            [com.phronemophobic.clong.gen.jna :as jna-gen]
            [com.phronemophobic.clong.gen.jna :as gen]))

(def results (->> (clang/parse "webview.h" clang/default-arguments)
                  clang/get-children
                  (map clang/cursor-info)))

(def api (clang/easy-api "webview.h"))

(def libwebview
  (com.sun.jna.NativeLibrary/getInstance "/native/osx/libwebview.dylib"))

(gen/def-api libwebview api)


  (def status (atom nil)) ; An atom to hold the status of the webview creation.
  
    ; A separate thread to monitor the status of the webview creation.
  (future
    (loop []
      (if-let [w @status]
        (println "Webview successfully created:" w)
        (do
          (println "Waiting for webview to be created...")
          (Thread/sleep 3000)
          (recur)))))

(defn -main [& args]

  (println "creating webview")
  (println "Name of thread: "(.getName (Thread/currentThread)))
  (let [w (webview_create 0 nil)]
    (println "webview created")
    (webview_set_title w "Basic Example")
    (webview_set_size w 100 200 nil)
    (webview_set_html w "Thanks for using webview!")))

(comment
  
  (println (.getName (Thread/currentThread)))



  (keys (ns-publics *ns*))

  (clojure.repl/doc  webview_navigate)

  (clojure.repl/doc  webview_create)

  (println (webview_version))

  (def webview-state (atom nil))

  (future (reset! webview-state (webview_create 0 nil)))

  (clojure.repl/doc webview_create)


  ;;;;;;;



  (let [w (webview_create 0 nil)]
    (webview_set_title w "Basic Example")
    (webview_set_size w 100 200 nil)
    (webview_set_html w "Thanks for using webview!"))


  (reset! status (webview_create 0 nil))

  (clojure.repl/doc webview_set_size)




  (println (webview_navigate)))