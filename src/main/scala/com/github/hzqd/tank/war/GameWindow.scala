package com.github.hzqd.tank.war

import java.io.{BufferedReader, InputStreamReader}
import java.util.concurrent.CopyOnWriteArrayList
import com.github.hzqd.tank.war.Config._
import com.github.hzqd.tank.war.business._
import com.github.hzqd.tank.war.engine.Window
import com.github.hzqd.tank.war.enums.Direction
import com.github.hzqd.tank.war.enums.Direction.Direction
import com.github.hzqd.tank.war.ext.Fn.KtStd
import com.github.hzqd.tank.war.model._
import javafx.scene.input.KeyCode._
import javafx.scene.input.KeyEvent
import scala.collection.mutable.ArrayBuffer

class GameWindow extends Window("坦克大战 v0.1", "img/logo.jpg", Config.gameWidth, Config.gameHeight) {
    //管理元素的集合：
    //private val views = arrayListOf<View>()
    private val views = new CopyOnWriteArrayList[View]()
    private val tank = MyTank()       // 晚点创建 /Scala先赋值为null => 直接创建，没必要先为null
    private var gameOver = false      // 游戏结束，默认不结束
    private var enemyTotalSize = 20   // 敌人总数
    private var enemyActiveSize = 6   // 敌人最多显示数量
    private val enemyBornLocation = ArrayBuffer[(Int, Int)]() // 敌方出生点
    private var bornIndex = 0
    import views._

    override def onCreate() {
        //读文件创建地图，读取行，循环遍历：
        var lineNum = 0
        getClass.getResourceAsStream("/map/1.map").let ( i =>
            new BufferedReader(new InputStreamReader(i, "utf-8"))
        ).lines.forEach { line =>
            var columnNum = 0
            line.toCharArray.foreach { column => import enemyBornLocation._
                column match {
                    case '砖' => add(Wall(columnNum * block, lineNum * block))
                    case '铁' => add(Steel(columnNum * block, lineNum * block))
                    case '草' => add(Grass(columnNum * block, lineNum * block))
                    case '水' => add(Water(columnNum * block, lineNum * block))
                    case '敌' => addOne((columnNum * block, lineNum * block))
                }
                columnNum += 1
            }
            lineNum += 1
        }
        //添加我方坦克：
        add(tank)
        add(Camp(gameWidth / 2 - block, gameHeight - 90))
    }

    override def onDisplay() = forEach(_.draw())  // 绘制地图中的元素 /views.forEach()

    override def onKeyPressed(event: KeyEvent) {
        import Direction._
        import tank._
        if (!gameOver) event.getCode match {
            case W => move(UP)
            case S => move(DOWN)
            case A => move(LEFT)
            case D => move(RIGHT)
            case ENTER => add(shot())
        }
    }

    override def onRefresh() {
        /**自动销毁：*/  //views.forEach中判断是否自动销毁：
        forEach {
            case d: Destroyable => if (d.isDestroyed) {
                remove(d)
                d match { case _: Enemy => enemyTotalSize -= 1 }
                d.showDestroy().let(addAll())
            }
        }
        if (gameOver) return
        /**业务逻辑：
         ***判断运动体和阻塞体是否发生碰撞：
        (1)找到运动的物体：
        (2)找到阻塞的物体：
        (3)遍历集合 -> 是否发生碰撞：
         */
        forEach { case m: Movable => var badDirection: Direction = null
                                     var badBlock: Blockable = null
            forEach {   // move和block是否碰撞：
                case b: Blockable => if (m != b) {
                    badDirection = m willCollision b
                    badBlock = b
                    return
                }
            }
            //找到和move碰撞的block与碰撞的方向 ; 通知可移动的物体，会在哪个方向碰撞。
            m.notifyCollision(badDirection, badBlock)
        }
        /**自动移动：*/
        forEach { case a: AutoMovable => a.autoMove() }
        /**检测攻击体与受攻体是否碰撞：*/
        //过滤有攻击和受攻能力的物体 且 攻击方的目标不能是自己：
        forEach {
            case a: Attackable => forEach {                             //判断是否发生碰撞：
                case s: Sufferable => if ((a.owner != s) && (a != s) && a.isCollision(s)) {
                    //产生碰撞，找到碰撞者；通知攻击者和被攻击者，产生碰撞：
                    a notifyAttack s
                    s.notifySuffer(a).let(addAll())    //显示受攻效果
                    return
                }
            }
        }
        /**检测自动射击*/
        forEach { case a: AutoShotable => a.autoShot().let(add) }
        /**检测游戏结束*/
        forEach(c => if (!c.isInstanceOf[Camp] || enemyTotalSize <= 0) gameOver = true)
        /**检测敌方出生*/
        var e = 0
        forEach { case _: Enemy => e += 1 }
        if (enemyTotalSize > 5 && e < enemyActiveSize) {
            (bornIndex % enemyBornLocation.size).let ( i =>
                enemyBornLocation(i).let( e =>
                    add(Enemy(e._1, e._2))))
            bornIndex += 1
        }
    }
}