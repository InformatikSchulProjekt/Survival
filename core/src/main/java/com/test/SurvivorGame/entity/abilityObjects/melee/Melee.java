package com.test.SurvivorGame.entity.abilityObjects.melee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.activeAbility.MeleeAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class Melee extends AbilityObject { //sollte später abstract parent von den Melee sein, grad zum Testen is aber da

    private Viewport viewport;

    private float deltaDuration;
    private float damageTimer = 0;

    private Player player;

    public Melee(float x, float y, float effectSize, float duration, Texture texture, Player player, Viewport viewport)
    {
        super(x,y,effectSize, effectSize, texture);

        this.deltaDuration = duration;

        this.player = player;

        this.viewport = viewport;
    }



    public void move()
    {
        Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0); // Mauskoordinaten in pixel

        viewport.unproject(mouseScreen); // wandelt pixelkoordinaten in welt-einheits-koordinaten um

        Vector2 direction = new Vector2(mouseScreen.x - player.getCenter().x, mouseScreen.y - player.getCenter().y); // vektoren berechnung wie in der Schule

        if(direction.isZero())
        {
            return;
        }

        direction.nor(); //wenns schräg geht normalisieren

        rotation = direction.angleDeg();

        float radius = 1.5f; // wie weit weg vom center

        collider.setPosition(player.getCenter().x + direction.x * radius - getWidth()/2, player.getCenter().y + direction.y * radius -getHeight()/2);
    }

    @Override
    public void onHit(Enemy enemy)
    {
        if(damageTimer < MeleeAbility.getDamageInterval())
        {
            return;
        }

        enemy.takeDamage(getDamage());

        damageTimer = 0;
    }

    public boolean getExpired()
    {
        return deltaDuration <= 0;
    }

    @Override
    public float getDamage()
    {
        return MeleeAbility.getDamage();
    }

    @Override
    public void update(float deltaTime, GameMap map)
    {
        if(deltaTime == 0) ;
        if(getExpired()) return;

        deltaDuration -= deltaTime;

        damageTimer += deltaTime;

        move();
    }
}
