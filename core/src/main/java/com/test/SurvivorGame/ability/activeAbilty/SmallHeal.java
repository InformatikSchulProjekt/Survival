package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

public class SmallHeal extends ActiveAbility {

    public static final String ID = "small_heal";

    // Ability base Stats
    private final float baseDuration = 3f;
    private final float baseInterval = 1.5f;
    private final float baseCooldown = 1f;
    private final float baseHeal= 100f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public SmallHeal(World world, Viewport viewport) {
        super(world, viewport);
    }

    @Override
    protected void activate() {
        player.getPlayerState().heal(getHeal());
        System.out.println("Healed HP: "+ getHeal());
            }

    public void dispose() {
        texture.dispose();
    }

    public float getHeal() {
        float heal = baseHeal;
        heal *= playerStats.getStat(StatType.HEALING);
        heal *= playerStats.getStat(StatScope.WATER,StatType.HEALING);
        if (getLevel() >= 3) heal*= 1.5f;
        if (getLevel() >= 4) heal*= 1.33f;
        return heal;
    }
//    public float getbaseInterval() {
//        return baseInterval;
//    }



    public float getDuration(){
        float duration = baseDuration;
        return duration;
    }


    public float getCooldown() {
        float cooldown = baseCooldown;
        if (getLevel()>=2) cooldown*= 0.9f;
        if (getLevel()== 5)cooldown*=0.75f;
        return cooldown;

    }

    @Override
    public String getID() {
        return ID;
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
    public String getDescription(int level){
        switch(level){
            case 1:
                return "Heal regenerates a small amount of HP";
            case 2:
                return "reduces the ability cooldown by 10%";
            case 3:
                return "heal increases by 50%";
            case 4:
                return "heal increased by 33%%";
            case 5:
                return "cooldown decreased by 25%";
            default:
                return "No description available";
        }
    }

//    public List<StatModifier> getModifiers(int amount){
//       int pierce = basePierce;
//        ArrayList<StatModifier> mods = new ArrayList<>();
//        if (amount >= 2){
//            pierce +=2;
//        }
//        // Level 3: +20% fire magic damage, +30% magic duration
//        if (amount >= 3 ) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DAMAGE, 0.2f, ModifierType.PERCENT, "ability:" + getID()));
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DURATION, 0.3f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        // Level 4: +30% magic size (cumulated with level 3 rules)
//        if (amount >= 4) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_SIZE, 0.3f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        // Level 5: increase damage to +70%
//        if (amount >= 5) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DAMAGE, 1f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        return mods;

    }


