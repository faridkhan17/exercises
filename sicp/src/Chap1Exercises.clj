(:require [java.lang.Math :as Math])
(ns Chap1Exercises)

; EXERCISE 1.1
(defn ex11 []
  (do
    (println 10)
    (println (+ 5 3 4))                                     ; 12
    (println (- 9 1))                                       ; 8
    (println (/ 6 2))                                       ; 3
    (println (+ (* 2 4) (- 4 6)))                           ; -6
    (def a 3)
    (def b (+ a 1))
    (println (+ a b (* a b)))
    (println (= a b))
    (println (if (and (> b a) (< b (* a b)))
               b
               a))
    (println (cond (= a 4) 6
                   (= b 4) (+ 6 7 a)
                   :else 25))
    (println (+ 2 (if > b a) b a))
    (* (cond (> a b) a
             (< a b) b
             :else -1)
       (+ a 1))
    )
  )

(println "---Exercise 1.1---")
(ex11)


; EXERCISE 1.2
(defn ex12 []
  (def n (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))))
  (def d (* 3 (- 6 2) (- 2 7)))
  (/ n d))

(println "---Exercise 1.2---")
(println (ex12))


; EXERCISE 1.3
(defn ex13 [a b c]
  (do
    (defn sq [x] (* x x))
    (- (+ (sq a) (sq b) (sq c)) (sq (min a b c)))))

(println "---Exercise 1.3---")
(println (ex13 3 4 2))

; EXERCISE 1.4
; If b is positive, return the addition operator, otherwise return the substraction operator.
; Then apply the returned operator to a and b. This is the same as a + |b|.
(defn ex14 [a b]
  ((if (> b 0) + -) a b ))

(println "---Exercise 1.4---")
(println (ex14 2 3))
(println (ex14 2 -3))

; EXERCISE 1.5
; If the interpreter is applicative, then it will try to evaluate p and the function will call itself infinitely.
; If it's normal, then it the function test will just return 0, since x equals 0.

; EXERCISE 1.6
; It will also evaluate the else clause immediately when it gets passed to new-if.
; In the best case this is just wasteful, in the worst case it crashes the program due to non-terminating recursion.

; EXERCISE 1.7
; First implement the iterative sqrt method in its naiive form.

(println "---Exercise 1.7---")
(defn abs [n]
  (if (> n 0) n (- 0 n)))

(defn good-enough? [x g t]
  (< (abs (- x (* g g))) t))

(defn improve [x g]
  (/ (+ g (/ x g)) 2.0))

(defn sqrt-iter [x g t]
  (if (good-enough? x g t)
    g
    (sqrt-iter x (improve x g) t)))

(defn sqrt [x] (sqrt-iter x 1.0 (min x 1e-8)))

(def s (sqrt 200))
(println [200, (* s s)])

; When I increase the exponent from 62 to 63, I get a StackOverflowError.
; This is because ....
(def bigNum (Math/pow 2 62))
(def sq (sqrt bigNum))
(println [bigNum, (* sq sq), sq])

; Dividing one by a very large number shows that the square roots are not equal.
; This is because ....
(def smallNum (/ 1 (Math/pow 2 32)))
(def sq (sqrt smallNum))
(println [smallNum, (* sq sq), sq])

; Implement the updated version of sqrt.
; This one should stop when the change between two guesses is less than a very small fraction of the guess.
(defn good-enough? [g1 g2 t]
  (< (/ (abs (- g1 g2)) g2) t))

(defn improve [x g]
  (/ (+ g (/ x g)) 2.0))

(defn sqrt-iter [x g1 t]
  (if (good-enough? g1 (improve x g1) t)
    g1
    (sqrt-iter x (improve x g1) t)))

(defn sqrt [x] (sqrt-iter x 1.0 1e-4))

; Demonstrate that this works for very large numbers (2^63 crashed with the first implementation).
(def bigNum (Math/pow 2 63))
(def sq (sqrt bigNum))
(println [bigNum, (* sq sq), sq])

; Demonstrate that it works for very small numbers (same test case as above).
(def smallNum (/ 1 (Math/pow 2 32)))
(def sq (sqrt smallNum))
(println [smallNum, (* sq sq), sq])

; EXERCISE 1.8
(println "---Exercise 1.8---")
(defn improve [x y]
  (do
    (def a (/ x (* y y)))
    (def b (* 2 y))
    (/ (+ a b) 3)
    ))

