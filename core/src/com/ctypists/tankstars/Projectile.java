package com.ctypists.tankstars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Projectile{
    private Body projectile;
    private final World world;
    private final Sprite projectileSprite;
    private final Integer damage;

    public Projectile(World world, float x, float y, String spritePath, Integer damage){
        this.world = world;
        this.damage = damage;

        BodyDef projDef = new BodyDef();
        projDef.type = BodyDef.BodyType.DynamicBody;
        projDef.position.set(x, y);
        projectile = world.createBody(projDef);

        PolygonShape projectileShape = new PolygonShape();
        projectileShape.setAsBox((float)0.02, (float)0.01);
        FixtureDef projectileFixture = new FixtureDef();
        projectileFixture.shape = projectileShape;
        projectileFixture.density = 10;
        projectileFixture.friction = 0.1f;
        projectileFixture.restitution = 0f;
        projectile.createFixture(projectileFixture).setUserData(this);
        projectileShape.dispose();

        Texture projectileTexture = new Texture(spritePath);
        projectileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.projectileSprite = new Sprite(projectileTexture);
        projectileSprite.setScale(1f);
        projectileSprite.setSize(0.11f, 0.14f);
        projectileSprite.setOriginCenter();
        projectile.setUserData(this);

    }

    public Body getProjectile(){
        return this.projectile;
    }

    public Integer getDamage(){
        return this.damage;
    }

    public Sprite getSprite(){
        return this.projectileSprite;
    }

    public void destroyProjectile(){
        world.destroyBody(this.projectile);
    }

}
