package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;

public class Projectile {

    private Body projectile;
    private final World world;
    private final Integer damage;

    public Projectile(World world, float x, float y, Integer damage){
        this.world = world;
        this.damage = damage;
    }

    public Body getProjectile(){
        return this.projectile;
    }

    public Integer getDamage(){
        return this.damage;
    }

}
