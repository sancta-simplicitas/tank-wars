package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.business._
import org.itheima.kotlin.game.core.Composer.INSTANCE.play
import org.itheima.kotlin.game.core.Painter.INSTANCE.drawImage
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.enums.Direction.Direction
import com.github.hzqd.tank.war.util.Analyze.{calcBullet, checkDirect}
import scala.util.Random

/**敌方坦克*/
case class Enemy(a: Int, b: Int) extends Tank with AutoMovable with AutoShotable {
    override var x: Int = a
    override var y: Int = b
    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8
    override var width: Int = block
    override var height: Int = block
    //坦克不能走的方向：
    private var badDirection: Direction = _
    private var lastShotTime = 0L
    private val shotFrequency = 800
    private var lastMoveTime = 0L
    private val moveFrequency = 50
    override var blood = 2

    override def draw() {
        val imagePath = currentDirection match {
            case Direction.UP   => "img/enemy1U.gif"
            case Direction.DOWN => "img/enemy1D.gif"
            case Direction.LEFT => "img/enemy1L.gif"
            case Direction.RIGHT=> "img/enemy1R.gif"
        }
        drawImage(imagePath, x, y)
    }

    override def notifyCollision(direction: Direction, block: Blockable) {
        badDirection = direction
    }

    override def autoMove() {
        //频率检测：
        val current = System.currentTimeMillis()
        if (current - lastMoveTime < moveFrequency) return
        lastMoveTime = current
        if (currentDirection == badDirection) {
            //不允许往错误方向移动，要求改变自身移动方向：
            currentDirection = rdmDirection(badDirection)
            return
        }
        //坦克坐标的变化 —— 根据不同的方向，改变对应的坐标：
        checkDirect(this)
    }

    private def rdmDirection(bad: Direction): Direction = {
        import Direction._
        val direction = Random.nextInt(4) match {
            case 0 => UP
            case 1 => DOWN
            case 2 => LEFT
            case _ => RIGHT
        }
        //判断丢弃错误方向：
        if (direction == bad) {
            return rdmDirection(bad)
        }
        direction
    }

    override def autoShot(): View = {
        val current = System.currentTimeMillis()
        if (current - lastShotTime < shotFrequency) return null
        lastShotTime = current
        calcBullet(this)
    }

    override def notifySuffer(attackable: Attackable): Array[View] = {
        if (attackable.owner.isInstanceOf[Enemy]) {
            //受攻->不掉血，不交互
            return null
        }
        blood -= attackable.attackPower
        play(HIT)
        Array(Blast(x, y))
    }

    override def isDestroyed() = blood <= 0
}