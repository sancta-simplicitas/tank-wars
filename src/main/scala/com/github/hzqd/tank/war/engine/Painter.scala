package com.github.hzqd.tank.war.engine

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.text.Font
import scala.collection.mutable
import com.github.hzqd.tank.war.ext.Fn.KtStd

object Painter {
    private var graphContext: GraphicsContext = _
    private val imageCache = mutable.HashMap[String, Image]()

    def set(graphicsContext: GraphicsContext): Unit = graphContext = graphicsContext

    /**
     * 获取图片大小
     */
    def size(imagePath: String): Array[Int] = {
        imageCache(imagePath).let { i =>
            Array[Int](i.getWidth.toInt, i.getHeight.toInt)
        }
        new Image(imagePath).let { i =>
            imageCache(imagePath) = i
            Array[Int](i.getWidth.toInt, i.getHeight.toInt)
        }
    }

    /**
     * 绘制图片
     */
    def drawImage(imagePath: String, x: Int, y: Int) {
        graphContext.let { g =>
            imageCache(imagePath).let { i =>
                g.drawImage(i, x.toDouble, y.toDouble)
            }
            new Image(imagePath).let { i =>
                imageCache(imagePath) = i
                g.drawImage(i, x.toDouble, y.toDouble)
            }
        }
    }

    /**
     * 绘制颜色
     */
    def drawColor(color: Color, x: Int, y: Int, width: Int, height: Int) {
        graphContext.let { g => import g._
            setFill(color)
            fillRect(x.toDouble, y.toDouble, width.toDouble, height.toDouble)
            setFill(Color.BLACK)
        }
    }

    /**
     * 绘制文本
     */
    def drawText(text: String, x: Int, y: Int, color: Color = Color.BLACK, font: Font = Font.getDefault) {
        graphContext.let { g => import g._
            setFill(color)
            setFont(font)
            fillText(text, x.toDouble, y.toDouble)
            setFont(Font.getDefault)
            setFill(Color.BLACK)
        }
    }
}