package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.world.World;

public class SmallHeal extends ActiveAbility {

    public static final String ID = "small_heal";

    private static final float BASE_COOLDOWN = 2f;
    private static final float BASE_HEAL = 2f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public SmallHeal(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.ALL);
    }

    @Override
    protected void activate() {
        playerState.heal(getHeal());
        SoundManager.playSound("SmallHeal.wav");
    }

    public void dispose() {
        texture.dispose();
    }

    public float getHeal() {
        float heal = BASE_HEAL;

        if (getLevel() >= 3) {
            heal *= 1.5f;
        }

        if (getLevel() >= 4) {
            heal *= 1.33f;
        }

        return heal;
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        if (getLevel() >= 2) {
            cooldown *= 0.9f;
        }

        if (getLevel() == 5) {
            cooldown *= 0.75f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Small Heal";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Heal regenerates a small amount of HP";
            case 2 -> "Reduces the ability cooldown by 10%";
            case 3 -> "Heal increases by 50%";
            case 4 -> "Heal increased by 33%";
            case 5 -> "Cooldown decreased by 25%";
            default -> "No description available";
        };
    }
}
