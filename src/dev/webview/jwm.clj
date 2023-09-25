(ns dev.webview.jwm
  (:require [dev.webview.tech :as tech])
  (:import [io.github.humbleui.jwm App Platform Window ZOrder]))

(defn make-window
  "Creates a window with JWM and sets up a webview for rendering."
  [{:keys [url title width height]}]
  (let [window (App/makeWindow)
        ;webview-instance (tech/webview_create 1 window)
        ]
    (.setTitle window title)
    #_(tech/webview_set_size webview-instance width height 0)
    #_(tech/webview_navigate webview-instance url)
    window))

(comment 
  (let [win (make-window {:url "https://www.example.com"
                          :title "Webview with JWM"
                          :width 800
                          :height 600})]
    (tech/webview_run win))
  
  )

