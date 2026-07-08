package com.test.SurvivorGame.core.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.world.World;

public class InputManager {

    private final World world;
    private final AbilityService abilityService;
    private final PlayerState playerState;

    private final Vector2 moveDirection = new Vector2();

    KeyBindings keyBindings;

    public InputManager(World world, AbilityService abilityService, KeyBindings keyBindings) {

        this.world = world;
        this.abilityService = abilityService;
        this.playerState = world.getPlayer().getPlayerState();
        this.keyBindings = keyBindings;
    }

    public void update(float passedTime) {
        handleMovement();
        handleAbilities(passedTime);
    }

    private void handleMovement() {
        moveDirection.setZero();

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_UP)))
            moveDirection.y++;

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_DOWN)))
            moveDirection.y--;

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_LEFT)))
            moveDirection.x--;

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_RIGHT)))
            moveDirection.x++;

        if (!moveDirection.isZero())
            moveDirection.nor();

        world.getPlayer().updateMoveDirection(moveDirection);
    }

    private void handleAbilities(float passedTime) {

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_1)))
            activateSlot(0, passedTime);

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_2)))
            activateSlot(1, passedTime);

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_3)))
            activateSlot(2, passedTime);

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_4)))
            activateSlot(3, passedTime);
    }

    private void activateSlot(int slot, float passedTime) {

        String ability = playerState.getPlayerData().abilitySlots[slot];

        if (ability == null || ability.isBlank())
        {
            return;
        }

        abilityService.activate(ability, passedTime);
    }
}
