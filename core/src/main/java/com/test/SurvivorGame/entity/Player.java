package com.test.SurvivorGame.entity;

import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.PlayerState;

/**
 * Repräsentiert die Spielfigur als Objekt in der Spielwelt.
 * <p>
 * Die Klasse ist für die Bewegung, Kollision und Schadensdarstellung
 * des Spielers zuständig. Die eigentlichen Spielerwerte wie Lebenspunkte,
 * Geschwindigkeit und Position werden im zugehörigen {@link PlayerState}
 * gespeichert und verändert.
 */
public class Player extends Entity  {
    private final PlayerState playerState;

    public Player(PlayerState playerState) {
        super(playerState.getX(), playerState.getY(), 1f, 1.5f, playerState.getHP(), playerState.getSpeed());
        this.playerState = playerState;
    }

    public void move(float deltaTime) {
        if (moveDirection.isZero()) return;

        float speed = playerState.getSpeed();
        //System.out.println("Player Speed: " + speed);
        float newX = collider.getX() + moveDirection.x * speed * deltaTime;
        float newY = collider.getY() + moveDirection.y * speed * deltaTime;

        playerState.setPosition(newX, newY);
        collider.setPosition(newX, newY);
    }

    @Override
    public void takeDamage(float damage) {

        float damageTaken = playerState.damage(damage);

        if (damageTaken > 0) {
            startDamageFlash();
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        SoundManager.updateFootsteps(!moveDirection.isZero());
        updateDamageFlash(deltaTime);
        move(deltaTime);
    }

}
