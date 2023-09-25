(ns dev.webview.tech
  (:require [tech.v3.jna :as jna])
  (:import [com.sun.jna Pointer]))

(defprotocol JNACallback
  "Protocol for functions to be passed as callbacks to JNA."
  (invoke [this & args]))

; Load the C library
(def c-lib
  (jna/load-library "/native/osx/libwebview.dylib"))

; Bind functions
(jna/def-jna-fn c-lib webview_create
  "Create a new webview instance."
  jna/as-ptr
  [debug int]
  [window jna/as-ptr])

(jna/def-jna-fn c-lib webview_destroy
  "Destroy a webview instance."
  nil
  [w jna/as-ptr])

(jna/def-jna-fn c-lib webview_run
  "Run the main loop for the webview."
  nil
  [w jna/as-ptr])

(jna/def-jna-fn c-lib webview_terminate
  "Terminate the main loop."
  nil
  [w jna/as-ptr])

(jna/def-jna-fn c-lib webview_dispatch
  "Dispatch a function to run on the main thread."
  nil
  [w jna/as-ptr]
  [callback JNACallback]
  [arg jna/as-ptr])


(defn dispatch
  [w f]
  (webview_dispatch w
                    (reify JNACallback
                      (invoke [this & args]
                        (apply f args)))
                    nil))



(def ^:const string-class java.lang.String)

(jna/def-jna-fn c-lib webview_set_title
  "Set the title of the webview."
  nil
  [w jna/as-ptr]
  [title string-class])

(jna/def-jna-fn c-lib webview_set_size
  "Set the size of the webview with specific hints."
  nil
  [w jna/as-ptr]
  [width int]
  [height int]
  [hints int])

(jna/def-jna-fn c-lib webview_navigate
  "Navigate to a specified URL."
  nil
  [w jna/as-ptr]
  [url string-class])

(jna/def-jna-fn c-lib webview_set_html
  "Set the HTML content of the webview."
  nil
  [w jna/as-ptr]
  [html string-class])

(jna/def-jna-fn c-lib webview_eval
  "Evaluate JavaScript in the webview."
  nil
  [w jna/as-ptr]
  [js string-class])

(jna/def-jna-fn c-lib webview_bind
  "Bind a native function to be callable from JavaScript."
  nil
  [w jna/as-ptr]
  [name string-class]
  [callback JNACallback]
  [arg jna/as-ptr])

(defn bind
  [w name f]
  (webview_bind w name
                (reify JNACallback
                  (invoke [this & args]
                    (apply f args)))
                nil))


(jna/def-jna-fn c-lib webview_unbind
  "Unbind a previously bound function."
  nil
  [w jna/as-ptr]
  [name string-class])

(comment
  
  
(def webview-instance (webview_create 0 nil))
 ;Execution error (IllegalArgumentException) at jdk.internal.reflect.DirectMethodHandleAccessor/null (DirectMethodHandleAccessor.java:108) .
; argument type mismatch
  
  
)
