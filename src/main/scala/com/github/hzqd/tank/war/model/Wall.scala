package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.business.Attackable
import com.github.hzqd.tank.war.business.Blockable
import com.github.hzqd.tank.war.business.Destroyable
import com.github.hzqd.tank.war.business.Sufferable
import com.github.hzqd.tank.war.engine.Composer
import com.github.hzqd.tank.war.engine.Painter

/**砖墙*/
case class Wall(override var x: Int, override var y: Int) extends Blockable with Sufferable with Destroyable {
    override var blood: Int = 3
    //位置：
//    override val x = 100
//    override val y = 100
    //宽高：
    override var width = block
    override var height = block
    //显示行为：
    override def draw() {
        Painter.drawImage("img/walls.gif", x, y)
    }

    override def isDestroyed: Boolean = blood <= 0    //砖墙生命值少于或等于零时被销毁

    override def notifySuffer(attackable: Attackable): Array[View] = {
        blood -= attackable.attackPower      //砖墙掉血
        Composer.play(HIT)                   //砖墙喊疼
        Array(Blast(x, y))
    }
}