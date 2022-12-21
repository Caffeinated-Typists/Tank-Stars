package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadScreen implements Screen {
    private TankStars game;
    private MainScreen mainScreen;
    private ButtonGenerator buttongen;
    private Stage loadStage;
    private Camera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Texture gamebackground;

    public LoadScreen(TankStars game, MainScreen mainScreen) {
        this.game = game;
        this.mainScreen = mainScreen;
        buttongen = new ButtonGenerator();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(TankStars.WIDTH, TankStars.HEIGHT, camera);
        loadStage = new Stage(viewport, batch);

        // back button
        ImageTextButton back = buttongen.createButton("BACK");
        buttongen.setNextScreen(back, mainScreen, this.game);
        back.setPosition(TankStars.WIDTH / 2f - back.getWidth() - 10 , TankStars.HEIGHT / 5f - back.getWidth()/ 2);

        // load game button
        ImageTextButton load = buttongen.createButton("LOAD");
        buttongen.setNextScreen(load, mainScreen, this.game);
        load.setPosition(TankStars.WIDTH / 2f + 10, TankStars.HEIGHT / 5f - load.getWidth()/ 2);

        //adding game background
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        // making 10 buttons
        for (int i = 0; i < 5; i++) {
            ImageTextButton load_game_l = buttongen.createButton("LOAD GAME " + (i + 1), "load_game.png");
            load_game_l.setPosition(TankStars.WIDTH/2f - (load_game_l.getWidth()) - 30, TankStars.HEIGHT - (load_game_l.getWidth()/ 3 * (i+1)));

            ImageTextButton load_game_r = buttongen.createButton("LOAD GAME " + (i + 6), "load_game.png");
            load_game_r.setPosition(TankStars.WIDTH/2f + 30, TankStars.HEIGHT - (load_game_l.getWidth()/ 3 * (i+1)));
            loadStage.addActor(load_game_l);
            loadStage.addActor(load_game_r);
        }

        loadStage.addActor(back);
        loadStage.addActor(load);
//        loadStage.addActor(test);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(loadStage);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

        batch.begin();
        batch.draw(gamebackground, 0, 0, TankStars.WIDTH, TankStars.HEIGHT);
        batch.end();

        loadStage.act();
        loadStage.draw();
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
