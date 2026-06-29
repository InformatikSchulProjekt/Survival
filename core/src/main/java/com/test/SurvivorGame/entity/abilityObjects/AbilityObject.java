package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.test.SurvivorGame.entity.GameObject;
import com.test.SurvivorGame.entity.enemy.Enemy;

public abstract class AbilityObject extends GameObject{

    protected float rotation;

    public AbilityObject(float x, float y, float w, float h, Texture texture) {
        super(x, y, w, h, texture);
    }

    public AbilityObject(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public abstract boolean getExpired();

    public abstract float getDamage(); //damage aus der ability steuerungs klasse

    public abstract void onHit(Enemy enemy);

    @Override
    public void draw(Batch batch)
    {
        if(texture == null) return;

        batch.draw(texture, collider.getX(), collider.getY(), collider.getWidth() / 2, collider.getHeight() / 2, collider.getWidth(), collider.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false
        );
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }
}
