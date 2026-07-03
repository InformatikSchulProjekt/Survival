package com.test.SurvivorGame.core;

import com.test.SurvivorGame.ability.AbilityRegistry;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRegistry;
import com.test.SurvivorGame.player_class.BasePlayerClass;
import com.test.SurvivorGame.player_class.PlayerClassRegistry;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();
    private final ItemRegistry itemRegistry;
    private final PlayerClassRegistry playerClassRegistry;
    private AbilityService abilityService;
    private AbilityRegistry abilityRegistry;

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

    public void setupAbilityService(AbilityService abilityService) {
        this.abilityService = abilityService;
        this.abilityRegistry = abilityService.getAbilityRegistry();
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
        while (newLevel > level) { // => Level-Up Logic
            // Hier Spiel pausieren.
            if (abilityService == null) throw new IllegalStateException("AbilityService not initialized.");

            level++;
            System.out.println("LEVEL UP: " + level); // debug
            int optionsCount = 3;
            String[] abilityOptions = genAbilityOptions(optionsCount);

            // debug:
            System.out.println("Ability Optionen;");
            printStringArray(abilityOptions);

            int result = levleUpUI(abilityOptions);
            abilityService.unlockAbility(abilityOptions[result]);

            // falls penalty davor vorhanden, aufheben
            playerData.skippedAbilityOptions.remove(abilityOptions[result]);

            // penalty sozusagen für, dass es nicht gepicked wurde
            abilityOptions[result] = null; // damit nicht auch penalty bekommt
            for (String abilityID : abilityOptions) {
                if (abilityID == null) continue;

                // erhöht penalty um 0.5 bzw. wenn keine da, dann setzt auf 0.5
                float currentPenalty = playerData.skippedAbilityOptions.getOrDefault(abilityID, 0f);
                playerData.skippedAbilityOptions.put(abilityID, currentPenalty + 0.5f);
            }
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

    private String[] genAbilityOptions(int count) {
        if (abilityRegistry == null) {
            throw new IllegalStateException("AbilityRegistry has not been set up.");
        }

        String[] options = new String[count];

        for (int i = 0; i < count; i++) {
            options[i] = rollAbilityOption(options, i);
        }

        return options;
    }

    private String rollAbilityOption(String[] alreadyPicked, int pickedCount) {
        float totalWeight = 0f;

        for (BaseAbility ability : abilityRegistry.getAbilities()) {
            if (!canRollAbility(ability, alreadyPicked, pickedCount)) {
                continue;
            }

            totalWeight += calculateAbilityWeight(ability);
        }

        if (totalWeight <= 0f) {
            throw new IllegalStateException("totalWeight is <= 0");
        }

        float roll = (float) (Math.random() * totalWeight);
        float currentWeight = 0f;

        for (BaseAbility ability : abilityRegistry.getAbilities()) {
            if (!canRollAbility(ability, alreadyPicked, pickedCount)) {
                continue;
            }

            currentWeight += calculateAbilityWeight(ability);

            if (roll <= currentWeight) {
                return ability.getID();
            }
        }

        throw new IllegalStateException("rollAbilityOption went wrong.");
    }

    private boolean canRollAbility(BaseAbility ability, String[] alreadyPicked, int pickedCount) {
        String abilityID = ability.getID();

        for (int i = 0; i < pickedCount; i++) {
            if (abilityID.equals(alreadyPicked[i])) {
                return false;
            }
        }

        int currentAmount = playerData.abilities.getOrDefault(abilityID, 0);

        return currentAmount < ability.getMaxAmount();
    }

    // je höher das Weight desto wahrscheinlicher
    private float calculateAbilityWeight(BaseAbility ability) {
        float weight = 1f;
        String abilityID = ability.getID();

        if (playerData.skippedAbilityOptions.containsKey(abilityID)) {
            float skippedCount = playerData.skippedAbilityOptions.get(abilityID);
            weight *= 1f - skippedCount;
        }

        // Wenn Spieler Ability schon hat, soll er sie öfter wieder bekommen.
        if (playerData.abilities.containsKey(abilityID)) {
            weight *= 2f;
        }

        if (isAbilityPreferredByClass(ability)) {
            weight *= 1.5f;
        }

        if (weight < 0) weight = 0;
        return weight;
    }

    private boolean isAbilityPreferredByClass(BaseAbility ability) {
        StatScope abilityScope = ability.getScope();
        if(abilityScope == null) return false; // Ability ist neutral / hat kein Element
        return abilityScope == playerClassRegistry.getPlayerClass(playerData.playerClass).getScope();
        // => Ability hat gleiches Element (Scope) wie Player Klasse
    }

    private int levleUpUI(String[] abilityOptions) {
        // Muss von Levin implementiert werden.

        // temporär bis da hin:
        return 1;
    }

    // DEBUG:
    private void printStringArray(String[] array) {
        for (String str : array) {
            System.out.println(str);
        }
    }

}
