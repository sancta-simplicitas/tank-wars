package com.github.hzqd.tank.war.engine

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem

object Composer {
    fun play(soundPath: String) = doPlay(soundPath)

    private fun doPlay(soundPath: String) {
        AudioSystem.getClip()?.let {
            BufferedInputStream(javaClass.getResourceAsStream("/${soundPath}")).run {
                AudioSystem.getAudioInputStream(this)
            }.run {
                with(it) {
                    addLineListener { event -> if (event.framePosition.toInt() == frameLength) close() }
                    open(this@run)
                    start()
                }
            }
        }
    }
}