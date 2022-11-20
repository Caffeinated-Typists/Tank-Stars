package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ButtonStyle extends TextButtonStyle {
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public ButtonStyle() {
        font = new BitmapFont();
//        skin = new Skin();
//        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
//        skin.addRegions(buttonAtlas);
        this.font = font;
//        this.up = skin.getDrawable("button");
//        this.down = skin.getDrawable("buttonPressed");
    }
}


