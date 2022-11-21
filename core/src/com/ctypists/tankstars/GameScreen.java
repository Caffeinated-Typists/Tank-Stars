package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class GameScreen implements Screen {
    Game game;
    SpriteBatch batch;
    Texture gamebackground;
    Stage gameStage;
    Window settingsWindow;
    Texture healthBarL;
    Texture healthBarR;


    public GameScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // Textures
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        healthBarL = new Texture(Gdx.files.internal("HealthBarL.png"));
        healthBarR = new Texture(Gdx.files.internal("HealthBarR.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//        settingsWindow = new Windo/w("Settings", gameStage.getSkin());

//        ButtonGenerator buttongen = new ButtonGenerator();
//        ImageTextButton settings = buttongen.createButton("SETTINGS");
//        settings.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                settingsWindow = new Window();
//            }
//        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        batch.begin();

        // background
        batch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //health bars
        batch.draw(healthBarL, TankStars.WIDTH/2f - healthBarL.getWidth() - 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarL.getWidth(), healthBarL.getHeight());
        batch.draw(healthBarR, TankStars.WIDTH/2f + 50, TankStars.HEIGHT - 10 - healthBarR.getHeight(), healthBarR.getWidth(), healthBarR.getHeight());

        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
