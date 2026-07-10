package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public abstract class Projectile extends AbilityObject {

    private Viewport viewport;
    private float deltaDuration;
    private final long cooldown = 1;

    private Player player;

    private Vector3 mouseScreen;
    protected Vector2 direction;

    private float speed;

    protected boolean expired;

    public Projectile(float x, float y, float effectSize, Texture texture, Player player, Viewport viewport, float speed, float duration) {
        this(x, y, effectSize, effectSize, texture, player, viewport, speed, duration);
    }

    public Projectile(float x, float y, float width, float height, Texture texture, Player player, Viewport viewport, float speed, float duration) {
        super(x, y, width, height, texture);

        this.deltaDuration = duration;
        this.player = player;
        this.viewport = viewport;
        this.speed = speed;

        mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(mouseScreen);
        direction = new Vector2(mouseScreen.x - player.getCenter().x, mouseScreen.y - player.getCenter().y);
        direction.nor();
        rotation = direction.angleDeg();
        collider.setPosition(player.getCenter().x + direction.x - getWidth() / 2, player.getCenter().y + direction.y - getHeight() / 2);

    }

    public Projectile(
        float x,
        float y,
        float width,
        float height,
        Texture texture,
        Vector2 direction,
        float speed,
        float duration
    ) {
        super(x, y, width, height, texture);

        this.deltaDuration = duration;
        this.speed = speed;

        this.direction = new Vector2(direction).nor();
        rotation = this.direction.angleDeg();

        collider.setPosition(
            x - getWidth()/2,
            y - getHeight()/2
        );
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        if (deltaDuration <= 0) expired = true;
        if (getExpired()) return;

        deltaDuration -= deltaTime;

        move(deltaTime);
    }

    public void move(float deltaTime) {
        collider.setPosition(collider.getX() + direction.x * speed * deltaTime, collider.getY() + direction.y * speed * deltaTime);
    }

    protected void expire() {
        expired = true;
    }


    protected void damageEnemy(Enemy enemy, float damage) {

        enemy.takeDamage(damage);
    }

    public void onHit(Enemy enemy) {
        damageEnemy(enemy, getDamage());
        expired = true;
    }

    @Override
    public boolean getExpired() {
        return expired;
    }
}
