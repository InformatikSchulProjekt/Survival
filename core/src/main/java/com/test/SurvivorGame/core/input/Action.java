package com.test.SurvivorGame.core.input;

import com.badlogic.gdx.Input;

public enum Action {
    MOVE_UP(Input.Keys.W),
    MOVE_DOWN(Input.Keys.S),
    MOVE_LEFT(Input.Keys.A),
    MOVE_RIGHT(Input.Keys.D),
    ABILITY_1(Input.Keys.NUM_1),
    ABILITY_2(Input.Keys.NUM_2),
    ABILITY_3(Input.Keys.NUM_3),
    ABILITY_4(Input.Keys.NUM_4),
    PAUSE(Input.Keys.ESCAPE);

    private final int defaultKey;

    Action(int defaultKey) {
        this.defaultKey = defaultKey;
    }

    public int getDefaultKey() {
        return defaultKey;
    }
}
