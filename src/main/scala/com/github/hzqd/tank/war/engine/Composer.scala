package com.github.hzqd.tank.war.engine

import java.io.BufferedInputStream
import javax.sound.sampled._
import com.github.hzqd.tank.war.ext.Fn.KtStd
import scala.collection.mutable
import com.github.hzqd.tank.war.Config._

class Audio(path: String) {
    private val audioInputStream = AudioSystem.getAudioInputStream(
        new BufferedInputStream(
            getClass.getResourceAsStream(
                path
            )
        )
    )

    private val af = audioInputStream.getFormat
    private val size = (af.getFrameSize * audioInputStream.getFrameLength).toInt
    private val audio = new Array[Byte](size)
    private val info = new DataLine.Info(classOf[Clip], af, size)   // .Info(Clip::class.java, af, size) /Kotlin

    def play() {
        audioInputStream.read(audio, 0, size)
        AudioSystem.getLine(info).asInstanceOf[Clip].let { c =>
            c.open(af, audio, 0, size)
            c.start()
        }
    }
}

object Composer {
    private val buffer = mutable.HashMap[String, Audio]()

    // init:
    buffer.toSet((HIT, new Audio(HIT)))

    def play(audioName: String) {
        buffer.get(HIT) match { // buffer.get(HIT)!!.play() /Kotlin直接非空断言
            case Some(v) => v.play()
            case None => ()
        }
    }
}