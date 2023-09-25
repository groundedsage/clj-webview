(ns dev.webview.tech2
  (:require [tech.v3.datatype.ffi :as dtype-ffi]))

(dtype-ffi/define-library!
  webview-lib
  '{:webview_create {:rettype :pointer
                     :argtypes [[flags :int32]
                                [window :pointer]]}
    :webview_destroy {:rettype :void
                      :argtypes [[webview-instance :pointer]]}
    :webview_run {:rettype :void
                  :argtypes [[webview-instance :pointer]]}
    :webview_eval {:rettype :int32
                   :argtypes [[webview-instance :pointer]
                              [js-str :pointer]]}
    ;; :webview_set_url {:rettype :void
    ;;                   :argtypes [[webview-instance :pointer]
    ;;                              [url :pointer]]}
    :webview_set_title {:rettype :void
                        :argtypes [[webview-instance :pointer]
                                   [title :pointer]]}
    :webview_set_size {:rettype :void
                       :argtypes [[webview-instance :pointer]
                                  [width :int32]
                                  [height :int32]
                                  [hints :int32]]}
    :webview_get_window {:rettype :pointer
                         :argtypes [[webview-instance :pointer]]}
    ;; :webview_dialog {:rettype :void
    ;;                  :argtypes [[webview-instance :pointer]
    ;;                             [dialog-type :int32]
    ;;                             [title :pointer]
    ;;                             [arg :pointer]
    ;;                             [result :pointer]]}
    :webview_dispatch {:rettype :void
                       :argtypes [[webview-instance :pointer]
                                  [fn :pointer]
                                  [arg :pointer]]}
    :webview_navigate {:rettype :void
                       :argtypes [[webview-instance :pointer]
                                  [url :pointer]]}
    :webview_set_html {:rettype :void
                       :argtypes [[webview-instance :pointer]
                                  [html-content :pointer]]}
    :webview_bind {:rettype :void
                   :argtypes [[webview-instance :pointer]
                              [name :pointer]
                              [fn :pointer]
                              [arg :pointer]]}
    :webview_unbind {:rettype :void
                     :argtypes [[webview-instance :pointer]
                                [name :pointer]]}}
  nil nil)


;; Instantiate the webview library. Use nil for the current process or provide the path to your dynamic library.
(defonce webview-instance (dtype-ffi/library-singleton-set! webview-lib "/native/osx/libwebview.dylib"))


(defn create-webview [debug window]
  (.webview_create webview-instance debug window))

(defn destroy-webview [webview]
  (.webview_destroy webview-instance webview))

(defn run-webview [webview]
  (.webview_run webview-instance webview))

(defn eval-webview [webview js-code]
  (.webview_eval webview-instance webview js-code))

(defn set-url-webview [webview url]
  (.webview_set_url webview-instance webview url))

(defn set-title-webview [webview title]
  (.webview_set_title webview-instance webview title))

(defn set-size-webview [webview w h hints]
  (.webview_set_size webview-instance webview w h hints))

(defn get-window-webview [webview]
  (.webview_get_window webview-instance webview))

(defn dialog-webview [webview type title arg result]
  (.webview_dialog webview-instance webview type title arg result))

(defn dispatch-webview [webview fn arg]
  (.webview_dispatch webview-instance webview fn arg))


(comment 
  
  (def w (atom nil))


  (future (reset! w (create-webview 1 nil)))
  )
