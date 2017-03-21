(ns advenjure.text.en-past)

;; this is a test of a possible hack to force a translation in the current
;; state of advenjure without having to implement cljs support in gettext

(defn starts-with-vowel
  [ctx]
  (let [vowel? (set "aeiouAEIOU")]
    (vowel? (ffirst (:names ctx)))))

(def dictionary
  {"a %s" #(if (starts-with-vowel %) "an %s" "a %s")
   "I wasn't carrying anything." "lalala"})
