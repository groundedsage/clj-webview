(ns dev.webview.loader
  (:require [clojure.java.io :as io])
  (:import (com.sun.jna Native)))

(defn extract-lib-to-tmp []
  (let [input-stream (io/resource "libwebview.dylib")
        tmp-file (java.io.File/createTempFile "libwebview" ".dylib")]
    (io/copy input-stream tmp-file)
    (.deleteOnExit tmp-file)
    tmp-file))

(defn load-webview-lib []
  (let [tmp-lib (extract-lib-to-tmp)]
    (System/setProperty "jna.library.path" (.getParent tmp-lib))
    ;; Assuming you've set up the JNA interface, you can load it here.
    ;; The next line is commented out since the actual interface needs to be defined.
    ;; (Native/load "webview" WebviewNativeInterface)
    ))

