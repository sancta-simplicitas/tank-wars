package com.github.hzqd.tank.war.business

import com.github.hzqd.tank.war.model.View

//自我回收/销毁的能力：
trait Destroyable extends View {
    def isDestroyed: Boolean
    def showDestroy(): Array[View] = null
}