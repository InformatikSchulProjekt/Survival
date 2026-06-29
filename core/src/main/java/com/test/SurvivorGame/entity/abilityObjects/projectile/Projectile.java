package com.test.SurvivorGame.entity.abilityObjects.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.ProjectileAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;

public class Projectile extends AbilityObject {

    private Viewport viewport;

    private Player player;

    private Vector3 mouseScreen;
    private Vector2 direction;

    private float speed;

    private boolean expired;

    public Projectile(float x, float y, float effectSize, Texture texture, Player player, Viewport viewport, float speed)
    {
        super(x, y, effectSize, effectSize, texture);

        this.player = player;
        this.viewport = viewport;
        this.speed  = speed;

        mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(mouseScreen);
        direction = new Vector2(mouseScreen.x - player.getCenter().x, mouseScreen.y - player.getCenter().y);
        direction.nor();
        rotation = direction.angleDeg();
        collider.setPosition(player.getCenter().x + direction.x - getWidth()/2, player.getCenter().y + direction.y - getHeight()/2);
    }

    @Override
    public void update(float deltaTime)
    {
        if(isExpired()) return;

        move(deltaTime);
    }

    public void move(float deltaTime)
    {
        collider.setPosition(collider.getX() + direction.x * speed * deltaTime,collider.getY() + direction.y * speed * deltaTime);
    }

    @Override
    public void onHit(Enemy enemy)
    {
        enemy.takeDamage(getDamage());
        expired = true;
    }

    @Override
    public boolean isExpired()
    {
        return  expired;
    }

    @Override
    public float getDamage()
    {
        return ProjectileAbility.getDamage();
    }

}
