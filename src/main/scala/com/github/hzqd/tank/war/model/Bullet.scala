package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.business.{Attackable, AutoMovable, Destroyable, Sufferable}
import com.github.hzqd.tank.war.engine.Painter.{size, drawImage}
import com.github.hzqd.tank.war.enums.Direction
import Direction._
import com.github.hzqd.tank.war.ext.Fn.KtStd

/**子弹*/
case class Bullet(override val owner: View, override val currentDirection: Direction, create: (Int, Int) => (Int, Int))
        extends AutoMovable with Destroyable with Attackable with Sufferable {
    override val blood = 0
    //给子弹一个方向，方向由坦克来决定：
    override var width = block
    override var height = block
    override var x = 0
    override var y = 0
    override val speed = 8
    override val attackPower: Int = 1
    private var isDestroyedVal = false
    private val imagePath = currentDirection match {
        case UP   => "img/bullet_up.bmp"
        case DOWN => "img/bullet_down.bmp"
        case LEFT => "img/bullet_left.bmp"
        case RIGHT=> "img/bullet_right.bmp"
    }

    //先计算宽度和高度：
    size(imagePath).let { s =>
        width = s(0)
        height = s(1)
    }
    create(width, height).let { t =>
        x = t._1
        y = t._2
    }

    override def draw() {
        drawImage(imagePath, x, y)
    }

    override def autoMove() {
        //根据自己的方向改变自己的坐标：
        currentDirection match {
            case UP   => y -= speed
            case DOWN => y += speed
            case LEFT => x -= speed
            case RIGHT=> x += speed
        }
    }

    override def isDestroyed: Boolean = {
        if (isDestroyedVal) return true
        //子弹脱离屏幕需要被销毁：
        if (x < -width)     return true
        if (x > gameWidth)  return true
        if (y < -height)    return true
        if (y > gameHeight) return true
        false
    }

    override def isCollision(sufferable: Sufferable): Boolean = checkCollision(sufferable)

    override def notifyAttack(sufferable: Sufferable) { isDestroyedVal = true }

    override def notifySuffer(attackable: Attackable): Array[View] = Array(Blast(x, y))
}