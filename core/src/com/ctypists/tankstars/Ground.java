package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Ground implements Serializable {

    private static Ground ground = null;

    private final World world;
    private final ArrayList<Float> groundPos;
    private ArrayList<Float> groundHeights;
    private ArrayList<GroundCol> groundCols;
    private Sprite groundSprite;

    private Ground(World world) {
        this.world = world;

        this.groundCols = new ArrayList<GroundCol>();
        this.groundPos = new ArrayList<Float>();
        this.groundHeights = new ArrayList<Float>();

        for(float i = -1; i < 1; i += 0.005){
            groundPos.add(i);

            // Rectangular ground
//            if(i <= -0.9){
//                groundHeights.add(1.4f + i);
//            }else if(i <= -0.2){
//                groundHeights.add(0.5f);
//            }else if(i <= 0.1){
//                groundHeights.add(0.5f - 0.65f*(i + 0.2f));
//            }else if(i <= 0.2){
//                groundHeights.add(0.3f);
//            }else if(i <= 0.4){
//                groundHeights.add((i + 0.2f)/2 + 0.1f);
//            }else {
//                groundHeights.add(0.4f);
//            }

            // Straight ground
//            groundHeights.add(0.5f);

            // Sinusoidal ground
            groundHeights.add(0.4f + (float)Math.sin(i*4)*0.05f);
        }

        this.createGround();
    }

    public static Ground getGround(World world){
        if(ground == null){
            ground = new Ground(world);
        }
        return ground;
    }

    private void createGround() {
        this.groundCols = new ArrayList<GroundCol>();
        for(int i = 0; i < groundHeights.size(); i++){
//            BodyDef groundColDef = new BodyDef();
//            groundColDef.type = BodyDef.BodyType.StaticBody;
//            groundColDef.position.set(groundPos.get(i), groundHeights.get(i) - 1);
//            Body groundCol = world.createBody(groundColDef);
//            FixtureDef groundColFixture = new FixtureDef();
//            groundColFixture.friction = 1f;
//            PolygonShape groundColShape = new PolygonShape();
//            groundColShape.setAsBox((float)0.005, groundHeights.get(i));
//            groundColFixture.shape = groundColShape;
//            groundCol.createFixture(groundColFixture).setUserData(this);

//            Texture groundTexture = new Texture(Gdx.files.internal("ground.png"));
//            groundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//            groundSprite = new Sprite(groundTexture);
//            groundSprite.setSize(0.005f, groundHeights.get(i)*2.4f);
//            groundSprite.setOriginCenter();
//            groundCol.setUserData(this);
            GroundCol groundCol = new GroundCol(world, i, groundPos.get(i), groundHeights.get(i), this);
            this.groundCols.add(groundCol);
//            groundColShape.dispose();
        }
    }

    public ArrayList<Float> getGroundPos(){
        return this.groundPos;
    }

    public ArrayList<Float> getGroundHeights(){
        return this.groundHeights;
    }

    public ArrayList<GroundCol> getGroundCols() {
        return groundCols;
    }

    public Sprite getSprite(){
        return this.groundSprite;
    }

    public void takeDamage(Integer damage, Fixture groundCol){
        int index = groundCols.indexOf(groundCol.getBody());
        for(int i = Math.max(index - damage/5, 0); i < Math.min(index + damage/5, groundHeights.size()); i++){
            groundCols.get(i).takeDamage(damage/(Math.abs(i - index) + 1));
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Ground) {
            Ground ground = (Ground) obj;
            return ground.getGroundHeights().equals(this.groundHeights);
        }
        return false;

    }

}

class GroundCol{

    private final Body groundCol;
    private final Sprite groundSprite;
    private final Ground ground;
    private final int index;

    public GroundCol(World world, int i, float x, float y, Ground ground){
        this.ground = ground;
        this.index = i;
        BodyDef groundColDef = new BodyDef();
        groundColDef.type = BodyDef.BodyType.StaticBody;
        groundColDef.position.set(x, y - 1);
        groundCol = world.createBody(groundColDef);
        FixtureDef groundColFixture = new FixtureDef();
        groundColFixture.friction = 1f;
        PolygonShape groundColShape = new PolygonShape();
        groundColShape.setAsBox((float)0.005, y);
        groundColFixture.shape = groundColShape;
        groundCol.createFixture(groundColFixture).setUserData(this);

        Texture groundTexture = new Texture(Gdx.files.internal("ground.png"));
        groundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        groundSprite = new Sprite(groundTexture);
        groundSprite.setSize(0.005f, y*2f);
        groundSprite.setOriginCenter();
        groundCol.setUserData(this);

        groundColShape.dispose();
    }

    public Ground getGround(){
        return this.ground;
    }

    public Body getGroundCol(){
        return this.groundCol;
    }

    public Sprite getSprite(){
        return this.groundSprite;
    }

    public void takeDamage(Integer damage){

    }


}
