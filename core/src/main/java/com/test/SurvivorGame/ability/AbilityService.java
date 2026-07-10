package com.test.SurvivorGame.ability;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.activeAbilty.ActiveAbility;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.world.World;

import java.util.ArrayList;
import java.util.List;
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


        // Packt Aktive Abilities in die Ability slot leiste, wenn Platz da
        if (currentAmount == 0 && ability.getAbilityType() == AbilityType.ACTIVE_ABILITY) {
            boolean hadFreeSpace = tryPutAbilityIntoFreeSlot(abilityID);
            // debug:
            if (!hadFreeSpace) {
                System.out.println("Ability Bar had no empty slots for new ability: "+abilityID);
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

    private void registerAbilities() {
        for (Map.Entry<String, Integer> entry : playerState.getPlayerData().abilities.entrySet()) {
            String abilityID = entry.getKey();
            int amount = entry.getValue();

            applyAbility(abilityID, amount);

            System.out.println("Registered Ability: "+abilityID + " | lvl: " + amount); // debug
        }
    }

    public void activate(String abilityId, float survivalTime) {
        ActiveAbility ability = (ActiveAbility) abilityRegistry.getAbility(abilityId);

        if (ability != null) {
            ability.tryActivate(survivalTime);
        }
    }

    private boolean tryPutAbilityIntoFreeSlot(String abilityID) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        for (int i = 0; i < abilitySlots.length; i++) {
            if (abilitySlots[i] == null || abilitySlots[i].isBlank()) {
                abilitySlots[i] = abilityID;
                return true;
            }
        }

        return false;
    }

    public AbilityRegistry getAbilityRegistry() {
        return this.abilityRegistry;
    }

    public List<String> getUnlockedActiveAbilityIds() {
        List<String> result = new ArrayList<>();

        for (String abilityID : playerState.getPlayerData().abilities.keySet()) {
            BaseAbility ability = abilityRegistry.getAbility(abilityID);

            if (ability != null && ability.getAbilityType() == AbilityType.ACTIVE_ABILITY) {
                result.add(abilityID);
            }
        }

        return result;
    }

    public int getSlotIndexOfAbility(String abilityId) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        for (int i = 0; i < abilitySlots.length; i++) {
            if (abilityId != null && abilityId.equals(abilitySlots[i])) {
                return i;
            }
        }

        return -1;
    }

    public void equipAbilityToSlot(int slotIndex, String abilityId) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        if (slotIndex < 0 || slotIndex >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slotIndex);
        }

        BaseAbility ability = abilityRegistry.getAbility(abilityId);

        if (ability == null || ability.getAbilityType() != AbilityType.ACTIVE_ABILITY) {
            throw new IllegalArgumentException("Not a valid active ability: " + abilityId);
        }

        if (!playerState.getPlayerData().abilities.containsKey(abilityId)) {
            throw new IllegalStateException("Ability not unlocked yet: " + abilityId);
        }

        int existingSlot = getSlotIndexOfAbility(abilityId);

        if (existingSlot == slotIndex) {
            return; // Ability steckt schon genau in diesem Slot
        }

        String previousOccupant = abilitySlots[slotIndex];

        if (existingSlot != -1) {
            // Ability war schon in einem anderen Slot aktiv => Slots vertauschen
            abilitySlots[existingSlot] = previousOccupant;
        }

        abilitySlots[slotIndex] = abilityId;
    }

    // Entfernt die Ability aus dem Slot. Sie bleibt freigeschaltet, ist aber nicht mehr aktiv.
    public void unequipSlot(int slotIndex) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        if (slotIndex < 0 || slotIndex >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slotIndex);
        }

        abilitySlots[slotIndex] = null;
    }

}
