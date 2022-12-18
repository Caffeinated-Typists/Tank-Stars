package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TankMenu implements Screen {
    private Game game;
    private SpriteBatch menuBatch;
    private Stage menuStage;
    private ButtonGenerator buttongen;
    private Texture tank1, gamebackground;
    private ImageButton arrowLeft, arrowRight;
    private ImageTextButton playButton, backButton;
    private MainScreen mainScreen;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    public TankMenu(Game game, MainScreen mainScreen) {
        this.game = game;
        this.mainScreen = mainScreen;
//        System.out.println("TankMenu created");
    }


    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(TankStars.WIDTH, TankStars.HEIGHT, camera);
        menuBatch = new SpriteBatch();
        menuStage = new Stage(viewport, menuBatch);
        buttongen = new ButtonGenerator();
        Gdx.input.setInputProcessor(menuStage);
        tank1 = new Texture(Gdx.files.internal("Tank1.png"));
        tank1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //adding game background
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // creating cycle through arrows
        Texture arrowLeftTexture = new Texture(Gdx.files.internal("arrowLeft.png"));
        arrowLeftTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Texture arrowRightTexture = new Texture(Gdx.files.internal("arrowRight.png"));
        arrowRightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        arrowLeft = new ImageButton(new TextureRegionDrawable( new TextureRegion(( arrowLeftTexture))));
        arrowRight = new ImageButton(new TextureRegionDrawable( new TextureRegion( arrowRightTexture)));

        arrowRight.setPosition(Gdx.graphics.getWidth() - arrowLeft.getWidth() - 100, Gdx.graphics.getHeight() / 2f - arrowLeft.getHeight() / 2);
        arrowLeft.setPosition(Gdx.graphics.getWidth() / 2f + 100, Gdx.graphics.getHeight() / 2f - arrowRight.getHeight() / 2);
        menuStage.addActor(arrowLeft);
        menuStage.addActor(arrowRight);

        // creating play and back button
        playButton = buttongen.createButton("PLAY");
        backButton = buttongen.createButton("BACK");
        playButton.setPosition(Gdx.graphics.getWidth() * 0.75f - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f - playButton.getHeight() / 2 - 100);
        backButton.setPosition(Gdx.graphics.getWidth() * 0.75f - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backButton.getHeight() - 150);

        //adding listeners to buttons
        buttongen.setNextScreen(playButton, new GameScreen(game), game);
        buttongen.setNextScreen(backButton, mainScreen, game);

        menuStage.addActor(playButton);
        menuStage.addActor(backButton);
//        menuStage.addActor(pauseMenu);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        menuBatch.begin();
        menuBatch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menuBatch.draw(tank1,  100, Gdx.graphics.getHeight()/2f - tank1.getHeight() * 0.75f, tank1.getWidth() * 1.5f, tank1.getHeight() * 1.5f);
        menuBatch.end();
        menuStage.draw();
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
        menuStage.dispose();
    }
}


