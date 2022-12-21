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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private Game game;
    private SpriteBatch batch;
    private Texture gamebackground, ground, joystick, fuel;
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
    private HealthBar healthBarL, healthBarR;

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

    private Array<Body> bodies;

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
//        buttongen = new ButtonGenerator();

        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);

        debugRenderer = new Box2DDebugRenderer();

        groundObj = new Ground(world);
//        groundPos = groundObj.getGroundPos();
//        groundHeights = groundObj.getGroundHeights();
//        groundCols = groundObj.getGroundCols();

//      Pass an argument to define the tank being used
        tank1Obj = new Tank(world, 0.45f, 0.1f, "TankTexture2.png");
        tank2Obj = new Tank(world, -0.45f, 0.1f, "TankTexture2.png");
        tank1 = tank1Obj.getTank();
        tank2 = tank2Obj.getTank();
        tank1Sprite = tank1Obj.getSprite();
        tank2Sprite = tank2Obj.getSprite();

        bodies = new Array<Body>();

//        font = new BitmapFont(Gdx.files.internal("font.fnt"));
//        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        font.getData().setScale(0.5f);

        // creating pause menu
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

//        buttongen.setNextScreen(pauseMenu.saveButton, new MainScreen(game), game);
//        buttongen.setNextScreen(pauseMenu.exitButton, new MainScreen(game), game);



        // fire button
//        fireButton = buttongen.createButton("FIRE", String.valueOf(Gdx.files.internal("fire.png")));
//        fireButton.setBounds(Gdx.graphics.getWidth() * 0.75f - fireButton.getWidth() / 2,
//                Gdx.graphics.getHeight() * 0.25f - fireButton.getHeight() / 2,
//                fireButton.getWidth() / 2,
//                fireButton.getHeight() / 2);

        // adding pause menu icon
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

        // joystick
//        joystickgen = new Joystick();
//        Touchpad touchpad = joystickgen.getTouchpad();
//        touchpad.setBounds(Gdx.graphics.getWidth() - touchpad.getWidth() - 10, 10, touchpad.getWidth(), touchpad.getHeight());
//        touchpad.setResetOnTouchUp(false);

        // fuel knob
//        Touchpad fuelKnob = joystickgen.getFuelTouchpad();
//        fuelKnob.setBounds(10, 10, 100, 50);

        // health bar experiment
//        HealthBar healthBarL = new HealthBar(), healthBarR = new HealthBar();
//        healthBarL.HealthBarL();
//        healthBarR.HealthBarR();

//        healthBarR.setBounds(TankStars.WIDTH/2f + 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarR.getWidth(), healthBarR.getHeight());
//        healthBarL.setBounds(TankStars.WIDTH/2f - healthBarL.getWidth() - 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarL.getWidth(), healthBarL.getHeight());

//        healthBarL.setHealth(0.5f);
//        healthBarR.setHealth(0.2f);

//        gameStage.addActor(fuelKnob);
//        gameStage.addActor(fireButton);
//        gameStage.addActor(pauseIcon);
//        gameStage.addActor(touchpad);
//        gameStage.addActor(healthBarR);
//        gameStage.addActor(healthBarL);

    }

    @Override
    public void show() {
        // Textures
//        fuel = new Texture(Gdx.files.internal("fuel.png"));
//        joystick = new Texture(Gdx.files.internal("aim.png"));
//        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
//        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        Gdx.input.setInputProcessor(gameStage);
//        pauseIcon.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                System.out.println("pause");
//                gameStage.addActor(pauseMenu);
//            }
//        });


    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
//                GL20.GL_DEPTH_BUFFER_BIT |
//                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
//        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

//        switch (state){
//            case RUN:
//                stepWorld();
//                break;
//        }

//        // tank movements and controls
//        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
////            if(this.playerTurn) tank1.applyForceToCenter(-1f, 0, true);
////            else tank2.applyForceToCenter(-1f, 0, true);
//            if(this.playerTurn) tank1.setLinearVelocity(-0.5f, 0);
//            else tank2.setLinearVelocity(-0.5f, 0);
//        }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
////            if(this.playerTurn) tank1.applyForceToCenter(1f, 0, true);
////            else tank2.applyForceToCenter(1f, 0, true);
//            if(this.playerTurn) tank1.setLinearVelocity(0.5f, 0);
//            else tank2.setLinearVelocity(0.5f, 0);
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.I)){
////            System.out.println("Zoom kar");
//            camera.zoom += 0.02;
//        }else if(Gdx.input.isKeyPressed(Input.Keys.O)){
////            System.out.println("Zoom kar");
//            camera.zoom -= 0.02;
//        }
//
////        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
////            tank1.applyForceToCenter(0, 1, true);
////        }else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
////            tank1.applyForceToCenter(0, -1, true);
////        }
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
//            if(this.playerTurn){
//                tank2Obj.fire();
//            }else{
//                tank1Obj.fire();
//            }
//            this.playerTurn = !this.playerTurn;
//        }

        stepWorld();

        camera.position.set(tank1.getPosition().x, tank1.getPosition().y, 0);
//        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // background
//        batch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world.getBodies(bodies);
        for (Body body : bodies) {
            if ((body.getUserData() != null) && (body.getUserData() instanceof Sprite)) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation((float) Math.toDegrees(body.getAngle()));
                sprite.draw(batch);
            }
        }

//        batch.draw(fuel, 100, 30, fuel.getWidth()/3f, fuel.getHeight()/3f);

//        font.draw(batch, "FUEL", 100 + fuel.getWidth() / 3f, 30 + fuel.getHeight() / 3f);
        batch.end();

//        gameStage.draw();
//        gameStage.act();
//        Comment or uncomment this line to see the polygons
//        debugRenderer.render(world, camera.combined);

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
