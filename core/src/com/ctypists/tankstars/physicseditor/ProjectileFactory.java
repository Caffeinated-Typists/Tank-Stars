package com.ctypists.tankstars.physicseditor;

import com.badlogic.gdx.physics.box2d.World;
import com.ctypists.tankstars.Projectile;

import java.util.HashMap;

public class ProjectileFactory {

    private final World world;
    private final HashMap<String, String> projectileTexturePath;

    public ProjectileFactory(World world) {
        this.world = world;
        this.projectileTexturePath = new HashMap<String, String>();
    }

    public Projectile createProjectile(String projectileName, float x, float y, Integer damage) {
        return new Projectile(world, x, y, damage);
    }


}