(defn cbrt-iter [x g1 t]
  (if (good-enough? g1 (improve x g1) t)
    g1
    (cbrt-iter x (improve x g1) t)))

(defn cbrt [x] (cbrt-iter x 1.0 1e-6))

(println (cbrt 27))
(println (cbrt (* 9 9 9)))

; EXERCISE 1.9
(println "---Exercise 1.9---")

; The first one basically works by repeatedly removing one from a and adding it to b.
(defn plus [a b]
  (if (= a 0)
    b
    (inc (plus (dec a) b))))
(println (plus 2 3))
; When adding (+ 2 3), it would look like this:
; (+ 2 3)
;  inc (+ 1 3)          The if-predicate is false, return the recursive call.
;       inc (+ 0 3)     Same
;            3          Now a is 0, return b
;       4               Increment b the first time
;  5                    Increment b again

; The second one works by just repeatedly incrementing b and decrementing a until a is zero.
; (+ 2 3)
;  + 1 4    If-predicate false, recursive call with decremented a, incremented b.
;  + 0 5    Same.
;  5        a is zero, return b.
(defn plus [a b]
  (if (= a 0)
    b
    (plus (dec a) (inc b))))
(println (plus 2 3))

; The first process is recursive the second is tail-recursive/iterative.

; EXERCISE 1.10
(println "---Exercise 1.10---")
(defn A [x y]
  (cond (= y 0) 0
        (= x 0) (* 2 y)
        (= y 1) 2
        :else (A (- x 1) (A x (- y 1)))))

(println (A 1 10))
(println (A 2 4))
(println (A 3 3))

(defn f [n] (A 0 n))                                        ; = (* 2 n)
(defn g [n] (A 1 n))                                        ; = 2^n
(defn h [n] (A 2 n))                                        ; ???
(defn k [n] (* 5 n n))                                      ; 5n^2

; EXERCISE 1.11
(println "---Exercise 1.11---")
(defn f [n]
  (if (< n 3)
    n
    (+ (f (- n 1))
       (* 2 (f (- n 2)))
       (* 3 (f (- n 3))))
    )
  )

(def t0 (System/currentTimeMillis))
(println (map f [10 15 20 25 27]))
(println (- (System/currentTimeMillis) t0))

; Equivalent scala code:
;def f(n:Int):Int = {
;  def g(a:Int, b:Int, c:Int, m:Int):Int =
;    if (m == n - 3) 3 * a + 2 * b + c
;    else g(b, c, 3 * a + 2 * b + c, m + 1)
;  g(0, 1, 2, 0)
;}
(defn f-iter
  ([n]
    (if (< n 3) n (f-iter 0 1 2 0 n)))
  ([a b c m n]
    (if (= m (- n 3))
      (+ (* 3 a) (* 2 b) c)
      (f-iter
        b
        c
        (+ (* 3 a) (* 2 b) c)
        (inc m)
        n)
      )))

(def t0 (System/currentTimeMillis))
(println (map f-iter [10 15 20 25 27]))
(println (- (System/currentTimeMillis) t0))

(println "---Exercise 1.12---")

;(defn pascal-row [prevRow]
;  (map ([i] (+ (prevRow i) (prevRow (inc i))))
;      (range 1 (- (count prevRow) 2))))

; Define the next row as a function of the previous row.
(defn pascal-row [prevRow]
  ; All concat'ed to a vector.
  (concat
    ; Always starts with a 1.
    [1]
    ; Map over range to add pairs of numbers from the prev row.
    (map (fn [i] (+ (nth prevRow i) (nth prevRow (inc i))))
         (range 0 (- (count prevRow) 1)))
    ; Always ends with a 1.
    [1]))

(defn pascal
  ; Default signature just takes the max level.
  ([maxLevel] (pascal 0 maxLevel [[1]]))
  ; This signature takes the current level,
  ; max level, and accumulation of rows so far.
  ([level maxLevel acc]
    (cond
      ; Return accumulation at the max level.
      (= level maxLevel) acc
      :else
      ; Otherwise recursive call with incremented
      ; level, concatenation of next row based on
      ; this row. ((acc level) is indexing.)
      (pascal
        (inc level) maxLevel
        (conj acc (pascal-row (acc level)))
        ))))

(doseq [line (pascal 5)] (println line))

