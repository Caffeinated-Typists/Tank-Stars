package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TankMenu implements Screen {
    private TankStars game;
    private SpriteBatch menuBatch;
    private Stage menuStage;
    private ButtonGenerator buttongen;
    private Texture tank1, tank2, tank3, gamebackground;
    private ImageButton arrowLeftP1, arrowRightP1, arrowLeftP2, arrowRightP2;
    private ImageTextButton playButton, backButton;
    private MainScreen mainScreen;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private ArrayList<Texture> tanks;
    private int p1_tank, p2_tank;
    private BitmapFont text;
    private boolean gameStarted = false;
    public TankMenu(TankStars game, MainScreen mainScreen) {
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
        tank1 = new Texture(Gdx.files.internal("TankTexture1.png"));
        tank2 = new Texture(Gdx.files.internal("TankTexture2.png"));
        tank3 = new Texture(Gdx.files.internal("TankTexture3.png"));

        tank1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tank2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tank3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        tanks = new ArrayList<Texture>();
        tanks.add(tank1);
        tanks.add(tank2);
        tanks.add(tank3);

        p1_tank = 0;
        p2_tank = 0;
        //adding game background
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // creating cycle through arrows
        Texture arrowLeftTexture = new Texture(Gdx.files.internal("arrowLeft.png"));
        arrowLeftTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Texture arrowRightTexture = new Texture(Gdx.files.internal("arrowRight.png"));
        arrowRightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        arrowLeftP1 = new ImageButton(new TextureRegionDrawable( new TextureRegion(( arrowLeftTexture))));
        arrowRightP1 = new ImageButton(new TextureRegionDrawable( new TextureRegion( arrowRightTexture)));

        arrowLeftP2 = new ImageButton(new TextureRegionDrawable( new TextureRegion(( arrowLeftTexture))));
        arrowRightP2 = new ImageButton(new TextureRegionDrawable( new TextureRegion( arrowRightTexture)));


        arrowRightP1.setPosition(Gdx.graphics.getWidth() - arrowLeftP1.getWidth() - 50, Gdx.graphics.getHeight() / 2f - arrowLeftP1.getHeight() / 2);
        arrowLeftP1.setPosition(Gdx.graphics.getWidth() / 2f + 100, Gdx.graphics.getHeight() / 2f - arrowRightP1.getHeight() / 2);

        arrowRightP2.setPosition(Gdx.graphics.getWidth() / 2f - 100 - arrowLeftP2.getWidth(), Gdx.graphics.getHeight() / 2f - arrowLeftP2.getHeight() / 2);
        arrowLeftP2.setPosition(50, Gdx.graphics.getHeight() / 2f - arrowRightP2.getHeight() / 2);


        arrowLeftP1.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                p2_tank = p2_tank == 0? tanks.size() - 1 : p2_tank - 1;
            }
        });

        arrowRightP1.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                p2_tank = p2_tank == tanks.size() - 1? 0 : p2_tank + 1;
            }
        });

        arrowLeftP2.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                p1_tank = p1_tank == 0? tanks.size() - 1 : p1_tank - 1;
            }
        });

        arrowRightP2.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                p1_tank = p1_tank == tanks.size() - 1? 0 : p1_tank + 1;
            }
        });

        menuStage.addActor(arrowLeftP1);
        menuStage.addActor(arrowRightP1);
        menuStage.addActor(arrowLeftP2);
        menuStage.addActor(arrowRightP2);
        // creating play and back button
        playButton = buttongen.createButton("PLAY");
        backButton = buttongen.createButton("BACK");
        playButton.setPosition(Gdx.graphics.getWidth() * 0.5f - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f - playButton.getHeight() / 2 - 100);
        backButton.setPosition(Gdx.graphics.getWidth() * 0.5f - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backButton.getHeight() - 150);

        //adding listeners to buttons
        buttongen.setNextScreen(backButton, mainScreen, game);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                gameStarted = true;
            }
        });
        menuStage.addActor(playButton);
        menuStage.addActor(backButton);
//        menuStage.addActor(pauseMenu);

        text = new BitmapFont(Gdx.files.internal("new.fnt"));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        menuBatch.begin();
        menuBatch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        text.draw(menuBatch, "Player 1", Gdx.graphics.getWidth() / 4f - 100, Gdx.graphics.getHeight() - 100);
        text.draw(menuBatch, "Player 2", 3 * Gdx.graphics.getWidth() / 4f - 20, Gdx.graphics.getHeight() - 100);

        menuBatch.draw((tanks.get(p1_tank)),  100, Gdx.graphics.getHeight()/2f - tanks.get(p1_tank).getHeight()/2f, tanks.get(p1_tank).getWidth(), tanks.get(p1_tank).getHeight());
        menuBatch.draw((tanks.get(p2_tank)),  Gdx.graphics.getWidth() - tanks.get(p2_tank).getWidth() - 100, Gdx.graphics.getHeight()/2f - tanks.get(p2_tank).getHeight()/2f, tanks.get(p2_tank).getWidth(), tanks.get(p2_tank).getHeight());
        menuBatch.end();
        menuStage.draw();
        if (gameStarted) {
            game.setScreen(new GameScreen(game, p2_tank, p1_tank));
        }
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


