package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class HealthBar extends Actor {
    private NinePatchDrawable loadingBarBackground;
    private int width;
    private int height;
    private NinePatchDrawable loadingBar;
    private Side side;

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    private float health;

    public HealthBar() {
        super();
        width = 295;
        height = 66;
        health = 1;
    }

    public void HealthBarL(){
        NinePatch loadingBarBackgroundPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarLExperiment.png")), 1, 1, 1, 1);
        NinePatch loadingBarPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarLEmpty.png")), 5, 5, 4, 4);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
        side = Side.LEFT;
    }

    public void HealthBarR(){
        NinePatch loadingBarBackgroundPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarLExperiment.png")), 1, 1, 1, 1);
        NinePatch loadingBarPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarREmpty.png")), 5, 5, 4, 4);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
        side = Side.RIGHT;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (side == Side.RIGHT)
            loadingBarBackground.draw(batch, getX() - 20 + (1-health)*  getWidth() * getScaleX() * 0.9322033898f, getY(), health* getWidth() * getScaleX() * 0.9322033898f, getHeight() * getScaleY());
        else
            loadingBarBackground.draw(batch, getX() + 20, getY(), health* getWidth() * getScaleX() * 0.9322033898f, getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(),  getWidth() * getScaleX(), getHeight() * getScaleY());
    }
}
