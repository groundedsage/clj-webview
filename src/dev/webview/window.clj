(ns dev.webview.window
  (:require
   [dev.webview.core :as core]
   [dev.webview.event :as event])
  (:import
   [io.github.humbleui.jwm App Platform Window ZOrder]
   [java.util.function Consumer]))

(set! *warn-on-reflection* true)

(defn close [^Window window]
  (.close window))

(defn make
  ":on-close-request (fn [window])
   :on-close         (fn [])
   :on-screen-change (fn [window])
   :on-resize        (fn [window])
   :on-paint         (fn [window canvas])
   :on-event         (fn [window event])"
  [{:keys [on-close-request on-close on-screen-change on-resize on-paint on-event]
    :or {on-close-request close}}]
  (let [window   (App/makeWindow)
        #_layer    #_(condp = Platform/CURRENT
                   Platform/MACOS   (LayerMetalSkija.)
                   Platform/WINDOWS (LayerD3D12Skija.)
                   Platform/X11     (LayerGLSkija.))
        listener (reify Consumer
                   (accept [this jwm-event]
                     (let [e (event/event->map jwm-event)]
                       (when on-event
                         (on-event window e))

                       (case (:event e)
                         :window-close-request
                         (when on-close-request
                           (on-close-request window))

                         :window-close
                         (when on-close
                           (on-close))

                         :window-screen-change
                         (when on-screen-change
                           (on-screen-change window))

                         :window-resize
                         (when on-resize
                           (on-resize window))

                         #_:frame-skija
                         #_(when on-paint
                           (let [canvas (.getCanvas ^Surface (:surface e))
                                 layer  (.save canvas)]
                             (try
                               (on-paint window canvas)
                               (catch Exception e
                                 (.printStackTrace e)
                                 (.clear canvas (unchecked-int 0xFFCC3333)))
                               (finally
                                 (.restoreToCount canvas layer)))))

                         nil))))]
    #_(.setLayer window layer)
    (.setEventListener window listener)
    window))

(defn scale [^Window window]
  (.getScale (.getScreen window)))

(defn set-title [^Window window ^String title]
  (.setTitle window title)
  window)

(defn set-visible [^Window window ^Boolean value]
  (.setVisible window value)
  window)

(defn window-rect [^Window window]
  (.getWindowRect window))

(defn content-rect [^Window window]
  (.getContentRect window))

(defn set-window-position [^Window window ^long x ^long y]
  (.setWindowPosition window x y)
  window)

(defn set-window-size [^Window window ^long width ^long height]
  (.setWindowSize window width height)
  window)

(defn set-content-size [^Window window ^long width ^long height]
  (.setContentSize window width height)
  window)

(defn set-z-order [^Window window order]
  (.setZOrder window
              (case order
                :normal       ZOrder/NORMAL
                :floating     ZOrder/FLOATING
                :modal-panel  ZOrder/MODAL_PANEL
                :main-menu    ZOrder/MAIN_MENU
                :status       ZOrder/STATUS
                :pop-up-menu  ZOrder/POP_UP_MENU
                :screen-saver ZOrder/SCREEN_SAVER))
  window)

(defn request-frame [^Window window]
  (.requestFrame window)
  window)
