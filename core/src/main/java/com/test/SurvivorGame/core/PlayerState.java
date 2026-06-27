package com.test.SurvivorGame.core;

import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.stat.PlayerStats;
import com.test.SurvivorGame.stat.StatScope;
import com.test.SurvivorGame.stat.StatType;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();

    private PlayerData playerData;
    private int level = 1;
    private float currentHP;

    public PlayerState() {
        currentHP = getMaxHealth();
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();
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

    public void giveXP(int xp) {
        if (playerData == null) {
            System.out.println("playerData in PlayerState is NULL !!!!");
            return;
        }

        playerData.xp += xp;

        int newLevel = calcLevel();
        if (newLevel > level) {
            level = newLevel;
            System.out.println("LEVEL UP: " + level);
            // hier dann level up einleiten
        }
    }

    private int calcLevel() {
        if (playerData == null) {
            System.out.println("playerData in PlayerState is NULL !!!!");
            return 1;
        }

        return playerData.xp / 3 + 1;
    }
}
