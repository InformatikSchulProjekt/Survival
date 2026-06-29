package com.test.SurvivorGame.core;

import com.test.SurvivorGame.ability.AbilityRegistry;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.stat.*;

import java.util.Map;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();
    private final AbilityRegistry abilityRegistry;

    private final PlayerData playerData;
    private int level;

    public PlayerState(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();

        this.abilityRegistry = new AbilityRegistry();

        // looped durch alle Entries also alle Abilities und initialisiert die Klassen für die
        for (Map.Entry<String, Integer> entry : playerData.abilities.entrySet()) {
            String abilityID = entry.getKey();
            int amount = entry.getValue();

            System.out.println(abilityID + ": " + amount); // debug

            applyAbility(abilityID, amount);
        }
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
        playerData.hp -= amount;

        // debug:
        System.out.println(playerData.hp+"/"+playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH));

        if (playerData.hp < 0f) {
            playerData.hp = 0f;
            // => Player Dead Logic
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

    // abilities:
    public void unlockAbility(String abilityID) {
        BaseAbility ability = abilityRegistry.getAbility(abilityID);

        if (ability == null) {
            throw new IllegalArgumentException("Unknown ability: " + abilityID);
        }

        int currentAmount = playerData.abilities.getOrDefault(abilityID, 0);

        if (currentAmount >= ability.getMaxAmount()) {
            throw new IllegalStateException(
                "Ability already maxed: " + abilityID
                    + " (" + currentAmount + "/" + ability.getMaxAmount() + ")"
            );
        }

        int newAmount = currentAmount + 1;
        playerData.abilities.put(abilityID, newAmount);

        applyAbility(abilityID, newAmount);
    }

    private void applyAbility(String abilityID, int amount) {
        BaseAbility ability = abilityRegistry.getAbility(abilityID);

        if (ability == null) {
            throw new IllegalArgumentException("Unknown ability: " + abilityID);
        }

        ability.onApply(this, amount);
    }

    private int calcLevel() {
        int xp = Math.max(0, playerData.xp);
        return 1 + (int) Math.sqrt(xp / 5f);
    }
}
