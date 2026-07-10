package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Entity;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.drops.ChestObject;
import com.test.SurvivorGame.entity.drops.ChestType;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

/**
 * Repräsentiert einen Gegner innerhalb der Spielwelt.
 * <p>
 * Die Klasse verwaltet grundlegende Eigenschaften und Verhaltensweisen,
 * die von allen Gegnertypen verwendet werden. Dazu gehören Lebenspunkte,
 * Schaden, Bewegungsgeschwindigkeit, Angriffe, Verlangsamungseffekte,
 * Trefferreaktionen und der Todeszustand.
 * <p>
 * Während der Aktualisierung bewegt sich der Gegner auf den Spieler zu.
 * Nach seinem Tod vergibt er Erfahrungspunkte, erhöht den Zähler besiegter
 * Gegner und kann abhängig von der Truhenchance eine Truhe erzeugen.
 * <p>
 * Konkrete Gegnertypen können diese Klasse erweitern und beispielsweise
 * eigene Werte, Animationen oder Belohnungen festlegen.
 * <p>
 * Jeder Enemy hat außerdem einen {@link AbilityType}.
 */
public class Enemy extends Entity { //sollte später abstract parent von den enemies sein, grad zum Testen is aber da ;
    private float currentHP; //current verändert sich, aber ist am start max

    private final float damage;

    private final Player player;
    private final World world;
    private final PlayerState playerState;

    protected float animationTime = 0f;
    private float originalMovementSpeed;
    private float slowTimer = 2f;

    private boolean dead = false;

    private final EnemyType enemyType;
    private boolean attacking = false;
    private float attackTimer = 0f;

    private static final float ATTACK_DURATION = 0.35f;
    private static final float DAMAGE_DURATION = 0.25f;
    private float attackCooldown = 0f;
    private static final float ATTACK_COOLDOWN = 1.0f; // 1 Angriff pro Sekunde
    private boolean removable = false;
    private static final float DEATH_DURATION = 0.6f;

    public Enemy(float x, float y, World world,
                 Vector2 size, float maxHP,
                 float movementSpeed, float damage, float hpMultiplier, EnemyType enemyType) {
        super(x, y, size.x, size.y, maxHP * hpMultiplier, movementSpeed);

        maxHP *= hpMultiplier;

        this.world = world;
        this.player = world.getPlayer();
        this.playerState = player.getPlayerState();

        this.maxHP = maxHP;
        this.currentHP = maxHP;

        this.damage = damage;
        this.enemyType = enemyType;
        this.movementSpeed = movementSpeed;
        this.originalMovementSpeed = movementSpeed;
    }

    public int getXPWorth() {
        return 2;
    }

    public float getDamage() {
        if (dead) return 0f;
        return damage;
    }

    public void applySlow(float speedMultiplier, float duration) {
        originalMovementSpeed = movementSpeed;  // Save original
        movementSpeed = originalMovementSpeed * 0.8f;
        slowTimer = duration;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean canAttack() {
        return attackCooldown <= 0f;
    }

    public void attack() {

        if (!canAttack()) {
            return;
        }

        attacking = true;
        attackTimer = ATTACK_DURATION;
        attackCooldown = ATTACK_COOLDOWN;
        animationTime = 0f;
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        if (dead) {

            if (animationTime >= DEATH_DURATION) {
                removable = true;
            }

            animationTime += deltaTime;
            return;
        }
        animationTime += deltaTime;
        updateDamageFlash(deltaTime);
        Vector2 moveDirection = new Vector2(player.getCenter())
            .sub(this.getCenter())
            .nor()
            .scl(movementSpeed * deltaTime);
        updateMoveDirection(moveDirection);
        collider.setPosition(
            collider.getX() + moveDirection.x,
            collider.getY() + moveDirection.y);

        if (slowTimer > 0f) {
            slowTimer -= deltaTime;
        } else {
            movementSpeed = originalMovementSpeed;
        }

        if (attacking) {
            attackTimer -= deltaTime;

            if (attackTimer <= 0f) {
                attacking = false;
            }
        }

        if (attackCooldown > 0f) {
            attackCooldown -= deltaTime;
        }
    }

    public void takeDamage(float damage) {
        if (dead) return;

        damage = calculateDamageTaken(damage);
        currentHP -= damage;

        animationTime = 0f;
        startDamageFlash();

        System.out.println("Enemy: " + damage + " dmg | hp: " + currentHP + "/" + maxHP);


        if (currentHP <= 0) { //=> Enemy Tod
            onDeath();
            damage -= currentHP;
            // => Echter Schaden (Wenn 1hp genug war um zu killen z. B. soll dmg net trzm 10 sein
        }
        onDamage(damage);
    }

    public boolean isRemovable() {
        return removable;
    }

    private void onDamage(float damage) {
        // Life steal heal:
        float heal = damage * playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL);
        if (heal > 0) {
            playerState.heal(damage * playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL));
        }
    }

    private float calculateDamageTaken(float damage) {
        if (shouldCrit()) {
            System.out.println("CRIT!"); // debug
            damage *= 1 + playerState.getPlayerStats().getStat(StatScope.ALL, StatType.CRIT_MULTIPLIER);
        }

        return damage;
    }

    private boolean shouldCrit() {
        float critChance = playerState.getPlayerStats().getStat(StatScope.ALL, StatType.CRIT_CHANCE);

        if (critChance <= 0f) {
            return false;
        }

        if (critChance > 1f) {
            critChance = 1f;
        }

        return Math.random() < critChance;
    }

    protected void onDeath() {
        dead = true;
        animationTime = 0f;

        if (shouldSpawnChest() && !(this instanceof Boss)) {
            System.out.println("Chest spawned!");
            world.addDrop(new ChestObject(getX(), getY(), player, ChestType.NORMAL));
        }

        playerState.giveXP(getXPWorth());
        playerState.getPlayerData().enemiesKilled++;
    }

    private boolean shouldSpawnChest() {
        float chance = getChestChance();

        chance = Math.clamp(chance, 0f, 1f);

        return Math.random() < chance;
    }

    public boolean isDead() {
        return dead;
    }

    // animation time access for renderer
    public float getAnimationTime() {
        return animationTime;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    protected ChestType getChestType() {
        return ChestType.NORMAL;
    }

    protected float getChestChance() {
        return playerState.getPlayerStats().getStat(
            StatScope.ALL,
            StatType.CHEST_CHANCE
        );
    }

    protected boolean isBoss() {
        return false;
    }

}
