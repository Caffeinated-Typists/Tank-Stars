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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainScreen implements Screen {

    Game game;
    OrthographicCamera camera;
    ExtendViewport viewport;
    SpriteBatch batch;
    Stage mainStage;
    AssetManager manager;
    Texture mainBackground;
    Sprite backgroundSprite;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(TankStars.WIDTH, TankStars.HEIGHT, camera);
        mainStage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(mainStage);
        camera.setToOrtho(false, TankStars.WIDTH, TankStars.HEIGHT);

        mainBackground = new Texture(Gdx.files.internal("mainScreenBackground.png"));
        mainBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(mainBackground);

        ButtonGenerator buttongen = new ButtonGenerator();

        // new game
        ImageTextButton new_game = buttongen.createButton("NEW");
        buttongen.setNextScreen(new_game, new TankMenu(game, this), game);
        new_game.setPosition((TankStars.WIDTH / 2f) - new_game.getWidth() / 2, TankStars.HEIGHT/ 2f);
        new_game.getLabel().setFontScale(1f);
        new_game.getLabel().setColor(1, 0, 0, 1);

        // load game
        ImageTextButton load_game = buttongen.createButton("LOAD");
        buttongen.setNextScreen(load_game, new TankMenu(game, this), game);
        load_game.setPosition(TankStars.WIDTH / 2f - load_game.getWidth() / 2f, TankStars.HEIGHT / 2f - load_game.getWidth()/ 2);


        // exit game
        ImageTextButton exit_game = buttongen.createButton("EXIT");
        exit_game.setPosition(TankStars.WIDTH / 2f - load_game.getWidth() / 2f, TankStars.HEIGHT / 2f - load_game.getWidth());
        exit_game.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        mainStage.addActor(exit_game);
        mainStage.addActor(load_game);
        mainStage.addActor(new_game);
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
        batch.draw(mainBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());batch.end();
        mainStage.draw();

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
