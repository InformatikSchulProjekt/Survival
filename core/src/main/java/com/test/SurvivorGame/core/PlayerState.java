package com.test.SurvivorGame.core;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.ability.AbilityRegistry;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.drops.ChestObject;
import com.test.SurvivorGame.entity.drops.ChestType;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;
import com.test.SurvivorGame.item.ItemRegistry;
import com.test.SurvivorGame.player_class.BasePlayerClass;
import com.test.SurvivorGame.player_class.PlayerClassRegistry;

import java.util.ArrayList;

public final class PlayerState {
    private final PlayerStats playerStats = new PlayerStats();
    private final ItemRegistry itemRegistry;
    private final PlayerClassRegistry playerClassRegistry;
    private AbilityService abilityService;
    private AbilityRegistry abilityRegistry;

    private final PlayerData playerData;
    private int level;
    private boolean dodgedLastAttack = false;
    private long dodgeEffectStartTime = 0;
    private static final long DODGE_EFFECT_DURATION = 300;
    private boolean gameOver = false;

    private boolean awaitingLevelUpChoice = false;
    private String[] pendingAbilityOptions = null;
    private int pendingLevelUps = 0;

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

    public AbilityRegistry getAbilityRegistry() {
        return abilityRegistry;
    }

    public boolean isGameOver() {
        return gameOver;
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

    public float getXP() {
        return playerData.xp;
    }

    public float getXpProgress() {
        int xpForCurrentLevel = xpRequiredForLevel(level);
        int xpForNextLevel = xpRequiredForLevel(level + 1);

        int xpIntoLevel = (int) playerData.xp - xpForCurrentLevel;
        int xpNeededForLevel = xpForNextLevel - xpForCurrentLevel;

        if (xpNeededForLevel <= 0) return 1f; // safety fallback, sollte nicht vorkommen

        return MathUtils.clamp(xpIntoLevel / (float) xpNeededForLevel, 0f, 1f);
    }

    // Gleiche Formel wie in calcLevel(), nur umgestellt nach xp. Level 1 braucht 0 XP.
    private int xpRequiredForLevel(int lvl) {
        int n = lvl - 1;
        return 5 * n * n;
    }

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
            dodgedLastAttack=true;
            dodgeEffectStartTime = System.currentTimeMillis();
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
            // => Player Dead Logic:
            boolean revived = deathScreen(); // Ergebnis vom UI Spieler Input
            if (!revived) { //=> Spieler ist nicht revived, also Tod
                System.out.println("Player died."); // debug
                gameOver = true;
                return false;
            } // => Spieler ist revived
            System.out.println("Player ist revived");
            playerData.hp = playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH) / 2;
            // => Spieler kriegt hälfte HP zurück bei Revive
            playerData.revivesUsed++;
        }
        return true;
    }
    public boolean isDodgeEffectActive() {
        return System.currentTimeMillis() - dodgeEffectStartTime < DODGE_EFFECT_DURATION;
    }

    // sollte true returnen, wenn der Spieler reviven kann und will. False wenn eben nicht
    private boolean deathScreen() {
        boolean playerCanRevive = playerStats.getStat(StatScope.ALL, StatType.REVIVES) > playerData.revivesUsed;
        //System.out.println("Player can revive: "+playerCanRevive); // debug

        // Hier UI Stuff einfügen

        // temporär, bis UI implementiert:
        return playerCanRevive;
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
        float actualXP = xp * playerStats.getStat(StatScope.ALL, StatType.XP_GAIN);
        playerData.xp += actualXP;
        System.out.println("Gained XP: "+actualXP);

        int newLevel = calcLevel();
        if (newLevel > level) {
            pendingLevelUps += (newLevel - level);
            level = newLevel;

            startNextLevelUpIfNeeded();
        }
    }

    // Bereitet die nächste Level-Up-Auswahl vor (falls noch welche ausstehen und
    // gerade keine andere Auswahl offen ist). Die UI liest awaitingLevelUpChoice()
    // und pendingAbilityOptions aus und ruft nach Auswahl chooseAbilityOption(index) auf.
    private void startNextLevelUpIfNeeded() {
        if (awaitingLevelUpChoice) return; // es läuft schon eine Auswahl
        if (pendingLevelUps <= 0) return;

        if (abilityService == null) throw new IllegalStateException("AbilityService not initialized.");

        int optionsCount = 3;
        String[] abilityOptions;
        try {
            abilityOptions = genAbilityOptions(optionsCount);
        } catch (Exception e) {
            System.out.println("[ERROR]: "+e.toString());
            pendingLevelUps = 0; // damit es nicht endlos wieder versucht wird
            return;
        }

        // debug:
        System.out.println("Ability Optionen;");
        printStringArray(abilityOptions);

        pendingAbilityOptions = abilityOptions;
        awaitingLevelUpChoice = true;
    }

    public boolean isAwaitingLevelUpChoice() {
        return awaitingLevelUpChoice;
    }

    public String[] getPendingAbilityOptions() {
        return pendingAbilityOptions;
    }

    // Wird von der Level-Up UI aufgerufen, sobald der Spieler eine Karte gewählt hat.
    public void chooseAbilityOption(int result) {
        if (!awaitingLevelUpChoice || pendingAbilityOptions == null) return;

        abilityService.unlockAbility(pendingAbilityOptions[result]);

        // falls penalty davor vorhanden, aufheben
        playerData.skippedAbilityOptions.remove(pendingAbilityOptions[result]);

        // penalty sozusagen für, dass es nicht gepicked wurde
        pendingAbilityOptions[result] = null; // damit nicht auch penalty bekommt
        for (String abilityID : pendingAbilityOptions) {
            if (abilityID == null) continue;

            // erhöht penalty um 0.5 bzw. wenn keine da, dann setzt auf 0.5
            float currentPenalty = playerData.skippedAbilityOptions.getOrDefault(abilityID, 0f);
            playerData.skippedAbilityOptions.put(abilityID, currentPenalty + 0.5f);
        }

        pendingAbilityOptions = null;
        awaitingLevelUpChoice = false;
        pendingLevelUps = Math.max(0, pendingLevelUps - 1);

        startNextLevelUpIfNeeded(); // falls z.B. 2 Level auf einmal aufgestiegen wurde
    }

    public void collectDrop(DroppedObject drop) {
        if (drop instanceof ChestObject) { // => Drop ist eine Chest
            openChest(((ChestObject) drop).getChestType());
        }
    }

    private void openChest(ChestType chestType) {
        BaseItem[] itemChoices = new BaseItem[3];

        for (int i = 0; i < itemChoices.length; i++) {
            try {
                BaseItem item = rollChestItem(chestType, itemChoices, i);
                itemChoices[i] = item;
            } catch(Exception e) {
                System.out.println("[ERROR]: "+e.toString());
                return;
            }
        }

        int chosenItemIndex = chestUI(itemChoices); // UI Öffnen für User Input

        unlockItem(itemChoices[chosenItemIndex].getID());
    }

    private int chestUI(BaseItem[] itemChoices) {
        // temporär bis UI dafür da:
        return 0;
    }

    private BaseItem rollChestItem(ChestType chestType, BaseItem[] alreadyPicked, int pickedCount) {
        ItemRarity itemRarity;

        if (chestType == ChestType.BOSS) {
            itemRarity = ItemRarity.LEGENDARY;
        } else {
            itemRarity = rollItemRarity();
        }

        BaseItem[] itemsOfRarity = itemRegistry.getItemsByRarity(itemRarity);
        ArrayList<BaseItem> possibleItems = new ArrayList<>();

        for (BaseItem item : itemsOfRarity) {
            if (playerData.items.contains(item.getID())) {
                continue; // Player hat Item schon
            }
            if (isItemAlreadyPicked(item, alreadyPicked, pickedCount)) {
                continue; // Item ist bereits in Auswahl
            }

            possibleItems.add(item);
        }

        if (possibleItems.isEmpty()) {
            throw new IllegalStateException("No available item found for rarity: " + itemRarity);
        }

        int randomIndex = getRandomIndex(possibleItems.size());
        return possibleItems.get(randomIndex);
    }

    private boolean isItemAlreadyPicked(BaseItem item, BaseItem[] alreadyPicked, int pickedCount) {
        for (int i = 0; i < pickedCount; i++) {
            if (alreadyPicked[i] != null && alreadyPicked[i].getID().equals(item.getID())) {
                return true;
            }
        }

        return false;
    }

    private int getRandomIndex(int length) {
        return (int) (Math.random() * length);
    }

    private ItemRarity rollItemRarity() {
        float commonWeight = 1f;
        float rareWeight = 0.25f;
        float epicWeight = 0.1f;
        float legendaryWeight = 0.03f;

        float totalWeight = commonWeight + rareWeight + epicWeight + legendaryWeight;

        float roll = (float) (Math.random() * totalWeight); // (float) weil das double ist
        float currentWeight = 0f;

        currentWeight += commonWeight;
        if (roll <= currentWeight) {
            return ItemRarity.COMMON;
        }

        currentWeight += rareWeight;
        if (roll <= currentWeight) {
            return ItemRarity.RARE;
        }

        currentWeight += epicWeight;
        if (roll <= currentWeight) {
            return ItemRarity.EPIC;
        }

        return ItemRarity.LEGENDARY;
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
        float xp = Math.max(0, playerData.xp);
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

    // DEBUG:
    private void printStringArray(String[] array) {
        for (String str : array) {
            System.out.println(str);
        }
    }

    public void gameOver()
    {
        gameOver = true;
    }
}
