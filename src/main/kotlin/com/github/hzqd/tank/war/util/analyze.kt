package com.github.hzqd.tank.war.util

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.model.Bullet
import com.github.hzqd.tank.war.model.Tank

fun checkDirect(ins: Tank) {
    when (ins.currentDirection) {
        Direction.UP -> ins.y -= ins.speed
        Direction.DOWN -> ins.y += ins.speed
        Direction.LEFT -> ins.x -= ins.speed
        Direction.RIGHT -> ins.x += ins.speed
    }
    //越界判断：
    if (ins.x < 0) ins.x = 0
    if (ins.x > Config.gameWidth - ins.width) ins.x = Config.gameWidth - ins.width
    if (ins.y < 0) ins.y = 0
    if (ins.y > Config.gameHeight - ins.height) ins.y = Config.gameHeight - ins.height
}

fun calcBullet(ins: Tank) = run {
    Bullet(ins, ins.currentDirection) { bulletWidth, bulletHeight ->
        val tankX = ins.x
        val tankY = ins.y
        val tankWidth = ins.width
        val tankHeight = ins.height
        //计算子弹真实的坐标：
        var bulletX = 0
        var bulletY = 0
        when (ins.currentDirection) {
            Direction.UP -> {
                bulletX = tankX + (tankWidth - bulletWidth) / 2
                bulletY = tankY - bulletHeight / 2
            }
            Direction.DOWN -> {
                bulletX = tankX + (tankWidth - bulletWidth) / 2
                bulletY = tankY + tankHeight - bulletHeight / 2
            }
            Direction.LEFT -> {
                bulletX = tankX - bulletWidth / 2
                bulletY = tankY + (tankHeight - bulletHeight) / 2
            }
            Direction.RIGHT -> {
                bulletX = tankX + tankWidth - bulletWidth / 2
                bulletY = tankY + (tankHeight - bulletHeight) / 2
            }
        }
        Pair(bulletX, bulletY)
    }
}