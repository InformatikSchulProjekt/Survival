package com.test.SurvivorGame.ability;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.ability.statAbility.*;

import java.util.HashMap;
import java.util.Map;

public final class AbilityRegistry {
    private final Map<String, BaseAbility> abilities = new HashMap<>();


    public AbilityRegistry(World world, Viewport viewport) {
        // Stat Abilities:
        register(new Vitality());
        register(new SwiftStep());
        register(new Regeneration());
        register(new ArcaneEcho());
        register(new SpellPower());
        register(new QuickCasting());
        register(new ExpandingMagic());

        register(new MeleeAbility(world, viewport));
        register(new ProjectileAbility(world, viewport));
    }

    public BaseAbility getAbility(String abilityId) {
        return abilities.get(abilityId);
    }

    private void register(BaseAbility ability) {
        abilities.put(ability.getID(), ability);
    }

    public void activate(String abilityId) {
        BaseAbility ability = abilities.get(abilityId);

        if (ability != null) {
            ability.activate();
        }
    }


}
