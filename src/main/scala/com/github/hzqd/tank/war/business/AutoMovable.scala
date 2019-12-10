package com.github.hzqd.tank.war.business

import com.github.hzqd.tank.war.enums.Direction._
import com.github.hzqd.tank.war.model.View

/**自动移动的能力：*/
trait AutoMovable extends View {
    //方向和速度：
    var currentDirection: Direction
    val speed: Int
    def autoMove()
}