package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.business.Blockable
import org.itheima.kotlin.game.core.Painter.INSTANCE.drawImage

/**水墙*/
case class Water(override var x: Int, override var y: Int) extends Blockable {
    //位置：
//    override var x = 200
//    override var y = 200
    //宽高：
    override var width = Config.block
    override var height = Config.block
    //显示行为：
    override def draw() {
        drawImage("img/water.gif", x, y)
    }
}