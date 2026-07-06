    package com.test.SurvivorGame.entity.enemy;

    import com.badlogic.gdx.math.Vector2;
    import com.test.SurvivorGame.core.PlayerState;
    import com.test.SurvivorGame.core.stat.StatScope;
    import com.test.SurvivorGame.core.stat.StatType;
    import com.test.SurvivorGame.entity.Entity;
    import com.test.SurvivorGame.entity.Player;
    import com.test.SurvivorGame.entity.drops.ChestObject;
    import com.test.SurvivorGame.entity.drops.ChestType;
    import com.test.SurvivorGame.world.World;
    import com.test.SurvivorGame.world.maps.GameMap;

    public class Enemy extends Entity { //sollte später abstract parent von den enemies sein, grad zum Testen is aber da

        private static float ENEMY_SIZE;

        private static float maxStartHP; //standard zuweisung für den start des Spieles
        private float currentHP = maxStartHP; //current verändert sich, aber ist am start max

        private float damage;

        private Player player;
        private World world;
        private PlayerState playerState;

        private Vector2 moveDirection;
        protected float animationTime = 0f;

        private boolean dead = false;

        public Enemy(float x, float y, World world,
                     float size, float maxHP,
                     float movementSpeed, float damage, float hpMultiplier)
        {
            super(x, y, size, size, maxHP*hpMultiplier, movementSpeed);

            maxHP *= hpMultiplier;

            this.world = world;
            this.player = world.getPlayer();
            this.playerState = player.getPlayerState();

            this.maxHP = maxHP;
            this.currentHP = maxHP;

            this.movementSpeed = movementSpeed;
            this.damage = damage;
        }

        public int getXPWorth() {
            return 2;
        }

        public float getDamage()
        {
            return damage;
        }

        @Override
        public void update(float deltaTime, GameMap map)
        {
            // per-entity animation time so each enemy/boss animates independently
            animationTime += deltaTime;

            moveDirection = player.getCenter().sub(this.getCenter()).nor().scl(movementSpeed * deltaTime);

            collider.setPosition(collider.getX() + moveDirection.x, collider.getY() + moveDirection.y);
        }

        public void takeDamage(float damage)
        {
            damage = calculateDamageTaken(damage);
            currentHP -= damage;
            System.out.println("Enemy: " + damage+"dmg | hp: "+currentHP+"/"+maxHP);

            if(currentHP <= 0) { //=> Enemy Tod
                onDeath();
                damage -= currentHP; // => Echter Schaden (Wenn 1hp genug war um zu killen z. B. soll dmg net trzm 10 sein
            }
            onDamage(damage);
        }

        private void onDamage(float damage) {
            // Life steal heal:
            float heal = damage*playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL);
            if (heal > 0) {
                playerState.heal(damage*playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL));
            }
        }

        private float calculateDamageTaken(float damage) {
            if (shouldCrit()) {
                System.out.println("CRIT!"); // debug
                damage *= 1 + playerState.getPlayerStats().getStat(StatScope.ALL, StatType.CRIT_MULTIPLIER);
            }

            return damage;
        }

        private boolean shouldCrit() {
            float critChance = playerState.getPlayerStats().getStat(StatScope.ALL, StatType.CRIT_CHANCE);

            if (critChance <= 0f) {
                return false;
            }

            if (critChance > 1f) {
                critChance = 1f;
            }

            return Math.random() < critChance;
        }

        private void onDeath() {
            dead = true;

            if (shouldSpawnChest())
            {
                System.out.println("Chest spawned!");
                world.addDrop(new ChestObject(getX(), getY(), player, getChestType()));
            }

            playerState.giveXP(getXPWorth());
        }

        private boolean shouldSpawnChest() {
            float chance = getChestChance();

            chance = Math.max(0f, Math.min(1f, chance));

            return Math.random() < chance;
        }

        public boolean isDead()
        {
            return dead;
        }

        // animation time access for renderer
        public float getAnimationTime() {
            return animationTime;
        }

        protected ChestType getChestType() {
            return ChestType.NORMAL;
        }

        protected float getChestChance() {
            return playerState.getPlayerStats().getStat(
                StatScope.ALL,
                StatType.CHEST_CHANCE
            );
        }

    }
