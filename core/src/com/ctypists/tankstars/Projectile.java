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

    public Projectile(World world, float x, float y, Integer damage){
        this.world = world;
        this.damage = damage;

        Texture projectileTexture = new Texture("ProjectileTexture.png");
        projectileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.projectileSprite = new Sprite(projectileTexture);
        projectileSprite.setScale(1f);

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
//        this.projectile.setActive(false);
    }

}
