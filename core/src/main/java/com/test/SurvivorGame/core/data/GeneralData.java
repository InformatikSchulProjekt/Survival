package com.test.SurvivorGame.core.data;

import com.badlogic.gdx.Input;
import com.test.SurvivorGame.core.Input.Action;

import java.util.HashMap;
import java.util.Map;

public class GeneralData {
    public Map<String, Integer> keybinds = new HashMap<>();

    public GeneralData() {
        keybinds.put(Action.MOVE_UP.name(), Input.Keys.W);
        keybinds.put(Action.MOVE_DOWN.name(), Input.Keys.S);
        keybinds.put(Action.MOVE_LEFT.name(), Input.Keys.A);
        keybinds.put(Action.MOVE_RIGHT.name(), Input.Keys.D);

        keybinds.put(Action.ABILITY_1.name(), Input.Keys.NUM_1);
        keybinds.put(Action.ABILITY_2.name(), Input.Keys.NUM_2);
        keybinds.put(Action.ABILITY_3.name(), Input.Keys.NUM_3);
        keybinds.put(Action.ABILITY_4.name(), Input.Keys.NUM_4);

        keybinds.put(Action.PAUSE.name(), Input.Keys.ESCAPE);
    }
}
