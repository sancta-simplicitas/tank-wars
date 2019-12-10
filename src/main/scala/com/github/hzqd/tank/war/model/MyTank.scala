package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.business._
import com.github.hzqd.tank.war.engine.Painter
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.enums.Direction.Direction
import com.github.hzqd.tank.war.util.Analyze.{calcBullet, checkDirect}

/**我方坦克*/
case class MyTank() extends Tank() {
    override var x: Int = Config.block * 10
    override var y: Int = Config.block * 10
    override def isDestroyed() = blood <= -10
    override var width: Int = Config.block
    override var height: Int = Config.block
    //方向：
    override var currentDirection: Direction = Direction.UP
    //速度：
    override val speed = 10
    //血量：
    override var blood = 0
    //坦克不能走的方向：
    private var badDirection: Direction = _

    //坦克绘制：
    override def draw() {
        import Direction._
        //根据坦克的方向进行绘制：
        val imagePath = currentDirection match {
            case UP   => "img/p1tankU.gif"
            case DOWN => "img/p1tankD.gif"
            case LEFT => "img/p1tankL.gif"
            case RIGHT=> "img/p1tankR.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    //坦克移动：
    def move(direction: Direction) {
        //判断是否要往碰撞的方向走：
        if (direction == badDirection) return
        //当前的方向和希望移动的方向不一致时，只做方向的改变：
        if (currentDirection != direction) {
            currentDirection = direction
            return
        }
        //坦克坐标的变化 —— 根据不同的方向，改变对应的坐标：
        checkDirect(this)
    }

    //接收碰撞
    override def notifyCollision(direction: Direction, block: Blockable) {
        this.badDirection = direction
    }

    def shot() = calcBullet(this)

    override def notifySuffer(attackable: Attackable): Array[View] = {
        blood -= attackable.attackPower
        Array(Blast(x, y))
    }
}