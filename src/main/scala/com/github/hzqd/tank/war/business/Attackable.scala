package com.github.hzqd.tank.war.business

import com.github.hzqd.tank.war.model.View

/**攻击的能力：*/
trait Attackable extends View {
    val owner: View
    val attackPower: Int
    def isCollision(sufferable: Sufferable): Boolean
    def notifyAttack(sufferable: Sufferable)
}