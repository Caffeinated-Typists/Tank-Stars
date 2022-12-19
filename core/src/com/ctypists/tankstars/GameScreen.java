package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    Game game;
    SpriteBatch batch;
    Texture gamebackground, ground, healthBarL, healthBarR, joystick, fuel;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage gameStage;
    private Window settingsWindow;
    private Sprite tank1Sprite, tank2Sprite;
    private ImageButton pauseIcon;
    private ImageTextButton fireButton;
    private PauseMenu pauseMenu;
    private ButtonGenerator buttongen;
    private BitmapFont font;
    private Joystick joystickgen;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float accumulator = 0;
    private Tank tank1Obj, tank2Obj;
    private Body tank1;
    private Body tank2;
    private Ground groundObj;
    private ArrayList<Body> groundCols;
    private ArrayList<Float> groundHeights;
    private ArrayList<Float> groundPos;

    private State state = State.RUN;
    private boolean isPaused = false;
    private boolean playerTurn = false; // false = player 1, true = player 2
    // Player 1 is on the left, player 2 is on the right

    public GameScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        gameStage = new Stage(viewport, batch);
        buttongen = new ButtonGenerator();

//        Texture tank1Texture = new Texture(Gdx.files.internal("TankTexture1.png"));
//        tank1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        tank1Sprite = new Sprite(tank1Texture);
//        tank1Sprite.setScale(0.4f);
//        tank1Sprite.flip(true, false);
//        Texture tank2Texture = new Texture(Gdx.files.internal("TankTexture2.png"));
//        tank2Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        tank2Sprite = new Sprite(tank2Texture);
//        tank2Sprite.setScale(0.4f);

        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);

        debugRenderer = new Box2DDebugRenderer();

        groundHeights = new ArrayList<Float>();
        groundPos = new ArrayList<Float>();
        for(float i = -1; i <= 1; i += 0.005){
            groundPos.add(i);
            groundHeights.add(0.3f);
//            groundHeights.add((float)0.3 + (float)0.05 * (float)Math.sin(i * 10));
        }
        groundObj = new Ground(world, groundPos, groundHeights);
        groundCols = groundObj.getGroundCols();

//      Pass an argument to define the tank being used
        tank1Obj = new Tank(world, 0.45f, 0.1f);
        tank2Obj = new Tank(world, -0.45f, 0.1f);
        tank1 = tank1Obj.getTank();
        tank2 = tank2Obj.getTank();
        tank1Sprite = tank1Obj.getSprite();
        tank2Sprite = tank2Obj.getSprite();

