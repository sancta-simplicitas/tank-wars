import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import com.github.hzqd.tank.war.engine.Composer.play
import com.github.hzqd.tank.war.engine.Painter
import com.github.hzqd.tank.war.engine.Window

/*窗体：
 *继承游戏引擎的窗体：
 */
class MyWindow() extends Window {
    override def onCreate() {
        println("窗体创建..")
    }

    override def onDisplay() {
        //窗体渲染回调：
        //println("onDisplay..")

        //绘制图片：
        Painter.drawImage("enemy3U.gif", 0, 0)

        //绘制颜色：
        Painter.drawColor(Color.WHITE, 20, 20, 100, 100)
    }

    override def onKeyPressed(event: KeyEvent) {
        //按键响应：
        event.getCode match {
            case KeyCode.ENTER => println("点击了Enter按钮")
            case KeyCode.L => play("blast.wav")
        }
    }

    override def onRefresh() {
        //刷新，做业务逻辑和耗时的操作：
    }
}

object Test {
    def main(args: Array[String]): Unit = {
        //启动游戏：
        Application.launch(classOf[MyWindow])
    }
}