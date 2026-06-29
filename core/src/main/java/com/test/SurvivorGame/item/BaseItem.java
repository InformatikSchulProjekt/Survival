package com.test.SurvivorGame.item;

import com.test.SurvivorGame.core.PlayerState;

public abstract class BaseItem {
    public abstract String getId();

    public abstract String getName();

    public abstract int getMaxAmount();

    public void onApply(PlayerState playerState) {
        // Wird ausgeführt, wenn das Item neu berechnet/angewendet wird.
        // Leer, weil nicht jedes Item Stats verändert.
    }

    public void onRemove(PlayerState playerState) {
        // Entfernt alte Modifier dieser Ability.
        // Kann von einzelnen Abilities überschrieben werden.
        playerState.getPlayerStats().removeModifiersFromSource("item:" + getId());
    }
}
