package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;

    Game game;
    SpriteBatch batch;
    Texture gamebackground;
    OrthographicCamera camera;
    ExtendViewport viewport;
    Stage gameStage;
    Window settingsWindow;
    Texture healthBarL;
    Texture healthBarR;
    Sprite tank1Sprite;
    Sprite tank2Sprite;

    World world;
    Box2DDebugRenderer debugRenderer;
    float accumulator = 0;
    Body tank1;
    Body tank2;

    public GameScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        Texture tank1Texture = new Texture(Gdx.files.internal("TankTexture1.png"));
        tank1Sprite = new Sprite(tank1Texture);
        tank1Sprite.setScale(0.4f);
        tank1Sprite.flip(true, false);
        Texture tank2Texture = new Texture(Gdx.files.internal("TankTexture2.png"));
        tank2Sprite = new Sprite(tank2Texture);
        tank2Sprite.setScale(0.4f);

        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);

        debugRenderer = new Box2DDebugRenderer();

        for(float i = -1; i <= 1; i += 0.01){
            createGroundColumn(i, (float)0.3 + (float)0.05 * (float)Math.sin(i * 10));
//            createGroundColumn(i, (float)0.3);
        }

        BodyDef tank1Def = new BodyDef();
        tank1Def.type = BodyDef.BodyType.DynamicBody;
        tank1Def.position.set((float)0.35, (float)0.1);
        tank1 = world.createBody(tank1Def);
        PolygonShape tank1Shape = new PolygonShape();
        tank1Shape.setAsBox((float)0.02, (float)0.04);
        FixtureDef tank1Fixture = new FixtureDef();
        tank1Fixture.shape = tank1Shape;
        tank1Fixture.density = 1;
        tank1Fixture.friction = 1f;
        tank1Fixture.restitution = -1f;
        tank1.createFixture(tank1Fixture);
        tank1Shape.dispose();

        BodyDef tank2Def = new BodyDef();
        tank2Def.type = BodyDef.BodyType.DynamicBody;
        tank2Def.position.set((float)-0.35, (float)0.1);
        tank2 = world.createBody(tank2Def);
        PolygonShape tank2Shape = new PolygonShape();
        tank2Shape.setAsBox((float)0.02, (float)0.04);
        FixtureDef tank2Fixture = new FixtureDef();
        tank2Fixture.shape = tank1Shape;
        tank2Fixture.density = 1;
        tank2Fixture.friction = 1f;
        tank2Fixture.restitution = -1f;
        tank2.createFixture(tank2Fixture);
        tank2Shape.dispose();
    }

    private void createGroundColumn(float x, float y){
        BodyDef groundColDef = new BodyDef();
        groundColDef.type = BodyDef.BodyType.StaticBody;
        groundColDef.position.set(x, y - 1);
        Body groundCol = world.createBody(groundColDef);
        FixtureDef groundColFixture = new FixtureDef();
        groundColFixture.friction = 1f;
        PolygonShape groundColShape = new PolygonShape();
        groundColShape.setAsBox((float)0.005, (float)y);
        groundColFixture.shape = groundColShape;
        groundCol.createFixture(groundColFixture);
        groundColShape.dispose();
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

        stepWorld();

        batch.begin();

        // background
        batch.draw(gamebackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //health bars
        batch.draw(healthBarL, TankStars.WIDTH/2f - healthBarL.getWidth() - 50, TankStars.HEIGHT - 10 - healthBarL.getHeight(), healthBarL.getWidth(), healthBarL.getHeight());
        batch.draw(healthBarR, TankStars.WIDTH/2f + 50, TankStars.HEIGHT - 10 - healthBarR.getHeight(), healthBarR.getWidth(), healthBarR.getHeight());

        // Ground


        // tanks
        tank1Sprite.setPosition(Gdx.graphics.getWidth()/2 + tank1.getPosition().x*(Gdx.graphics.getWidth()/2) - 90, Gdx.graphics.getHeight()/2 + tank1.getPosition().y*(Gdx.graphics.getHeight()/2) - 45);
        tank1Sprite.setRotation((float)Math.toDegrees(tank1.getAngle()));
        tank1Sprite.draw(batch);

        tank2Sprite.setPosition(Gdx.graphics.getWidth()/2 + tank2.getPosition().x*(Gdx.graphics.getWidth()/2) - 90, Gdx.graphics.getHeight()/2 + tank2.getPosition().y*(Gdx.graphics.getHeight()/2) - 50);
        tank2Sprite.setRotation((float)Math.toDegrees(tank2.getAngle()));
        tank2Sprite.draw(batch);

        batch.end();

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

    }
}
