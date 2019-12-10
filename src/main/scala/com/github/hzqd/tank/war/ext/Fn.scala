package com.github.hzqd.tank.war.ext

object Fn {

    implicit class KtStd[T](a: T) {

        def also(f: T => Unit): T = {
            f(a)
            a
        }

        def let[R](f: T => R): R = f(a)

        def takeIf(predicate: T => Boolean): Any = if (predicate(a)) a else null

        def takeUnless(predicate: T => Boolean): Any = if (!predicate(a)) a else null

        def repeat(times: Int, action: Int => Unit): Unit = (0 until times).foreach(action)

    }

}