(println "---Exercise 1.15---")
; Sine and p both get called n times, where n satisfies t * 3^n >= a.
; Where t is the threshold (0.1) and a the angle. So n = log(a / t) / log(3)
; In the example, n = natural-log(12.5 / 0.1) = 4.8.
; This checks out as "sine(...)" and "p(...)" each get printed 5 times.
; In terms of space, the runtime has to maintain n function calls on the stack.
; This checks out as all of the sine calls come before the p calls.

(defn cube [x] (* x x x))
(defn p [x]
  (do
    (println (str "p(" x ")"))
    (- (* 3 x) (* 4 (cube x)))))
(defn sine [ang]
  (do
    (println (str "sine(" ang ")"))
    (if (<= (abs ang) 0.1)
      ang
      (p (sine (/ ang 3.0)))))
  )
(println (sine 12.5))

(println "---Exercise 1.16---")

(defn square [n] (* n n))

(defn fast-expt [b n]
  (do
    (cond (= n 0) 1
          (even? n) (square (fast-expt b (/ n 2)))
          :else (* b (fast-expt b (- n 1))))
    )
  )

(println [(Math/pow 3.0 10.0), (fast-expt 3 10)])

; Equivalent scala implementation:
; def fastexpiter(b: Double, n: Int): Double = {
;    @tailrec
;    def f(r: Double, s: Double, n: Int): Double =
;      if (n == 1) r * s
;      else if (n % 2 == 0) f(r * r, s, n / 2)
;      else f(r, r * s, n - 1)
;    f(b, 1, n)
;  }
(defn fast-expt-iter
  ([b n] (fast-expt-iter b 1 n))
  ([r s n]
    (cond
      (= n 1) (* r s)
      (even? n) (fast-expt-iter (* r r) s (/ n 2))
      :else (fast-expt-iter r (* r s) (- n 1))
      )))

(println [(Math/pow 3.0 10.0), (fast-expt-iter 3 10)])

(println "---Exercise 1.17---")

(defn doubl [n] (+ n n))
(defn halve [n] (/ n 2))

(defn fast-mul [a b]
  (cond (= b 1) a
        (even? b) (fast-mul (doubl a) (halve b))
        :else (+ a (fast-mul a (- b 1)))))

(println (fast-mul 10 10))
(println (fast-mul 2 9))
(println (fast-mul 3 9))

(println "---Exercise 1.18---")

; Equivalent scala
; def mul2(a: Int, b: Int): Int =
;    if (b == 1) a
;    else if (b % 2 == 0) mul2(double(a), halve(b))
;    else a + mul2(a, b - 1)
(defn fast-mul-iter
  ([a b] (fast-mul-iter a b 0))
  ([a b c]
    (cond (= b 1) (+ a c)
          (even? b) (fast-mul-iter (doubl a) (halve b) c)
          :else (fast-mul-iter a (- b 1) (+ a c)))
    ))

(println (fast-mul-iter 10 10))
(println (fast-mul-iter 2 9))
(println (fast-mul-iter 3 9))

(println  "---Exercise 1.19---")

(defn fib [n]
  (cond (= n 0) 0
        (= n 1) 1
        :else (+ (fib (- n 1)) (fib (- n 2)))))

; Had to look here to understand this one:
; http://www.billthelizard.com/2010/01/sicp-exercise-119-computing-fibonacci.html
(defn fast-fib
  ([n] (fast-fib 1 0 0 1 n))
  ([a b p q n]
    (cond (= n 0) b
          (even? n) (fast-fib
                      a
                      b
                      (+ (* p p) (* q q))
                      (+ (* 2 (* p q)) (* q q))
                      (/ n 2))
          :else (fast-fib
                  (+ (* b q) (* a q) (* a p))
                  (+ (* b p) (* a q))
                  p
                  q
                  (- n 1))
          )))

(println [(fib 6) (fast-fib 6)])

(println "---Exercise 1.20---")

; Implemented in scala:
; def ifNormal[A](pr: => Boolean, t: => A, f: => A): A = pr match {
;    case true  => t
;    case false => f
;  }
;
;  def ifApplic[A](pr: => Boolean, t: A, f: A): A = pr match {
;    case true  => t
;    case false => f
;  }
;
;  def remainder(a: Int, b: Int): Int = {
;    println(s"remainder(a=$a, b=$b)")
;    a % b
;  }
;
;  def gcdNormal(a: Int, b: Int): Int =
;    ifNormal[Int](b == 0, a, gcdNormal(b, remainder(a, b)))
;
;  def gcdApplic(a: Int, b: Int): Int =
;    ifApplic[Int](b == 0, a, gcdApplic(b, remainder(a, b)))
;
;  println("---gcdNormal---")
;  println(gcdNormal(206, 40))
;  println("---gcdApplic---")
;  println(gcdApplic(206, 40))

