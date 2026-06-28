package com.test.SurvivorGame.core.data;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    public int xp = 0;
    public float hp = 10f;

    public float x = 0;
    public float y = 0;

    public Map<String, Integer> abilities = new HashMap<>();
    public Map<String, Boolean> items = new HashMap<>();

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public void setPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }
}
