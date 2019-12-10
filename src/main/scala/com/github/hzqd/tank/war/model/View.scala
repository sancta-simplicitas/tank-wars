package com.github.hzqd.tank.war.model

/* 显示的视图，定义显示规范 */
trait View {
    /* 定义属性，用实现类实现 */
    //位置：
    var x: Int
    var y: Int
    //宽高：
    var width: Int
    var height: Int
    //显示：
    def draw()
    //碰撞检测：
    def checkCollision(x1: Int, x2: Int, y1: Int, y2: Int, w1: Int, w2: Int, h1: Int, h2: Int): Boolean = {
        //两个物体的x,y,w,h的比较
        if (y2 + h2 <= y1)      false   //阻挡物在运动物的上方时，不碰撞
        else if (y1 + h1 <= y2) false   //阻挡物在运动物的下方时，不碰撞
        else if (x2 + w2 <= x1) false   //阻挡物在运动物的左方时，不碰撞
        else x1 + w1 > x2               //阻挡物在运动物的右方时，不碰撞
    }
    def checkCollision(view: View): Boolean =
        checkCollision(x, view.x, y, view.y, width, view.width, height, view.height)
}