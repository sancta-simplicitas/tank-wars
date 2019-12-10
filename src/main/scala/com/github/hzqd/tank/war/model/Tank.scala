package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.business._
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.enums.Direction.Direction

abstract class Tank extends Movable with Blockable with Sufferable with Destroyable {
    override var x: Int = 0
    override var y: Int = 0
    override var currentDirection: Direction = Direction.UP
}