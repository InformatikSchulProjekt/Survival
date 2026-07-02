package com.test.SurvivorGame.ability;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.world.World;

import java.util.Map;

public class AbilityService {

    private final PlayerState playerState;
    private final AbilityRegistry abilityRegistry;

    public AbilityService(PlayerState playerState, World world, Viewport viewport) {
        this.playerState = playerState;
        this.abilityRegistry = new AbilityRegistry(world, viewport);

        registerAbilities();
    }

    public void unlockAbility(String abilityID) {
        BaseAbility ability = abilityRegistry.getAbility(abilityID);

        if (ability == null) {
            throw new IllegalArgumentException("Unknown ability: " + abilityID);
        }

        int currentAmount = playerState.getPlayerData().abilities.getOrDefault(abilityID, 0);

        if (currentAmount >= ability.getMaxAmount()) {
            throw new IllegalStateException("Ability already maxed");
        }

        int newAmount = currentAmount + 1;
        playerState.getPlayerData().abilities.put(abilityID, newAmount);

        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        // Packt Aktive Abilities in die Ability slot leiste, wenn Platz da
        if (ability.getAbilityType() == AbilityType.ACTIVE_ABILITY) {
            for (int i = 0; i < abilitySlots.length; i++) {
                if (abilitySlots[i] == null || abilitySlots[i].isBlank()) {
                    abilitySlots[i] = abilityID;
                    break;
                }
            }
        // Applied die Modifier von StatAbilities
        } else if (ability.getAbilityType() == AbilityType.STAT_ABILITY) {
            ability.onApply(playerState.getPlayerStats(), newAmount);
        }
    }

    private void applyAbility(String abilityID, int amount) {
        BaseAbility ability = abilityRegistry.getAbility(abilityID);

        if (ability == null) {
            throw new IllegalArgumentException("Unknown ability: " + abilityID);
        }

        ability.onApply(playerState.getPlayerStats(), amount);
    }

    // looped durch alle Entries also alle Abilities und initialisiert die Klassen für die
    private void registerAbilities() {
        for (Map.Entry<String, Integer> entry : playerState.getPlayerData().abilities.entrySet()) {
            String abilityID = entry.getKey();
            int amount = entry.getValue();

            applyAbility(abilityID, amount);

            System.out.println("Registered Ability: "+abilityID + " | lvl: " + amount); // debug
        }
    }

    public void activate(String abilityId) {
        BaseAbility ability = abilityRegistry.getAbility(abilityId);

        if (ability != null) {
            ability.activate();
        }
    }
}
