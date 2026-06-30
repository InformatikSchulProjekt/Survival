package com.test.SurvivorGame.core;

import com.test.SurvivorGame.ability.AbilityRegistry;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRegistry;

import java.util.Map;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();
    private final AbilityRegistry abilityRegistry;
    private final ItemRegistry itemRegistry;

    private final PlayerData playerData;
    private int level;

    public PlayerState(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();

        this.abilityRegistry = new AbilityRegistry();
        registerAbilities();

        this.itemRegistry = new ItemRegistry();
        registerItems();

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

        float resistance = playerStats.getStat(StatScope.ALL, StatType.RESISTANCE);
        if (resistance < 1f) {
            System.out.println("INVALID RESISTANCE! Resistance has to be at least 1f.");
            resistance = 1f;
        }

        float damageMultiplier = 1f / resistance;
        float finalDamage = amount * damageMultiplier;
        playerData.hp -= finalDamage;

        System.out.println("-"+finalDamage+"hp => "+playerData.hp+"/"+playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH)+"hp"); // debug

        if (playerData.hp < 0f) {
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

    // looped durch alle Entries also alle Abilities und initialisiert die Klassen für die
    private void registerAbilities() {
        for (Map.Entry<String, Integer> entry : playerData.abilities.entrySet()) {
            String abilityID = entry.getKey();
            int amount = entry.getValue();

            applyAbility(abilityID, amount);

            System.out.println("Registered Ability: "+abilityID + " | lvl: " + amount); // debug
        }
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


}
