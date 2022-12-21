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
    private ArrayList<Body> groundCols;
    private Sprite groundSprite;

    private Ground(World world) {
        this.world = world;

        this.groundCols = new ArrayList<Body>();
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
        this.groundCols = new ArrayList<Body>();
        for(int i = 0; i < groundHeights.size(); i++){
            BodyDef groundColDef = new BodyDef();
            groundColDef.type = BodyDef.BodyType.StaticBody;
            groundColDef.position.set(groundPos.get(i), groundHeights.get(i) - 1);
            Body groundCol = world.createBody(groundColDef);
            FixtureDef groundColFixture = new FixtureDef();
            groundColFixture.friction = 1f;
            PolygonShape groundColShape = new PolygonShape();
            groundColShape.setAsBox((float)0.005, groundHeights.get(i));
            groundColFixture.shape = groundColShape;
            groundCol.createFixture(groundColFixture);

            Texture groundTexture = new Texture(Gdx.files.internal("ground.png"));
            groundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            groundSprite = new Sprite(groundTexture);
            groundSprite.setSize(0.005f, groundHeights.get(i)*2f);
//                groundSprite.setOrigin(groundSprite.getWidth() / 2, groundSprite.getHeight() / 2);
            groundSprite.setOriginCenter();
            groundCol.setUserData(groundSprite);


            this.groundCols.add(groundCol);
            groundColShape.dispose();
        }
    }

    public ArrayList<Float> getGroundPos(){
        return this.groundPos;
    }

    public ArrayList<Float> getGroundHeights(){
        return this.groundHeights;
    }

    public ArrayList<Body> getGroundCols() {
        return groundCols;
    }

}
