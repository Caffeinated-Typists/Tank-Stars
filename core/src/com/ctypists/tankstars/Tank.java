package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ctypists.tankstars.physicseditor.PhysicsShapeCache;
import java.util.ArrayList;

public class  Tank{

    private final Body tank;
    private final World world;
    private final Sprite tankSprite;
    private ArrayList<Projectile> projectiles;
    private Integer health;

    public Tank(World world, float x, float y, String SpritePath, boolean playerSide){
        this.world = world;

        BodyDef tankDef = new BodyDef();
        tankDef.type = BodyDef.BodyType.DynamicBody;
        tankDef.position.set(x, y);
        tank = world.createBody(tankDef);

        PolygonShape tankBoxShape = new PolygonShape();
        tankBoxShape.setAsBox((float)0.06, (float)0.04);
        FixtureDef tankBoxFixture = new FixtureDef();
        tankBoxFixture.shape = tankBoxShape;
        tankBoxFixture.density = 10;
        tankBoxFixture.friction = 0.1f;
        tankBoxFixture.restitution = 0f;
        tank.createFixture(tankBoxFixture).setUserData(this);
        tankBoxShape.dispose();

        CircleShape tankLeftWheelShape = new CircleShape();
        tankLeftWheelShape.setRadius((float)0.03);
        tankLeftWheelShape.setPosition(new Vector2((float)0.05, (float)-0.03));
        FixtureDef tankLeftWheelFixture = new FixtureDef();
        tankLeftWheelFixture.shape = tankLeftWheelShape;
        tankLeftWheelFixture.density = 10;
        tankLeftWheelFixture.friction = 0.1f;
        tankLeftWheelFixture.restitution = 0f;
        tank.createFixture(tankLeftWheelFixture).setUserData(this);
        tankLeftWheelShape.dispose();

        CircleShape tankRightWheelShape = new CircleShape();
        tankRightWheelShape.setRadius((float)0.03);
        tankRightWheelShape.setPosition(new Vector2((float)-0.05, (float)-0.03));
        FixtureDef tankRightWheelFixture = new FixtureDef();
        tankRightWheelFixture.shape = tankRightWheelShape;
        tankRightWheelFixture.density = 10;
        tankRightWheelFixture.friction = 0.1f;
        tankRightWheelFixture.restitution = 0f;
        tank.createFixture(tankRightWheelFixture).setUserData(this);
        tankRightWheelShape.dispose();

        Texture tankTexture = new Texture(Gdx.files.internal(SpritePath));
        tankTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tankSprite = new Sprite(tankTexture);
        tankSprite.setSize(0.11f, 0.14f);
        tankSprite.setOriginCenter();
        if(playerSide){
            tankSprite.flip(true, false);
        }
        tank.setUserData(this);

    }

    public Body getTank(){
        return this.tank;
    }

    public Sprite getSprite(){
        return this.tankSprite;
    }

//    Modify function to accept a projectile object or name
    public void fire(){

    }

    public void takeDamage(Integer damage){
        this.health -= damage;
    }

}
