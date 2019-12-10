package com.github.hzqd.tank.war.engine

import java.lang.System._
import com.github.hzqd.tank.war.ext.Fn.KtStd
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.{Group, Scene}
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.paint.Color
import javafx.stage.Stage
import scala.collection.mutable
import scala.util.control.Breaks.break

abstract class Window(
    private val title: String = "New Game",
    private val icon: String = "icon/logo.png",
    val width: Int = 800,
    val height: Int = 600
) extends Application() {
    val canvas: MyCanvas = MyCanvas(width, height)
    private val looper = Looper(this)
    var fps = 0L
    private var running = false
    private val keyRecorder = mutable.Map[KeyCode, KeyEvent]()
    private var currentKey: KeyCode = _

    override def start(primaryStage: Stage) {
        val group = new Group()
        group.getChildren.add(canvas)
        val scene = new Scene(group, width.toDouble, height.toDouble)
        //为画笔设置源
        Painter.set(canvas.getGraphicsContext2D)
        import primaryStage._
        setTitle(title)
        setScene(scene)
        setResizable(false)
        getIcons.add(new Image(icon))
        setOnCloseRequest { _ =>
            looper.stop()
            running = false
            exit(0)
        }
        show()

        scene.setOnKeyPressed(event => new Thread {
            Thread.currentThread().setName("hm-key")
            currentKey = event.getCode
            keyRecorder(event.getCode) = event
            onKeyPressed(event)
        }.start())

        scene.setOnKeyPressed(event => {
            keyRecorder.remove(event.getCode)
            if (currentKey == event.getCode) currentKey = null
        })
        //初始化回调
        onCreate()
        looper.start()
        running = true

        new Thread {
            Thread.sleep(200)
            while (true) {
                Thread.sleep(80)
                if (!running) break
                keyRecorder.filter(_._1 != currentKey).foreach(_._2 let onKeyPressed)
            }
        }.start()

        new Thread {
            Thread.sleep(200)
            while (true) {
                Thread.sleep(20)
                if (!running) break
                onRefresh()
            }
        }.let { t => import t._
            setDaemon(true)
            setPriority(Thread.MAX_PRIORITY)
            t.start()
        }

    }

    def onCreate()

    def onDisplay()

    def onRefresh()

    def onKeyPressed(event: KeyEvent)

    case class Looper(private val window: Window) extends AnimationTimer {
        private var lastTime = 0L

        override def handle(now: Long) {
            lastTime match {
                case 0L => lastTime = now
                case _ => window.fps = 1000 * 1000 * 1000 / (now - lastTime)
            }
            lastTime = now

            window.let { w =>
                w.canvas.getGraphicsContext2D.let { g =>
                    g.setFill(Color.BLACK)
                    g.fillRect(0.0, 0.0, w.width.toDouble, w.height.toDouble)
                }
                w.onDisplay()
            }
            gc()
        }
    }

    case class MyCanvas(width: Int = 800, height: Int = 600) extends Canvas {
        setWidth(width.toDouble)
        setHeight(height.toDouble)
    }
}