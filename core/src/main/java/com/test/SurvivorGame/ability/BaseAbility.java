package com.test.SurvivorGame.ability;

import com.test.SurvivorGame.core.PlayerState;

public abstract class BaseAbility {
    public abstract String getId();

    public abstract String getName();

    public abstract int getMaxAmount();

    public void onApply(PlayerState playerState, int amount) {
        // Wird ausgeführt, wenn die Ability neu berechnet/angewendet wird.
        // Leer, weil nicht jede Ability Stats verändert.
    }

    public void onRemove(PlayerState playerState) {
        // Entfernt alte Modifier dieser Ability.
        // Kann von einzelnen Abilities überschrieben werden.
        playerState.getPlayerStats().removeModifiersFromSource("ability:" + getId());
    }
}
