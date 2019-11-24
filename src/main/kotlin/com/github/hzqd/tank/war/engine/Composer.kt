package com.github.hzqd.tank.war.engine

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine

object Composer {
    fun play(soundPath: String) {
        BufferedInputStream(javaClass.getResourceAsStream("/$soundPath")).let {
            AudioSystem.getAudioInputStream(it)
        }.run {
            with(format) {
                (frameSize * frameLength).toInt().let { size ->
                    ByteArray(size).let { audio ->
                        read(audio, 0, size)
                        (AudioSystem.getLine(DataLine.Info(Clip::class.java, this, size)) as Clip).apply {
                            open(this@with, audio, 0, size)
                        }.start()
                    }
                }
            }
        }
    }
}