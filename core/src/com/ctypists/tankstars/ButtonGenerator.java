package com.ctypists.tankstars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonGenerator {
    private Texture image;
    private Drawable imageDrawable;
    private ImageTextButtonStyle mystyle;
    private BitmapFont font;
    private Skin skin;

    public ImageTextButton createButton(String text, String texture){
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        image = new Texture(Gdx.files.internal(texture));
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        imageDrawable = new TextureRegionDrawable(new TextureRegion(image));
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mystyle = new ImageTextButtonStyle();
        mystyle.font = font;
        mystyle.up = imageDrawable;
        return new ImageTextButton(text, mystyle);
    }

    public ImageTextButton createButton(String text) {
        return createButton(text, "box.png");

    }

    public void setScalableButton(ImageTextButton button, float scale) {
        button.getLabel().setFontScale(scale);
    }

    public void setNextScreen(ImageTextButton button, final Screen screen, final Game game){
        button.addListener(new ClickListener(){
        @Override
        public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            game.setScreen(screen);
            }
        });
    }

    public void setNextScreen(ImageButton button, final Screen screen, final Game game){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(screen);
            }
        });
    }
}


