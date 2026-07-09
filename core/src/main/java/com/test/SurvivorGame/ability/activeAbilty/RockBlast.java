package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Projectile;
import com.test.SurvivorGame.entity.abilityObjects.projectile.RockBlastProjectile;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.world.World;
// macht etwas mehr DMG weil es halt stein ist
public class RockBlast extends ActiveAbility {

    public static final String ID = "rock_blast";

    private final Viewport viewport;
    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final PlayerState playerState;
    private int level;

    private float duration = 3f;
    private float baseWidth = 2f;
    private float height= 1f;
    private float speed = 7f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private float baseDamage = 1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public RockBlast(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.viewport = viewport;
        this.playerState = player.getPlayerState();
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
    }

    @Override
    protected void activate() {
        RockBlastProjectile rockBlastProjectile = new RockBlastProjectile(
            player.getX(),
            player.getY(),
            baseWidth,
            height,
            texture,
            player,
            viewport,
            speed,
            duration,
            getDamage()
        );

        world.addAbility(rockBlastProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.EARTH, StatType.MAGIC_DAMAGE);
        if(level >= 2){
            damage *= 1.1f;
        }
        if(level ==5){
            damage *= 1.3f;
        }
        return damage;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Rock blast";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.EARTH, StatType.MAGIC_COOLDOWN);
        if(level >= 3){
            cooldown *= 0.9f;
        }
        if(level >= 4){
            cooldown *= 0.85f;
        }
        return cooldown;
    }

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Shoots a rock blast that explodes on impact";
            case 2:
                return "Rock blast damage increased by 10%";
            case 3:
                return "Rock blast cooldown decreased by 10%";
            case 4:
                return "cooldown reduced by 15%";
            case 5:
                return "Damage increased by 30%";
            default:
                return "No description available";
        }
    }
}
