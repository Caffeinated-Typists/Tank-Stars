package com.ctypists.tankstars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Tests {

    @Test
    public void testHealthBar(){
        HealthBar healthBar = new HealthBar();
        healthBar.setHealth(1.2f);
        System.out.println(healthBar.invalidHealth());
        assertTrue(healthBar.invalidHealth());
    }


    public static void main(String[] args) {
        Tests tests = new Tests();
//        tests.testGround();
//        tests.testTank();
        tests.testHealthBar();
    }

}