package com.test.SurvivorGame.core.data;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class PlayerData {
    public float xp = 0;
    public float hp = 10f;

    public float x = 0;
    public float y = 0;

    public int wave = 1;

    public int revivesUsed = 0;

    public String playerClass = "";

    public String[] abilitySlots = new String[4];
    public Map<String, Float> skippedAbilityOptions = new HashMap<>();
    public Map<String, Integer> abilities = new HashMap<>();
    public Set<String> items = new HashSet<>();

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
    public String getPlayerClass(){return playerClass;}

}
