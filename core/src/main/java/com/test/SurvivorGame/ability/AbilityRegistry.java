package com.test.SurvivorGame.ability;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.active_abilities.*;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.ability.stat_abilities.*;

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

        // Active Abilities:
        register(new FireballAbility(world, viewport));
        register(new FireArrowAbility(world, viewport));
        register(new WaterBlast(world,viewport));
        register(new FireStormAbility(world));
        register(new EarthQuake(world));
        register(new WaterArrowAbility(world, viewport));
        register(new SmallHeal(world, viewport));
        register(new Wave(world, viewport));
        register(new WindCutter(world, viewport));
        register(new WindBullet(world, viewport));
        register(new RockBlast(world, viewport));
    }

    public BaseAbility getAbility(String abilityId) {
        return abilities.get(abilityId);
    }

    public Iterable<BaseAbility> getAbilities() {
        return abilities.values();
    }

    private void register(BaseAbility ability) {
        abilities.put(ability.getID(), ability);
    }

}
