package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseMenu extends Table {
    ImageTextButton resumeButton, saveButton, exitButton;
    ButtonGenerator buttonGenerator;

    public PauseMenu() {
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBG.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundTexture.getTextureData().prepare();
        Pixmap backgroundPixmap = backgroundTexture.getTextureData().consumePixmap();

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();

        pixmap.drawPixmap(backgroundPixmap, Gdx.graphics.getWidth() / 2 - backgroundTexture.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - backgroundTexture.getHeight() / 2,
                0,
                0,
                backgroundTexture.getWidth(),
                backgroundTexture.getHeight());

        TextureRegionDrawable background = new TextureRegionDrawable(new Texture(pixmap));
        setBackground(background);
        setFillParent(true);
//        setBounds(0, 0, backgroundTexture.getWidth() / 2f, backgroundTexture.getHeight() / 2f);
        buttonGenerator = new ButtonGenerator();
        resumeButton = buttonGenerator.createButton("RESUME");
        saveButton = buttonGenerator.createButton("SAVE");
        exitButton = buttonGenerator.createButton("EXIT");
        add(resumeButton);
        row();
        add(saveButton).padTop(20);
        row();
        add(exitButton).padTop(20);
    }
}
