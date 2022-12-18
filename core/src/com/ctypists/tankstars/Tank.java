package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;

public class  Tank{

    private Body tank;
    private World world;

    public Tank(World world, float x, float y){
        this.world = world;
        BodyDef tankDef = new BodyDef();
        tankDef.type = BodyDef.BodyType.DynamicBody;
        tankDef.position.set(x, y);
        tank = world.createBody(tankDef);
        PolygonShape tankShape = new PolygonShape();
        tankShape.setAsBox((float)0.02, (float)0.04);
        FixtureDef tank2Fixture = new FixtureDef();
        tank2Fixture.shape = tankShape;
        tank2Fixture.density = 1;
        tank2Fixture.friction = 1f;
        tank2Fixture.restitution = -1f;
        tank.createFixture(tank2Fixture);
        tankShape.dispose();
    }

    public Body getTank(){
        return this.tank;
    }

}
