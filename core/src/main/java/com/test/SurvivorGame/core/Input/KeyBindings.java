package com.test.SurvivorGame.core.Input;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class KeyBindings {

    private  Map<Action, Integer> bindings = new HashMap<>();

    private final Map<Action, Integer> standardBindings;

    public KeyBindings() {

        bindings.put(Action.MOVE_UP, Input.Keys.W);
        bindings.put(Action.MOVE_DOWN, Input.Keys.S);
        bindings.put(Action.MOVE_LEFT, Input.Keys.A);
        bindings.put(Action.MOVE_RIGHT, Input.Keys.D);

        bindings.put(Action.ABILITY_1, Input.Keys.NUM_1);
        bindings.put(Action.ABILITY_2, Input.Keys.NUM_2);
        bindings.put(Action.ABILITY_3, Input.Keys.NUM_3);
        bindings.put(Action.ABILITY_4, Input.Keys.NUM_4);

        bindings.put(Action.PAUSE, Input.Keys.ESCAPE);

        standardBindings = new HashMap<>(bindings);
    }

    public int getKey(Action action) {
        return bindings.get(action);
    }

    public void setKey(Action action, int key) {
        bindings.put(action, key);
    }

    public void resetKeys()
    {
        bindings.clear();
        bindings.putAll(standardBindings);
    }
}
