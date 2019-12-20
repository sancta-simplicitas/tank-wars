package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config
import com.github.hzqd.tank.war.business.Destroyable
import org.itheima.kotlin.game.core.Painter.INSTANCE.drawImage
import scala.collection.mutable.ArrayBuffer
import com.github.hzqd.tank.war.ext.Fn.KtStd

/**爆炸物*/
case class Blast(override var x: Int, override var y: Int) extends Destroyable {

    override var width: Int = Config.block
    override var height: Int = Config.block
    private val imagePaths = ArrayBuffer[String]()
    private var index = 0

    (1 to 8).foreach(i => imagePaths.addOne(s"img/blast$i.png"))

    override def draw() {
        (index % imagePaths.size).let(i => drawImage(imagePaths(i), x, y))
        index += 1
    }

    override def isDestroyed() = index >= imagePaths.size
}
