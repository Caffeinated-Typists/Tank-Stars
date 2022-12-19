package com.ctypists.tankstars;

import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Ground implements Serializable {

    private transient World world;
    private ArrayList<Float> groundPos;
    private ArrayList<Float> groundHeights;
    private transient ArrayList<Body> groundCols;

    public Ground(World world, ArrayList<Float> groundPos, ArrayList<Float> groundHeights) {
        this.world = world;
        this.groundPos = groundPos;
        this.groundHeights = groundHeights;
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

    public ArrayList<Body> getGroundCols() {
        return groundCols;
    }

}
