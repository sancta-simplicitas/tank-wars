package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.business.Attackable
import com.github.hzqd.tank.war.business.Blockable
import com.github.hzqd.tank.war.business.Sufferable
import org.itheima.kotlin.game.core.Painter.INSTANCE.drawImage

/**铁墙*/
case class Steel(override var x: Int, override var y: Int) extends Blockable with Sufferable {
    override var blood = 0
    //位置：
//    override var x = 200
//    override var y = 200
    //宽高：
    override var width = Config.block
    override var height = Config.block
    //显示行为：
    override def draw() = drawImage("img/steels.gif", x, y)
    override def notifySuffer(attackable: Attackable): Array[View] = null
}