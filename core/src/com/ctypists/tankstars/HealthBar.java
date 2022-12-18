package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class HealthBar extends Actor {
    private NinePatchDrawable loadingBarBackground;

    private NinePatchDrawable loadingBar;

    public HealthBar() {
        NinePatch loadingBarBackgroundPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarLEmpty.png")), 1, 1, 1, 1);
        NinePatch loadingBarPatch = new NinePatch(new Texture(Gdx.files.internal("HealthBarL.png")), 5, 5, 4, 4);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float progress = 0.4f;

        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
    }
}
