package com.test.SurvivorGame.core.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.screen.GameState;
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

    public boolean processInput()
    {
        handleMovement();
        handleAbilities();

        return Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.PAUSE));
    }

    private void handleMovement() {
        moveDirection.setZero();

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_UP)))
        {
            moveDirection.y++;
        }

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_DOWN)))
        {
            moveDirection.y--;
        }

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_LEFT)))
        {
            moveDirection.x--;
        }

        if (Gdx.input.isKeyPressed(keyBindings.getKey(Action.MOVE_RIGHT)))
        {
            moveDirection.x++;
        }

        if (!moveDirection.isZero())
        {
            moveDirection.nor();
        }

        world.getPlayer().updateMoveDirection(moveDirection);
    }

    private void handleAbilities() {

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_1)))
        {
            activateAbilitySlot(0);
        }

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_2)))
        {
            activateAbilitySlot(1);
        }

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_3)))
        {
            activateAbilitySlot(2);
        }

        if (Gdx.input.isKeyJustPressed(keyBindings.getKey(Action.ABILITY_4)))
        {
            activateAbilitySlot(3);
        }
    }

    private void  activateAbilitySlot(int slotIndex) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        if (slotIndex < 0 || slotIndex >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slotIndex);
        }

        String abilityId = abilitySlots[slotIndex];

        if (abilityId == null || abilityId.isBlank()) {
            return;
        }

        abilityService.activate(abilityId, world.getPassedTime());
    }
}
