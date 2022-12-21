package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.tools.jmod.Main;

import javax.swing.text.View;

public class PauseMenuAlt implements Screen {
    private TankStars game;
    private TankStars tankStars;
    private GameScreen screen;
    private Camera camera;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch batch;
    private ButtonGenerator buttongen;
    private Texture menuBG, background;
    private Pixmap bg;
    private ImageTextButton resumeButton, saveButton, exitButton;
    private ButtonGenerator buttonGenerator;

    public PauseMenuAlt(final TankStars game, final GameScreen screen) {
        this.game = game;
        this.screen = screen;

        bg = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        bg.setColor(0, 0, 0, 0.5f);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(TankStars.WIDTH, TankStars.HEIGHT, camera);
        stage = new Stage(viewport, batch);

        menuBG = new Texture(Gdx.files.internal("menuBG.png"));
        menuBG.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background = new Texture(Gdx.files.internal("BackgroundBlack.png"));
        buttonGenerator = new ButtonGenerator();

        resumeButton = buttonGenerator.createButton("RESUME");
        saveButton = buttonGenerator.createButton("SAVE");
        exitButton = buttonGenerator.createButton("EXIT");

        resumeButton.setPosition(TankStars.WIDTH / 2f - resumeButton.getWidth() / 2, TankStars.HEIGHT / 2f + resumeButton.getHeight() / 2 + 30);
        saveButton.setPosition(TankStars.WIDTH / 2f - saveButton.getWidth() / 2, TankStars.HEIGHT / 2f - saveButton.getHeight() / 2);
        exitButton.setPosition(TankStars.WIDTH / 2f - exitButton.getWidth() / 2, TankStars.HEIGHT / 2f - exitButton.getHeight() * 3 / 2 - 30);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Save");
                game.save_state = true;
                game.setScreen(screen);
            }
        });
        stage.addActor(resumeButton);
        stage.addActor(saveButton);
        stage.addActor(exitButton);



    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        buttonGenerator.setNextScreen(resumeButton, screen, game);
        buttonGenerator.setNextScreen(exitButton, new MainScreen(game), game);

    }

    @Override
    public void render(float delta) {
//        Gdx.gl20.glClearColor(0, 0, 0, 0);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(menuBG, Gdx.graphics.getWidth() / 2f - menuBG.getWidth()/2f, Gdx.graphics.getHeight() / 2f - menuBG.getHeight()/2f);
        batch.end();

        stage.draw();
        stage.act();
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
