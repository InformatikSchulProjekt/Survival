package com.test.SurvivorGame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.AbilityRegistry;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRegistry;
import com.test.SurvivorGame.player_class.BasePlayerClass;
import com.test.SurvivorGame.player_class.PlayerClassRegistry;

import java.util.Map;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();
    private final ItemRegistry itemRegistry;
    private final PlayerClassRegistry playerClassRegistry;

    private final PlayerData playerData;
    private int level;

    public PlayerState(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();

        this.itemRegistry = new ItemRegistry();
        registerItems();

        this.playerClassRegistry = new PlayerClassRegistry();
        registerPlayerClass();
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public int getLevel() {
        return level;
    }

    // returned currentHP (NICHT MAX_HP)
    public float getHP() {
        return playerData.hp;
    }

    public float getMaxHealth() {
        return playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH);
    }

    public float getSpeed() {
        return playerStats.getStat(StatScope.ALL, StatType.SPEED);
    }

    public void resetHealth() {
        playerData.hp = getMaxHealth();
    }

    public void heal(float amount) {
        if (amount <= 0f) {
            System.out.println("Attempt to heal <= 0 | nicht durchgeführt");
            return;
        }

        float hp = amount + playerData.hp;

        float maxHealth = getMaxHealth();
        if (hp > maxHealth) {
            hp = maxHealth;
        }
        playerData.hp = hp;
    }

    // true = survived; false = died;
    public boolean damage(float amount) {
        //if(playerData.hp <= 0) return true; // => Spieler ist bereits Tod
        if (amount <= 0f) {
            System.out.println("INVALID DAMAGE! Can't deal negative or 0 damage to player.");
            return true;
        }

        if(tryDodge()) {
            System.out.println("Dodged Attack"); //debug
            // => Dodge Visuals adden
            return true; // Spieler ist gedodged.
        }

        float resistance = playerStats.getStat(StatScope.ALL, StatType.RESISTANCE);
        if (resistance < 1f) {
            System.out.println("INVALID RESISTANCE! Resistance has to be at least 1f.");
            resistance = 1f;
        }

        float damageMultiplier = 1f / resistance;
        float finalDamage = amount * damageMultiplier;
        playerData.hp -= finalDamage;
        SoundManager.playSound("damaged1.wav");

        System.out.println("-"+finalDamage+"hp => "+playerData.hp+"/"+playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH)+"hp"); // debug

        if (playerData.hp <= 0f) {
            playerData.hp = 0f;
            // => Player Dead Logic
            System.out.println("Player died."); // debug
            return false;
        }
        return true;

    }

    public void setPosition(float x, float y) {
        playerData.x = x;
        playerData.y = y;
    }

    public float getX() {
        return playerData.x;
    }

    public float getY() {
        return playerData.y;
    }

    public void giveXP(int xp) {
        System.out.println("Giving XP: "+xp);
        playerData.xp += xp;

        int newLevel = calcLevel();
        if (newLevel > level) {
            level = newLevel;
            System.out.println("LEVEL UP: " + level);
            // => Level-Up Logic
        }
    }

    public void unlockItem(String itemId) {
        BaseItem item = itemRegistry.getItem(itemId);

        if (item == null) {
            throw new IllegalArgumentException("Unknown item: " + itemId);
        }

        if (playerData.items.contains(itemId)) {
            throw new IllegalStateException("Item already unlocked: " + itemId);
        }

        playerData.items.add(itemId);

        applyItem(itemId);
    }

    private int calcLevel() {
        int xp = Math.max(0, playerData.xp);
        return 1 + (int) Math.sqrt(xp / 5f);
    }

    private void registerItems() {
        for (String itemId : playerData.items) {
            applyItem(itemId);

            System.out.println("Registered Item: " + itemId); // debug
        }
    }

    private void applyItem(String itemId) {
        BaseItem item = itemRegistry.getItem(itemId);

        if (item == null) {
            throw new IllegalArgumentException("Unknown item: " + itemId);
        }

        item.onApply(playerStats);
    }

    private void registerPlayerClass() {
        BasePlayerClass playerClass = playerClassRegistry.getPlayerClass(playerData.playerClass);

        if (playerClass == null) {
            if (playerData.playerClass.isEmpty()) System.out.println("[ERROR]: No class given");
            throw new IllegalArgumentException("Unknown player class: " + playerData.playerClass);
        }

        playerClass.onApply(playerStats);

        System.out.println("Registered PlayerClass: " + playerData.playerClass); // debug
    }

    // true = dodged, false = nicht dodged => damage bekommen
    private boolean tryDodge() {
        float dodgeChance = playerStats.getStat(StatScope.ALL, StatType.DODGE_CHANCE);

        dodgeChance = calculateEffectiveDodgeChance(dodgeChance);
        //System.out.println("Dodge chance: "+dodgeChance); // debug

        if (dodgeChance <= 0f) {
            return false;
        }

        return Math.random() < dodgeChance;
    }

    // man kann damit nie wirklich 90% Dodge Chance erreichen, aber du näherst dich dem an mit allem über dem softcap
    private float calculateEffectiveDodgeChance(float dodgeChance) {
        if (dodgeChance <= 0.60f) {
            return dodgeChance;
        }

        float softCap = 0.60f;
        float maxCap = 0.90f;

        float overflow = dodgeChance - softCap;

        return softCap + (maxCap - softCap) * (overflow / (overflow + 1f));
    }

}
