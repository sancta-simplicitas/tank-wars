package com.github.hzqd.tank.war.model

import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.business.{Attackable, Blockable, Destroyable, Sufferable}
import com.github.hzqd.tank.war.engine.Painter.drawImage
import com.github.hzqd.tank.war.ext.Fn.KtStd

/** 大本营 */
case class Camp(override var x: Int, override var y: Int) extends Blockable with Sufferable with Destroyable {

    override var blood: Int = 12
    override var width = block * 2
    override var height = block + 32

    override def draw() {
        "img/1/tugai.net.20101117144551.gif".let { t =>
            "img/1/wall.gif".let { w =>
                "img/camp.png".let { c =>
                    if (blood <= 3) {
                        width = block
                        height = block
                        x = (gameWidth - block) / 2
                        y = gameHeight - block
                        drawImage(c, x, y)
                    } else if (blood <= 6) {
                        drawImage(w, x, y)
                        drawImage(w, x + block / 2, y)
                        drawImage(w, x + block, y)
                        drawImage(w, x + block + block / 2, y)
                        drawImage(w, x, y + block / 2)
                        drawImage(w, x, y + block)
                        drawImage(w, x + block + block / 2, y + block / 2)
                        drawImage(w, x + block + block / 2, y + block)
                        drawImage(c, x + block / 2, y + block / 2)
                    } else {
                        drawImage(t, x, y)
                        drawImage(t, x + block, y)
                        drawImage(t, x, y + block / 2)
                        drawImage(t, x + block + block / 2, y + block / 2)
                        drawImage(c, x + block / 2, y + block / 2)
                    }
                }
            }
        }
    }

    override def notifySuffer(attackable: Attackable): Array[View] = {
        blood -= attackable.attackPower
        if (blood == 3 || blood == 6) {
            return Array(
                Blast(x, y),
                Blast(x + block / 2, y),
                Blast(x + block, y),
                Blast(x, y + block / 2),
                Blast(x + block, y + block / 2),
                Blast(x, y + block),
                Blast(x + block, y + block),
                Blast(x + block / 2, y + block / 2)
            )
        }
        null
    }

    override def isDestroyed() = blood <= 0

    override def showDestroy(): Array[View] = {
        Array(
            Blast(x - 32, y - 32),
            Blast(x, y - 32),
            Blast(x + 32, y - 32),
            Blast(x - 32, y),
            Blast(x, y),
            Blast(x + 32, y),
            Blast(x - 32, y + 32),
            Blast(x, y + 32),
            Blast(x + 32, y + 32)
        )
    }
}