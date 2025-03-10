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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import sun.tools.jconsole.inspector.XObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GameScreen implements Screen {
    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private TankStars game;
    private SpriteBatch batch;
    private Texture gamebackground, ground, joystick, fuel, healthbarl, healthbarr;
    private TextureRegion fuelTexture;
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
    private float fuel_level = 0.7f;

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
    private float scalingX = 0.0014880952f;
    private float scalingY = 0.0032206119f;
    private float scaleX = 672;
    private float scaleY = 310.5f;
    private Array<Body> bodies;
    private int p1_tank, p2_tank;

    private HashMap<Integer, String> tankMapping;
    private State state = State.RUN;
    private boolean isPaused = false;
    private boolean playerTurn = false; // false = player 1, true = player 2
    // Player 1 is on the left, player 2 is on the right

    public GameScreen(TankStars game, int p1_tank, int p2_tank) {
        this.game = game;
        this.p1_tank = p1_tank;
        this.p2_tank = p2_tank;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/672f, Gdx.graphics.getHeight()/310.5f);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        gameStage = new Stage();
        buttongen = new ButtonGenerator();

        tankMapping = new HashMap<Integer, String>();
        tankMapping.put(0, "Tank1");
        tankMapping.put(1, "Tank2");
        tankMapping.put(2, "Tank3");

        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new detectCollisions());

        groundObj = Ground.getGround(world);
//        groundPos = groundObj.getGroundPos();
//        groundHeights = groundObj.getGroundHeights();
//        groundCols = groundObj.getGroundCols();

//      Pass an argument to define the tank being used
        TankFactory tankFactory = new TankFactory(world);
        System.out.println("Player 1 tank: " + p1_tank);
        System.out.println("Player 2 tank: " + p2_tank);
        tank1Obj = tankFactory.createTank(tankMapping.get(p1_tank), 0.45f, 0.1f, true);
        tank2Obj = tankFactory.createTank(tankMapping.get(p2_tank), -0.45f, 0.1f, false);
        tank1 = tank1Obj.getTank();
        tank2 = tank2Obj.getTank();
        tank1Sprite = tank1Obj.getSprite();
        tank2Sprite = tank2Obj.getSprite();

        bodies = new Array<Body>();




        // fire button
        fireButton = buttongen.createButton("FIRE", String.valueOf(Gdx.files.internal("fire.png")));
        fireButton.setBounds(((Gdx.graphics.getWidth() * 0.7f - fireButton.getWidth() / 2) / scaleX) - 1 ,
                ((Gdx.graphics.getHeight() * 0.3f - fireButton.getHeight() / 2) / scaleY) - 1,
                (fireButton.getWidth() / scaleX) / 1.75f,
                (fireButton.getHeight() / scaleY)/1.75f);
//        fireButton.getData().setScale(0.5f);
        buttongen.setScalableButton(fireButton, 0.03f);

        // adding pause menu icon
        Texture pauseIconTexture = new Texture(Gdx.files.internal("menuIcon.png"));
        pauseIconTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseIcon = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseIconTexture)));
        pauseIcon.setBounds((10) / scaleX - 1f, (Gdx.graphics.getHeight() - pauseIcon.getHeight()/2 - 10) / scaleY - 1 , pauseIcon.getWidth() / 2, pauseIcon.getHeight() / 2);
        pauseIcon.setTransform(true);
        pauseIcon.setScale(scalingX, scalingY);

        buttongen.setNextScreen(pauseIcon, new PauseMenuAlt(game, this), game);

        // joystick
        Touchpad touchpad = Joystick.getTouchpad();
        touchpad.setBounds((Gdx.graphics.getWidth() - touchpad.getWidth() - 20) / scaleX - 1, 20 / scaleY - 1, touchpad.getWidth() * scaleX, touchpad.getHeight() * scaleY);
        touchpad.setResetOnTouchUp(true);

        // health bar experiment
        HealthBar healthBarL = new HealthBar(), healthBarR = new HealthBar();
        healthBarL.HealthBarL();
        healthBarR.HealthBarR();

        healthBarR.setBounds(TankStars.WIDTH/2f + 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarR.getWidth(), healthBarR.getHeight());
        healthBarL.setBounds((TankStars.WIDTH/2f - healthBarL.getWidth() - 50) * scalingX - 1, (TankStars.HEIGHT - 10 - healthBarL.getHeight()) * scalingY - 1, healthBarL.getWidth() /scaleX, healthBarL.getHeight() / scaleY);
//        healthBarL.set
//        healthBarL.setScale(scalingX * scalingX, scalingY * scalingY);
        healthBarL.setHealth(0.5f);
        healthBarR.setHealth(0.2f);

