package com.test.SurvivorGame.core;

import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.stat.PlayerStats;
import com.test.SurvivorGame.stat.StatScope;
import com.test.SurvivorGame.stat.StatType;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();

    private final PlayerData playerData;
    private int level;
    private float currentHP;

    public PlayerState(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();
        this.currentHP = getMaxHealth();
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

    public float getCurrentHP() {
        return currentHP;
    }

    public float getMaxHealth() {
        return playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH);
    }

    public float getSpeed() {
        return playerStats.getStat(StatScope.ALL, StatType.SPEED);
    }

    public void resetHealth() {
        currentHP = getMaxHealth();
    }

    public void heal(float amount) {
        currentHP += amount;

        float maxHealth = getMaxHealth();
        if (currentHP > maxHealth) {
            currentHP = maxHealth;
        }
    }

    public void damage(float amount) {
        currentHP -= amount;

        if (currentHP < 0f) {
            currentHP = 0f;
        }
    }

    public boolean isDead() {
        return currentHP <= 0f;
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
        playerData.xp += xp;

        int newLevel = calcLevel();
        if (newLevel > level) {
            level = newLevel;
            System.out.println("LEVEL UP: " + level);
            // hier dann level up einleiten
        }
    }

    private int calcLevel() {
        int xp = Math.max(0, playerData.xp);
        return 1 + (int) Math.sqrt(xp / 5f);
    }
}
