package com.github.hzqd.tank.war.business

import com.github.hzqd.tank.war.model.View

/**受攻的能力：*/
trait Sufferable extends View {
    var blood: Int  //生命值
    def notifySuffer(attackable: Attackable): Array[View]
}