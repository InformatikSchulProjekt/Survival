package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.projectile.WindBulletProjectile;
import com.test.SurvivorGame.world.World;

public class WindBullet extends ActiveAbility {

    public static final String ID = "wind_bullet";

    private float duration = 3f;
    private float baseWidth = 0.7f;
    private float height= 0.3f;
    private float speed = 7.5f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen
    private final int basePierce = 2;


    private static float baseDamage = 0.75f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WindBullet(World world, Viewport viewport) {
        super(world, viewport);

    }

    @Override
    protected void activate() {
        WindBulletProjectile windCutterProjectile = new WindBulletProjectile(
            player.getX(),
            player.getY(),
            getWidth(),
            height,
            texture,
            player,
            viewport,
            speed,
            duration,
            getDamage(),
            getPierce()
        );

        world.addAbility(windCutterProjectile);

    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.WIND, StatType.MAGIC_DAMAGE);
        if(getLevel() >= 2){
            damage *= 1.333f;
        }
        if(getLevel() ==5){
            damage *= 1.5f;
        }
        return damage;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Wind bullet";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.WIND, StatType.MAGIC_COOLDOWN);
        if(getLevel() >= 4){
            cooldown *= 0.8f;
        }
        return cooldown;
    }
    public float getWidth(){
        float width = baseWidth;
        if(getLevel() >= 4){
            width *= 1.25f;
        }
        if (getLevel() == 5){
            width *= 1.4;
        }
        return width;
    }
    public int getPierce() {
        int level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
        int pierce = basePierce;
        if (level >= 3) pierce += 1; // wenn die Ability Level 3 erreicht wird pierce um 2 erhöht
        return pierce;
    }
    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "creates a bullet of wind that pierces the enemy";
            case 2:
                return "Wave damage increased by 33%";
            case 3:
                return "pierce increased by 1";
            case 4:
                return "Width increased by 25% and cooldown reduced by 20%";
            case 5:
                return "Damage increased by 50%";
            default:
                return "No description available";
        }

    }
}
