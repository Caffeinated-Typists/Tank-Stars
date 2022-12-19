package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Ground {

    private final World world;
    private final ArrayList<Float> groundPos;
    private ArrayList<Float> groundHeights;
    private ArrayList<Body> groundCols;

    public Ground(World world) {
        this.world = world;

        this.groundCols = new ArrayList<Body>();
        this.groundPos = new ArrayList<Float>();
        this.groundHeights = new ArrayList<Float>();

        for(float i = -1; i < 1; i += 0.005){
            groundPos.add(i);
            if(i <= -0.9){
                groundHeights.add(1.4f + i);
            }else if(i <= -0.2){
                groundHeights.add(0.5f);
            }else if(i <= 0.1){
                groundHeights.add(0.5f - 0.65f*(i + 0.2f));
            }else if(i <= 0.2){
                groundHeights.add(0.3f);
            }else if(i <= 0.4){
                groundHeights.add((i + 0.2f)/2 + 0.1f);
            }else if(i <= 1){
                groundHeights.add(0.4f);
            }
        }

        this.createGround();
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
