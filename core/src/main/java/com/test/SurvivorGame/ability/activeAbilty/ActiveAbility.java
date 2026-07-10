package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.World;

public abstract class ActiveAbility extends BaseAbility {
    protected Viewport viewport;
    protected final Player player;
    protected final World world;
    protected final PlayerStats playerStats;
    protected final PlayerState playerState;

    private final StatScope statScope;
    private final String ID;

    private float cooldownStartTime = 0f;

    public ActiveAbility(String ID, World world, Viewport viewport, StatScope statScope) {
        this.ID = ID;
        this.player = world.getPlayer();
        this.world = world;
        this.playerState = player.getPlayerState();
        this.playerStats = playerState.getPlayerStats();
        this.viewport = viewport;
        this.statScope = statScope;
    }

    public ActiveAbility(String ID, World world, StatScope statScope) {
        this.ID = ID;
        this.player = world.getPlayer();
        this.world = world;
        this.playerState = player.getPlayerState();
        this.playerStats = playerState.getPlayerStats();
        this.statScope = statScope;
    }

    @Override
    public String getID() {
        return ID;
    }

    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE_ABILITY;
    }

    protected final float applyStat(float baseValue, StatType statType) {
        return baseValue * playerStats.getStat(statScope, statType);
    }

    protected abstract void activate();

    public abstract float getCooldown();

    public float getDuration() {
        return 0f;
    }

    @Override
    public StatScope getScope() {
        return statScope;
    }

    public void tryActivate(float passedTime) {
        if (isOnCooldown(passedTime)) {
            return;
        }

        activate();
        cooldownStartTime = passedTime + getDuration();
    }

    protected int getLevel() {
        return playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
    }

    private boolean isOnCooldown(float survivalTime) {
        float cooldown = Math.max(getCooldown(), 0.4f);
        float timeLeft = cooldown - (survivalTime - cooldownStartTime);

        if (timeLeft > 0) {
            System.out.println("[DEBUG] " + getName() + " cooldown left: " + timeLeft + "s");
            return true;
        }

        return false;
    }
}
