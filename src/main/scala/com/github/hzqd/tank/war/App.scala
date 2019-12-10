package com.github.hzqd.tank.war

import com.github.hzqd.tank.war.engine.Composer
import javafx.application.Application

object App {
    def main(args: Array[String]): Unit = {
        Composer
        Application launch classOf[GameWindow]
    }
}