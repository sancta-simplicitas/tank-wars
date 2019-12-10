package com.github.hzqd.tank.war.business

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.model.View
import Direction._
import com.github.hzqd.tank.war.ext.Fn.KtStd

/**移动的能力：*/
trait Movable extends View {
    //可移动物体存在方向和速度：
    var currentDirection: Direction
    val speed: Int
    //判断移动体是否和阻塞体发生碰撞：
    def willCollision(block: Blockable): Direction = {
        //未来的坐标：
        var x = this.x
        var y = this.y
        //将要碰撞时做判断：
        currentDirection match {
            case UP   => y -= speed
            case DOWN => y += speed
            case LEFT => x -= speed
            case RIGHT=> x += speed
        }
        //TODO:先做边界碰撞检测，再进行碰撞物检测：
        //越界判断：
        Config.let { C =>
            if (x < 0)                      return Direction.LEFT
            if (x > C.gameWidth - width)    return Direction.RIGHT
            if (y < 0)                      return Direction.UP
            if (y > C.gameHeight- height)   return Direction.DOWN
        }
        block.let ( b =>
            checkCollision(b.x, x, b.y, y, b.width, width, b.height, height)
                    .let(if (_) currentDirection else null)
        )
    }

    //通知碰撞：
    def notifyCollision(direction: Direction, block: Blockable)
}