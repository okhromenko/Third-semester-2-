package com.gmlibgdxgame.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils

class MyGame : ApplicationAdapter() {
    lateinit var font: BitmapFont
    internal lateinit var  batch: SpriteBatch
    internal lateinit var fruit: Texture
    lateinit var camera: OrthographicCamera
    var rectangles = mutableListOf<Rectangle>()
    var lastrec: Long = 0
    var score: Long = 0
    var allfruits: Long = 0
    var time: Long = 0
    var n: Int = 3
    var win = "You Win!"
    var lose = "You Lose!"
    var heart: Texture? = null
    var button: Texture? = null


    override fun create(){
        batch = SpriteBatch()
        fruit = Texture("pin.png")
        heart = Texture("heart.png")
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)
        font = BitmapFont()
        time = System.currentTimeMillis()
        font.getData().setScale(2f)
        button = Texture("restart1.png")
        make()
    }

    fun restartGame() {
        time = 0
        time = System.currentTimeMillis()
        score = 0
        n = 3
    }

    fun make(){
        val rectangle = Rectangle()
        rectangle.y = 480f
        rectangle.x = MathUtils.random(0f, 800f - 80)
        rectangle.height = 140f
        rectangle.width = 71f
        rectangles.add(rectangle)
        lastrec = TimeUtils.nanoTime()
    }

    fun endGame() {
        val glyphLayout = GlyphLayout()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        print("end")
        rectangles.clear()
        batch.begin()
        glyphLayout.setText(font, win)
        font.draw(batch, glyphLayout, 400f, 240f)
        batch.draw(button, 10f, 10f)
        if (Gdx.input.isTouched) {
            val x = Gdx.input.getX().toFloat()
            val y = Gdx.input.getY().toFloat()
            if (x < 64f && x > 10f) {
                if (y < 480f && y > 420f) {
                    restartGame()
                }
            }
        }
        batch.end()
    }

    fun loseGame() {
        val glyphLayout = GlyphLayout()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        print("end")
        rectangles.clear()
        batch.begin()
        glyphLayout.setText(font, lose)
        font.draw(batch, glyphLayout, 400f, 240f)
        batch.draw(button, 10f, 10f)

        if (Gdx.input.isTouched) {
            val x = Gdx.input.getX().toFloat()
            val y = Gdx.input.getY().toFloat()
            if (x < 64f && x > 10f) {
                if (y < 480f && y > 420f) {
                    restartGame()
                }
            }
        }
        batch.end()
    }

    override fun render(){
        if(System.currentTimeMillis() - time >= 30000 && n > 0){
            endGame()
        } else if (n < 0) {
            loseGame()
        } else {
            if (TimeUtils.nanoTime() - lastrec > 700000000) {
                make()
            }
            font.setColor(0f, 0f, 255f, 255f)
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            camera.update()
            batch.projectionMatrix = camera.combined
            var touchPos: Vector3 = Vector3(1000f, 1000f, 0f)

            if (Gdx.input.isTouched) {
                touchPos = Vector3(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
                camera.unproject(touchPos)
            }

            batch.begin()
            for (r in rectangles) {
                batch.draw(fruit, r.x, r.y)
                r.y -= 80 * Gdx.graphics.deltaTime
            }
            var times: Long = 0
            times = (System.currentTimeMillis() - time) / 1000
            font.draw(batch, "score: " + score, 30f, 470f)
            font.draw(batch, "time: " + times, 160f, 470f)
            if (n > 0) {
                batch.draw(heart, 730f, 420f)
                if (n >= 2) {
                    batch.draw(heart, 665f, 420f)
                }
                if (n == 3) {
                    batch.draw(heart, 600f, 420f)
                }
            }
            batch.end()

            val iter = rectangles.iterator()
            while (iter.hasNext()) {
                val fruitdrop = iter.next()
                if (fruitdrop.y < 0) {
                    iter.remove()
                    allfruits++
                    n--
                }
                if (fruitdrop.contains(touchPos.x, touchPos.y)) {
                    iter.remove()
                    score++
                    println(score)
                }
            }
        }
    }

    override fun dispose() {
        batch.dispose()
        fruit.dispose()
        font.dispose()
    }
}
