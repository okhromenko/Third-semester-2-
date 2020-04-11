package com.gmlibgdxgame.game

//import android.R.attr.checked
//import android.R.attr.font
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
//import org.lwjgl.opengl.Display.getDrawable


class Buttonn {
    var stage: Stage? = null
    var button: TextButton? = null
    var textButtonStyle: TextButtonStyle? = null
    var font: BitmapFont? = null
    var skin: Skin? = null
    var buttonAtlas: TextureAtlas? = null

    fun create() {
        stage = Stage()
        Gdx.input.inputProcessor = stage
        font = BitmapFont()
        skin = Skin()
        buttonAtlas = TextureAtlas(Gdx.files.internal("buttons/buttons.pack"))
        skin!!.addRegions(buttonAtlas)
        textButtonStyle = TextButtonStyle()
        textButtonStyle!!.font = font
        textButtonStyle!!.up = skin!!.getDrawable("up-button")
        textButtonStyle!!.down = skin!!.getDrawable("down-button")
        textButtonStyle!!.checked = skin!!.getDrawable("checked-button")
        button = TextButton("Button1", textButtonStyle)
        stage!!.addActor(button)
    }

    fun render() {
//        render()
        stage!!.draw()
    }
}