package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;

public class detectCollisions  implements ContactListener {

    @Override
    public void beginContact(Contact contact){

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() instanceof Projectile){
            if(fixtureB.getUserData() instanceof Tank) {
                Tank tank = (Tank) fixtureB.getUserData();
                Projectile projectile = (Projectile) fixtureA.getUserData();
                tank.takeDamage(projectile.getDamage());
                projectile.destroyProjectile();
            }else if(fixtureB.getUserData() instanceof Ground) {
                Ground ground = (Ground) fixtureB.getUserData();
                Projectile projectile = (Projectile) fixtureA.getUserData();
                ground.takeDamage(projectile.getDamage(), fixtureB);
                projectile.destroyProjectile();
            }
        }else if(fixtureB.getUserData() instanceof Projectile){
            if(fixtureA.getUserData() instanceof Tank) {
                Tank tank = (Tank) fixtureA.getUserData();
                Projectile projectile = (Projectile) fixtureB.getUserData();
                tank.takeDamage(projectile.getDamage());
                projectile.destroyProjectile();
            }else if(fixtureA.getUserData() instanceof Ground) {
                Ground ground = (Ground) fixtureA.getUserData();
                Projectile projectile = (Projectile) fixtureB.getUserData();
                ground.takeDamage(projectile.getDamage(), fixtureA);
                projectile.destroyProjectile();
            }
        }

    }

    @Override
    public void endContact(Contact contact){
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