//        gameStage.addActor(fireButton);
//        gameStage.addActor(pauseIcon);
//        gameStage.addActor(touchpad);
//        gameStage.addActor(healthBarR);
//        gameStage.addActor(healthBarL);

    }

    @Override
    public void show() {
        // Textures
        fuel = new Texture(Gdx.files.internal("fuel.png"));
        fuelTexture = new TextureRegion(fuel, 0, 0, fuel.getWidth()  / 2, fuel.getHeight());
        joystick = new Texture(Gdx.files.internal("aim.png"));
        gamebackground = new Texture(Gdx.files.internal("gameBackground.png"));
        ground = new Texture(Gdx.files.internal("ground.png"));
        healthbarl = new Texture(Gdx.files.internal("HealthBarL.png"));
        healthbarr = new Texture(Gdx.files.internal("HealthBarR.png"));
        gamebackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        Gdx.input.setInputProcessor(gameStage);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

//        switch (state){
//            case RUN:
//                stepWorld();
//                break;
//        }

//        // tank movements and controls
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//            if(this.playerTurn) tank1.applyForceToCenter(-1f, 0, true);
//            else tank2.applyForceToCenter(-1f, 0, true);
            if(this.playerTurn) tank1.setLinearVelocity(-0.5f, 0);
            else tank2.setLinearVelocity(-0.5f, 0);
        }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//            if(this.playerTurn) tank1.applyForceToCenter(1f, 0, true);
//            else tank2.applyForceToCenter(1f, 0, true);
            if(this.playerTurn) tank1.setLinearVelocity(0.5f, 0);
            else tank2.setLinearVelocity(0.5f, 0);
        }
//
        if(Gdx.input.isKeyPressed(Input.Keys.I)){
            camera.zoom -= 0.02;
        }else if(Gdx.input.isKeyPressed(Input.Keys.O)){
            camera.zoom += 0.02;
        }

//
////        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
////            tank1.applyForceToCenter(0, 1, true);
////        }else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
////            tank1.applyForceToCenter(0, -1, true);
////        }
//
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            save_game();
            game.setScreen(new PauseMenuAlt(game, this));
        }



        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(this.playerTurn){
                tank2Obj.fire(50, 60);
            }else{
                tank1Obj.fire(50, 60);
            }
            this.playerTurn = !this.playerTurn;
        }

        stepWorld();

//        camera.position.set(0, 0, 0);
        camera.update();
//        System.out.println(camera.position);

        batch.setProjectionMatrix(camera.combined);
        gameStage.setViewport(viewport);
        batch.begin();

        // background
        batch.draw(gamebackground, -1, -1, Gdx.graphics.getWidth()*scalingX, Gdx.graphics.getHeight()*scalingY);

        batch.draw(healthbarl, -0.6f, 0.7f, healthbarl.getWidth() * scalingX, healthbarl.getHeight() * scalingY);
        batch.draw(healthbarr, 0.2f, 0.7f, healthbarr.getWidth() * scalingX, healthbarr.getHeight() * scalingY);
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null) {
                Sprite sprite = null;
                if(body.getUserData() instanceof Tank){
                    Tank tank = (Tank) body.getUserData();
                    sprite = tank.getSprite();
                }else if(body.getUserData() instanceof Projectile){
                    Projectile projectile = (Projectile) body.getUserData();
                    sprite = projectile.getSprite();
                }else if(body.getUserData() instanceof GroundCol){
                    GroundCol groundCol = (GroundCol) body.getUserData();
                    sprite = groundCol.getSprite();
                }else{
                    System.out.println(body.getUserData().getClass());
                    continue;
                }
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation((float) Math.toDegrees(body.getAngle()));
                sprite.draw(batch);
            }
        }

//        font.draw(batch, "FUEL", 100 + fuel.getWidth() / 3f, 30 + fuel.getHeight() / 3f);
        fuelTexture.setRegionWidth((int) (fuel.getWidth() * fuel_level));
//        batch.draw(fuelTexture, 0, 0);
        batch.end();

        gameStage.draw();
        gameStage.act();
//        Comment or uncomment this line to see the polygons
//        debugRenderer.render(world, camera.combined);
//        save_game();
    }

    private void stepWorld(){
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    public void save_game(){
        // serializes the game state
//        if (!game.save_state)
//            return;

        try {

            // getting current datetime
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String datetime = dateFormat.format(date);

            System.out.println("Saving game state...");
            FileOutputStream fileOut = new FileOutputStream( datetime + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            SaveGameObj saveGameObj = new SaveGameObj(tankMapping.get(p1_tank), tankMapping.get(p2_tank), tank1Obj.getHealth(), tank2Obj.getHealth());

            out.writeObject(saveGameObj);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in game_state.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

//        game.save_state = false;
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
