package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class TankFactory {

    private final World world;
//    Might need to add stuff like scaling in the HashMap too later on, skipping for now
    private final HashMap<String, String> tankTexturesPath;

    public TankFactory(World world) {
        this.world = world;
        this.tankTexturesPath = new HashMap<String, String>();
        this.tankTexturesPath.put("Tank1", "TankTexture1.png");
        this.tankTexturesPath.put("Tank2", "TankTexture2.png");
        this.tankTexturesPath.put("Tank3", "TankTexture3.png");
    }

    public Tank createTank(String tankName, float x, float y, boolean playerSide) {
        return new Tank(world, x, y, tankTexturesPath.get(tankName), playerSide);
    }

}
