package com.ctypists.tankstars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Joystick {
    Touchpad.TouchpadStyle touchpadStyle;
    Touchpad.TouchpadStyle fuelTouchpadStyle;
    Touchpad touchpad;
    Touchpad fuel_touchpad;

    public Joystick() {
        touchpadStyle = new Touchpad.TouchpadStyle();
        Texture touchpadBackground = new Texture("joystick_bg.png");
        Texture touchpadKnob = new Texture("joystick_knob.png");
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

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public Touchpad getFuelTouchpad() {
        return fuel_touchpad;
    }
}
