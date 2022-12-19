package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;
//import com.codeandweb.physicseditor.PhysicsShapeCache;
//import gdx-pe-loader-1.1.0.*;
//import gdx-pe-loader-1.1.0.PhysicsShapeCache;
//import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.ctypists.tankstars.physicseditor.PhysicsShapeCache;

public class  Tank{

    private Body tank;
    private World world;

    public Tank(World world, float x, float y){
        this.world = world;
//        BodyDef tankDef = new BodyDef();
//        tankDef.type = BodyDef.BodyType.DynamicBody;
//        tankDef.position.set(x, y);
//        tank = world.createBody(tankDef);
        PhysicsShapeCache tankShapeCache = new PhysicsShapeCache("tank_hitbox.xml");
        tank = tankShapeCache.createBody("TankTexture2", this.world, 0.0006f, 0.0012f);
        tank.setTransform(x, y, 0);
//        PolygonShape tankShape = new PolygonShape();
//        tankShape.setAsBox((float)0.02, (float)0.04);
//        FixtureDef tankFixture = new FixtureDef();
//        tankFixture.shape = tankShape;
//        tankFixture.density = 1;
//        tankFixture.friction = 1f;
//        tankFixture.restitution = -1f;
//        tank.createFixture(tankFixture);
//        tankShape.dispose();
    }

    public Body getTank(){
        return this.tank;
    }

}
