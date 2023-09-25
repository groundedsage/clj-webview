
(ns dev.webview.user 
  (:require [dev.webview.core :as hui]
            [dev.webview.window :as window]))

(defonce *window (atom nil))

(defonce *floating (atom false))

(defn make-window []
  (let [screen (last (hui/screens))
        scale  (:scale screen)
        width  (* 600 scale)
        height (* 400 scale)
        area   (:work-area screen)
        x      (:x area)
        y      (-> (:height area) (- height) (/ 2) (+ (:y area)))]
    (doto
     (window/make
      {:on-close #(reset! *window nil)})
      (window/set-title "Humble UI Test 👋")
      (window/set-window-size width height)
      (window/set-window-position x y)
      (window/set-visible true))))

(comment
  
  (future (hui/start #(reset! *window (make-window))))
  (do
    (hui/doui (some-> @*window window/close))
    (reset! *floating false)
    (reset! *window (hui/doui (make-window))))

  (hui/doui (window/set-z-order @*window :normal))
  (hui/doui (window/set-z-order @*window :floating))
  
  )