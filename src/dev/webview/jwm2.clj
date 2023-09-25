(ns dev.webview.jwm2
  (:import [io.github.humbleui.jwm App Window Event EventWindowCloseRequest]
           [java.util.function Consumer]))

(defn close-window [^Window window]
  (.close window))

(defn create-window []
  (let [window (App/makeWindow)]
    (.setTitle window "Hello, world!")
    window))


(defn initialize []
  (App/start (fn []
               (let [window (create-window)]
                 (.setEventListener window
                                    (reify Consumer
                                      (accept [this e]
                                        (println e)
                                        (when (instance? EventWindowCloseRequest e)
                                          (close-window window)
                                          (App/terminate)))))))
             (.setVisible window true)))

(defn -main []
  (initialize))
