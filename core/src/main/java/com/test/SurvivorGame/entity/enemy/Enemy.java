    package com.test.SurvivorGame.entity.enemy;

    import com.badlogic.gdx.math.Vector2;
    import com.test.SurvivorGame.entity.Entity;
    import com.test.SurvivorGame.entity.Player;
    import com.test.SurvivorGame.world.maps.GameMap;

    public class Enemy extends Entity { //sollte später abstract parent von den enemies sein, grad zum Testen is aber da

        private static final float ENEMY_SIZE = 1f;

        private static float maxStartHP = 1; //standard zuweisung für den start des Spieles

        private float currentHP = maxStartHP; //current verändert sich, aber ist am start max

        private Player player;

        private Vector2 moveDirection;

        private float damage = 1f;

        private boolean dead = false;

        public Enemy(float x, float y, Player player)
        {
            super(x, y, ENEMY_SIZE, ENEMY_SIZE, maxStartHP, 2f);
            this.player = player;
        }

        public Enemy(float x, float y, Player player,
                     float size, float maxHP,
                     float movementSpeed, float damage) {

            super(x, y, size, size, maxHP, movementSpeed);

            this.player = player;

            this.maxHP = maxHP;
            this.currentHP = maxHP;

            this.movementSpeed = movementSpeed;
            this.damage = damage;
        }


        public float getDamage()
        {
            return damage;
        }

        @Override
        public void update(float deltaTime, GameMap map)
        {
            moveDirection = player.getCenter().sub(this.getCenter()).nor().scl(movementSpeed * deltaTime);

            collider.setPosition(collider.getX() + moveDirection.x, collider.getY() + moveDirection.y);
        }

        public void takeDamage(float damage)
        {
            currentHP -= damage;
            System.out.println("Enemy bekommt schaden: " + damage);
            System.out.println("Enemy hat: " + currentHP + " Leben");

            if(currentHP <= 0)
            {
                currentHP = 0;
                dead = true;
            }
        }

        public boolean isDead()
        {
            return dead;
        }

    }
