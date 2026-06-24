package com.test.SurvivorGame.entity.abilityObjects.melee;

import com.badlogic.gdx.graphics.Texture;

import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;

public class Melee extends AbilityObject { //sollte später abstract parent von den Melee sein, grad zum Testen is aber da

    private float deltaDuration;

    private Player player;

    public Melee(float x, float y, float effectSize, float duration, Texture texture, Player player)
    {
        super(x,y,effectSize, effectSize, texture);

        this.deltaDuration = duration;

        this.player = player;
    }

    @Override
    public void update(float deltaTime)
    {
        if(isExpired()) return;

        deltaDuration -= deltaTime;

        move();
    }

    public void move()
    {
        collider.setPosition(player.getCenter().x + 1, player.getCenter().y);
    }

    public boolean isExpired()
    {
        return deltaDuration <= 0;
    }


}
