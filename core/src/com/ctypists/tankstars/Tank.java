package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.ctypists.tankstars.physicseditor.PhysicsShapeCache;

import java.io.Serializable;
import java.util.ArrayList;

public class  Tank implements Serializable {

    private transient Body tank;
    private transient World world;
    private transient Sprite tankSprite;

    private ArrayList<Projectile> projectiles;

    public Tank(World world, float x, float y, String SpritePath){
        this.world = world;

        PhysicsShapeCache tankShapeCache = new PhysicsShapeCache("tank_hitbox.xml");
        tank = tankShapeCache.createBody("TankTexture2", this.world, 0.0006f, 0.0012f);
        tank.setTransform(x, y, 0);

        Texture tankTexture = new Texture(Gdx.files.internal(SpritePath));
        tankTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tankSprite = new Sprite(tankTexture);
        tankSprite.setSize(0.11f, 0.14f);
        tankSprite.setOrigin(tankSprite.getWidth()/2, tankSprite.getHeight()/2);
//        tankSprite.setOriginCenter();
        tank.setUserData(tankSprite);

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

}
