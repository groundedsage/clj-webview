(ns binder
  (:require [com.phronemophobic.clong.clang :as clang]
            [com.phronemophobic.clong.gen.jna :as jna-gen]
            [com.phronemophobic.clong.gen.jna :as gen]))

(comment

  (def results (->> (clang/parse "webview.h" clang/default-arguments)
                    clang/get-children
                    (map clang/cursor-info)))
  
  (def api (clang/easy-api "webview.h"))

  (def libwebview
    (com.sun.jna.NativeLibrary/getInstance "/native/osx/libwebview.dylib"))
  
  (gen/def-api libwebview api)

  (keys (ns-publics *ns*))

  (clojure.repl/doc  webview_navigate)

  (clojure.repl/doc  webview_create)

  (println (webview_version))

  (def webview-state (atom nil))

  (future (reset! webview-state (webview_create 0 nil)))

  (clojure.repl/doc webview_create)


  (println (webview_navigate))


  )