//        font = new BitmapFont(Gdx.files.internal("font.fnt"));
//        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        font.getData().setScale(0.5f);
//
//        // creating pause menu
//        pauseMenu = new PauseMenu();
//        pauseMenu.resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                if (isPaused){
//                    System.out.println("Resume button clicked");
//                    gameStage.getActors().removeValue(pauseMenu, true);
//                    isPaused = false;
//                }
//            }
//        });
//
//        buttongen.setNextScreen(pauseMenu.saveButton, new MainScreen(game), game);
//        buttongen.setNextScreen(pauseMenu.exitButton, new MainScreen(game), game);
//
//
//
//        // fire button
//        fireButton = buttongen.createButton("FIRE", String.valueOf(Gdx.files.internal("fire.png")));
//        fireButton.setBounds(Gdx.graphics.getWidth() * 0.75f - fireButton.getWidth() / 2,
//                Gdx.graphics.getHeight() * 0.25f - fireButton.getHeight() / 2,
//                fireButton.getWidth() / 2,
//                fireButton.getHeight() / 2);
//        // adding pause menu icon
//        Texture pauseIconTexture = new Texture(Gdx.files.internal("menuIcon.png"));
//        pauseIconTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        pauseIcon = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseIconTexture)));
//        pauseIcon.setBounds(10, Gdx.graphics.getHeight() - pauseIcon.getHeight()/2 - 10, pauseIcon.getWidth() / 2, pauseIcon.getHeight() / 2);
//        pauseIcon.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                System.out.println("pause");
//                if (!isPaused) {
//                    gameStage.addActor(pauseMenu);
//                    isPaused = true;
//                }
//            }
//        });
//
//        // joystick
//        joystickgen = new Joystick();
//        Touchpad touchpad = joystickgen.getTouchpad();
//        touchpad.setBounds(Gdx.graphics.getWidth() - touchpad.getWidth() - 10, 10, touchpad.getWidth(), touchpad.getHeight());
//        touchpad.setResetOnTouchUp(false);
//
//        // fuel knob
//        Touchpad fuelKnob = joystickgen.getFuelTouchpad();
//        fuelKnob.setBounds(10, 10, 100, 50);
//
//        gameStage.addActor(fuelKnob);
//        gameStage.addActor(fireButton);
//        gameStage.addActor(pauseIcon);
//        gameStage.addActor(touchpad);
    }

    @Override
    public void show() {
        // Textures
//        fuel = new Texture(Gdx.files.internal("fuel.png"));
        joystick = new Texture(Gdx.files.internal("aim.png"));
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        healthBarL = new Texture(Gdx.files.internal("HealthBarL.png"));
        healthBarR = new Texture(Gdx.files.internal("HealthBarR.png"));
        ground = new Texture(Gdx.files.internal("ground.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        healthBarL.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        healthBarR.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(gameStage);
//        pauseIcon.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                System.out.println("pause");
//                gameStage.addActor(pauseMenu);
//            }
//        });

//        settingsWindow = new Window("Settings", gameStage.getSkin());

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

//        switch (state){
//            case RUN:
//                stepWorld();
//                break;
//        }

        // tank movements and controls
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(this.playerTurn) tank1.applyForceToCenter(-0.2f, 0, true);
            else tank2.applyForceToCenter(-0.2f, 0, true);
        }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(this.playerTurn) tank1.applyForceToCenter(0.2f, 0, true);
            else tank2.applyForceToCenter(0.2f, 0, true);
        }

//        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
//            tank1.applyForceToCenter(0, 1, true);
//        }else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//            tank1.applyForceToCenter(0, -1, true);
//        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(this.playerTurn){
                tank2Obj.fire();
            }else{
                tank1Obj.fire();
            }
            this.playerTurn = !this.playerTurn;
        }

        stepWorld();

        batch.begin();

        // background
        batch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //health bars
        batch.draw(healthBarL, TankStars.WIDTH/2f - healthBarL.getWidth() - 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarL.getWidth(), healthBarL.getHeight());
        batch.draw(healthBarR, TankStars.WIDTH/2f + 50, TankStars.HEIGHT - 10 - healthBarR.getHeight(), healthBarR.getWidth(), healthBarR.getHeight());

        // Ground
        renderGround();

        // tanks
        tank1Sprite.setPosition(Gdx.graphics.getWidth()/2 + tank1.getPosition().x*(Gdx.graphics.getWidth()/2) - 55, Gdx.graphics.getHeight()/2 + tank1.getPosition().y*(Gdx.graphics.getHeight()/2) - 35);
//        tank1Sprite.setRotation((float)Math.toDegrees(tank1.getAngle()));
        tank1Sprite.draw(batch);

        tank2Sprite.setPosition(Gdx.graphics.getWidth()/2 + tank2.getPosition().x*(Gdx.graphics.getWidth()/2) - 55, Gdx.graphics.getHeight()/2 + tank2.getPosition().y*(Gdx.graphics.getHeight()/2) - 35);
//        tank2Sprite.setRotation((float)Math.toDegrees(tank2.getAngle()));
        tank2Sprite.draw(batch);

//        batch.draw(fuel, 100, 30, fuel.getWidth()/3f, fuel.getHeight()/3f);

//        font.draw(batch, "FUEL", 100 + fuel.getWidth() / 3f, 30 + fuel.getHeight() / 3f);
        batch.end();

//        gameStage.draw();
//        gameStage.act();
//        Comment or uncomment this line to see the polygons
        debugRenderer.render(world, camera.combined);

    }

    private void renderGround(){
        for(int i = 0; i < groundCols.size(); i++){
            Body groundCol = groundCols.get(i);
            Vector2 pos = groundCol.getPosition();
            float width = 1.7f;
//            float height = groundCol.getFixtureList().get(0).getShape().ge
            float height = groundHeights.get(i)*312;
            batch.draw(ground, Gdx.graphics.getWidth()/2 + pos.x*(Gdx.graphics.getWidth()/2), 0, width*2, height*2);
        }
    }

    private void stepWorld(){
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
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
        gameStage.dispose();
    }
}
