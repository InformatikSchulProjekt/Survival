package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.World;

public abstract class ActiveAbility extends BaseAbility {
    protected Viewport viewport;
    protected final Player player;
    protected final World world;
    protected final PlayerStats playerStats;
    protected final PlayerState playerState;

    private float cooldownStartTime = 0f;

    public ActiveAbility(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.viewport = viewport;
        this.playerState = player.getPlayerState();
    }
    public ActiveAbility(World world) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.playerState = player.getPlayerState();
    }

    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE_ABILITY;
    }

    protected abstract void activate();

    public abstract float getCooldown();

    public float getDuration() {
        return 0f;
    }

    public void tryActivate(float passedTime) {
        if (isOnCooldown(passedTime)) {
            return;
        }

        activate();
        cooldownStartTime = passedTime + getDuration();
    }
    protected int getLevel(){
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