(println "---Exercise 1.21---")

; Naiive next-div.
(defn next-div [t] (inc t))

(defn find-divisor
  [n t] (cond (> (* t t) n) n
              (= (mod n t) 0) t
              :else (find-divisor n (next-div t))))

(defn smallest-divisor [n] (find-divisor n 2))

; 199 and 1999 are prime; 19999 is divisible by 7 and 2857.
(println [(smallest-divisor 199), (smallest-divisor 1999), (smallest-divisor 19999)])

(println "---Exercise 1.22---")

(defn prime? [n] (= n (smallest-divisor n)))

(defn search-for-primes
  ([m n] (search-for-primes m n (vector)))
  ([m n primes-so-far]
    (cond (= n 0) primes-so-far
          (prime? m) (search-for-primes (inc m) (dec n) (conj primes-so-far m))
          :else (search-for-primes (inc m) n primes-so-far))))

; The timing ends up being very noisy. Maybe a property of the JVM?
(defn prime-timing []
  (do
    (doseq [m [1000 10000 100000]]
      (do (doseq [prime (search-for-primes m 3)]
            (do (print prime)
                (print " ")
                (time (prime? prime))))
          (println "---")))))

(prime-timing)

(println "")
(println "---Exercise 1.23---")

; Override the next-div function.
(defn next-div [t] (if (= t 2) 3 (+ t 2)))
(prime-timing)

(println "---Exercise 1.24---")

(defn expmod [base exp m]
  (cond (= exp 0) 1
          (even? exp) (mod (square (expmod base (/ exp 2) m)) m)
          :else (mod (* base (expmod base (- exp 1) m)) m))
    )

(defn try-it [a n]
  (= (expmod a n n) a))

(defn fermat-test [n]
  (try-it (inc (rand-int (dec n))) n))

(defn fast-prime? [n times]
  (cond (= times 0) true
        (fermat-test n) (fast-prime? n (dec times))
        :else false))

; Override the prime function.
(defn prime? [n] (fast-prime? n 1))
(prime-timing)

(println "---Exercise 1.25---")
; I think they are functionally equivalent.
; In both cases they only terminate once n has decreased to zero.
; The only difference is that the first expmod adds the modulo
; inside the returned expression.

(println "---Exercise 1.26---")
; Why is this O(n) and not O(log n)?
; Using square: each invocation of expmod(exp = n) yields a single invocation of expmod(exp = n / 2). So the number of computations is proportional to the sum of the logs of the exps.
; For example, starting with exp = 8, the number of computations is proportional to
;	  log(4) + log(2) + log(1) = log(8)
; Using multiplication: each invocation of expmod(exp = n) invokes two more expmod(exp = n / 2).
; For example, starting with exp = 8, the number of computations is proportional to
;	  2 * log2(4) + 4 * log2(2) + 8 * log2(1) = 8

(println "---Exercise 1.27---")
; Smallest carmichael numbers: 561, 1105, 1729, 2465, 2821, 6601
; These are numbers which fool the fermat test.

(def realprimes [5 7 11 13])
(def carmichaels [561 1105 1729 2465 2821])                 ; these aren't really prime.
(defn check-congruent
  ([n] (check-congruent n 1))
  ([n a] (cond (= n a) true
               (= (expmod a n n) (mod a n)) (check-congruent n (inc a))
               :else false)))

(doseq [n realprimes] (println [n, (check-congruent n)]))
(doseq [n carmichaels] (println [n, (check-congruent n)]))

(println "---Exercise 1.28---")

(defn square-check [x m]
        (if (and
              (not (or
                     (= x 1)
                     (= x (- m 1))))
              (= (mod (* x x) m) 1))
          0
          (mod (* x x) m)
          ))

(defn expmod [base exp m]
        (cond (= exp 0) 1
              (even? exp) (square-check (expmod base (/ exp 2) m) m)
              :else (mod (* base (expmod base (- exp 1) m)) m)))

(defn try-it [a n] (= (expmod a (- n 1) n) 1))

(defn miller-rabin-test [n]
  (try-it (+ 2 (rand-int (- n 2))) n))

(doseq [n carmichaels] (println [n, (miller-rabin-test n)]))