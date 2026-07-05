    package com.test.SurvivorGame.entity.enemy;

    import com.badlogic.gdx.math.Vector2;
    import com.test.SurvivorGame.core.PlayerState;
    import com.test.SurvivorGame.core.stat.PlayerStats;
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

        public void takeDamage(float damage, PlayerState playerState)
        {
            currentHP -= damage;
            System.out.println("Enemy bekommt schaden: " + damage);
            System.out.println("Enemy hat: " + currentHP + " Leben");

            if(currentHP <= 0) { //=> Enemy Tod
                currentHP = 0;
                dead = true;
                if (shouldSpawnChest(playerState.getPlayerStats())) {
                    System.out.println("Chest spawned!"); // debug
                    world.addDrop(new ChestObject(getX(), getY(), player, ChestType.NORMAL));
                }
                playerState.giveXP(getXPWorth());
                damage -= currentHP; // => Effektiver Schaden (Wenn 1hp genug war um zu killen z. B. soll dmg net trzm 10 sein
            }
            playerState.heal(damage*playerState.getPlayerStats().getStat(StatScope.ALL, StatType.LIFE_STEAL));
        }

        private boolean shouldSpawnChest(PlayerStats playerStats) {
            float chestChance = playerStats.getStat(StatScope.ALL, StatType.CHEST_CHANCE);

            if (chestChance <= 0f) {
                return false;
            }

            if (chestChance > 1f) {
                chestChance = 1f;
            }

            return Math.random() < chestChance;
        }

        public boolean isDead()
        {
            return dead;
        }

        // animation time access for renderer
        public float getAnimationTime() {
            return animationTime;
        }

    }
