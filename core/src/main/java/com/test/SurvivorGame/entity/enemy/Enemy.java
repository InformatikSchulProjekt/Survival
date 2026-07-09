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

        private final float damage;

        private final Player player;
        private final World world;
        private final PlayerState playerState;

        private Vector2 moveDirection;
        protected float animationTime = 0f;
        private float originalMovementSpeed;
        private float slowTimer = 2f;

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
        public void applySlow(float speedMultiplier, float duration) {
            originalMovementSpeed = movementSpeed;  // Save original
            movementSpeed = originalMovementSpeed * 0.8f;
            slowTimer = duration;
        }

        @Override
        public void update(float deltaTime, GameMap map)
        {
            // per-entity animation time so each enemy/boss animates independently
            animationTime += deltaTime;

            Vector2 moveDirection = player.getCenter().sub(this.getCenter()).nor().scl(movementSpeed * deltaTime);

            collider.setPosition(collider.getX() + moveDirection.x, collider.getY() + moveDirection.y);
            if (slowTimer <= 0) {
                movementSpeed = originalMovementSpeed;  // Restore when done
            }
        }

        public void takeDamage(float dmg)
        {
            dmg = calculateDamageTaken(dmg);
            currentHP -= dmg;
            System.out.println("Enemy: " + dmg+"dmg | hp: "+currentHP+"/"+maxHP);

            if(currentHP <= 0) { //=> Enemy Tod
                onDeath();
                dmg -= currentHP;
                // => Echter Schaden (Wenn 1hp genug war um zu killen z. B. soll dmg net trzm 10 sein
            }
            onDamage(dmg);
        }

        private void onDamage(float dmg) {
            // Life steal heal:
            float heal = dmg*playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL);
            if (heal > 0) {
                playerState.heal(dmg*playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL));
            }
        }

        private float calculateDamageTaken(float dmg) {
            if (shouldCrit()) {
                System.out.println("CRIT!"); // debug
                dmg *= 1 + playerState.getPlayerStats().getStat(StatScope.ALL, StatType.CRIT_MULTIPLIER);
            }

            return dmg;
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

        protected void onDeath() {
            dead = true;

            if (shouldSpawnChest())
            {
                System.out.println("Chest spawned!");
                world.addDrop(new ChestObject(getX(), getY(), player, getChestType()));
            }

            if (isBoss()) {
                world.setSurvivalTimePaused(false);
            }

            playerState.giveXP(getXPWorth());
        }

        private boolean shouldSpawnChest() {
            float chance = getChestChance();

            chance = Math.clamp(chance, 0f, 1f);

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

        protected boolean isBoss() {
            return false;
        }

    }
