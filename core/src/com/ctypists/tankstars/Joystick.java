package com.ctypists.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Joystick {
    private static Touchpad.TouchpadStyle touchpadStyle;
    private Touchpad.TouchpadStyle fuelTouchpadStyle;
    private static Touchpad touchpad;
    private Touchpad fuel_touchpad;

    private Joystick() {
        touchpadStyle = new Touchpad.TouchpadStyle();
        Texture touchpadBackground = new Texture(Gdx.files.internal("joystick_bg.png"));
        Texture touchpadKnob = new Texture(Gdx.files.internal("joystick_knob.png"));
        touchpadBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        touchpadKnob.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);



        TextureRegion touchpadBackgroundRegion = new TextureRegion(touchpadBackground);
        TextureRegion touchpadKnobRegion = new TextureRegion(touchpadKnob);


        touchpadStyle.background = new TextureRegionDrawable(touchpadBackgroundRegion);
        touchpadStyle.knob = new TextureRegionDrawable(touchpadKnobRegion);
        touchpad = new Touchpad(10, touchpadStyle);

        // fuel_knob
        Texture fuel_knob_texture = new Texture("fuel_knob.png");
        fuel_knob_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion fuel_knob_region = new TextureRegion(fuel_knob_texture);

        Touchpad.TouchpadStyle fuelTouchpadStyle = new Touchpad.TouchpadStyle();
        fuelTouchpadStyle.background = new TextureRegionDrawable(touchpadBackgroundRegion);
        fuelTouchpadStyle.knob = new TextureRegionDrawable(fuel_knob_region);

        fuel_touchpad = new Touchpad(15, fuelTouchpadStyle);
    }

    public static Touchpad getTouchpad() {
        if (touchpad == null) {
            Joystick joystick = new Joystick();
            touchpad = joystick.touchpad;
        }

        return touchpad;
    }

    public Touchpad getFuelTouchpad() {
        return fuel_touchpad;
    }
}
