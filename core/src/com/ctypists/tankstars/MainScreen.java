package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainScreen implements Screen {

    Game game;
    OrthographicCamera camera;
    ExtendViewport viewport;
    SpriteBatch batch;
    Stage stage;
    AssetManager manager;
    Texture background;
    Sprite backgroundSprite;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(TankStars.WIDTH, TankStars.HEIGHT, camera);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        camera.setToOrtho(false, TankStars.WIDTH, TankStars.HEIGHT);

        background = new Texture(Gdx.files.internal("mainScreenBackground.png"));
        backgroundSprite = new Sprite(background);

        ButtonGenerator buttongen = new ButtonGenerator();
        ImageTextButton button = buttongen.createButton("NEW GAME");
        buttongen.setNextScreen(button, new GameScreen((game)), game);
        button.setPosition((TankStars.WIDTH / 2f) - button.getWidth() / 2, TankStars.HEIGHT/ 2f - button.getWidth() / 2);
        button.getLabel().setFontScale(1f);
        button.getLabel().setColor(1, 0, 0, 1);
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                game.setScreen(new GameScreen(game));
//            }
//        });
        stage.addActor(button);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        backgroundSprite.draw(batch);
        batch.end();
        stage.draw();

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
        batch.dispose();
    }
}
