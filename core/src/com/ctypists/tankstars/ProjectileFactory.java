package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class ProjectileFactory {

    private final World world;
    private final HashMap<String, String> projectileTexturesPath;
    private final HashMap<String, Integer> projectileDamage;

    public ProjectileFactory(World world) {
        this.world = world;
        this.projectileTexturesPath = new HashMap<String, String>();
        this.projectileDamage = new HashMap<String, Integer>();
        this.projectileTexturesPath.put("Projectile1", "ProjectileTexture1.png");
        this.projectileTexturesPath.put("Projectile2", "ProjectileTexture2.png");
        this.projectileDamage.put("Projectile1", 20);
        this.projectileDamage.put("Projectile2", 30);
    }

    public Projectile createProjectile(String projectileName, float x, float y) {
        return new Projectile(world, x, y, projectileTexturesPath.get(projectileName), projectileDamage.get(projectileName));
    }

}
