package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.entity.GameObject;

public abstract class AbilityObject extends GameObject{

    public AbilityObject(float x, float y, float w, float h, Texture texture) {
        super(x, y, w, h, texture);
    }

    public AbilityObject(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public abstract boolean isExpired();
}
