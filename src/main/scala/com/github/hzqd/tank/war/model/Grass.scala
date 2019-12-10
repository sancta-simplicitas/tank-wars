package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.engine.Painter

/**草坪*/
case class Grass(override val x: Int, override val y: Int) extends View {
    //位置：
//    override var x = 200
//    override var y = 200
    //宽高：
    override var width = Config.block
    override var height = Config.block
    //显示行为：
    override def draw() = Painter.drawImage("img/grass.png", x, y)
}