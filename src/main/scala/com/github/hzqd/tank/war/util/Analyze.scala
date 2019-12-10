package com.github.hzqd.tank.war.util

import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.model.Bullet
import com.github.hzqd.tank.war.model.Tank

object Analyze {
    def checkDirect(ins: Tank) {
        import ins._
        import Direction._
        currentDirection match {
            case UP   => y -= speed
            case DOWN => y += speed
            case LEFT => x -= speed
            case RIGHT=> x += speed
        }
        //越界判断：
        if (x < 0)                  x = 0
        if (x > gameWidth - width)  x = gameWidth  - width
        if (y < 0)                  y = 0
        if (y > gameHeight-height)  y = gameHeight - height
    }

    def calcBullet(ins: Tank) = {
        import ins._
        Bullet(ins, currentDirection, (bulletWidth, bulletHeight) => {
            val tankX = x
            val tankY = y
            val tankWidth = width
            val tankHeight = height
            //计算子弹真实的坐标：
            var bulletX = 0
            var bulletY = 0
            currentDirection match {
                case Direction.UP => {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                case Direction.DOWN => {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY + tankHeight - bulletHeight / 2
                }
                case Direction.LEFT => {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
                case Direction.RIGHT => {
                    bulletX = tankX + tankWidth - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
            }
            (bulletX, bulletY)
        })
    }
}