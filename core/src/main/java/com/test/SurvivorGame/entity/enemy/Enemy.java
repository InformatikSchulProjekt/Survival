    package com.test.SurvivorGame.entity.enemy;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.math.Vector2;
    import com.test.SurvivorGame.entity.GameObject;
    import com.test.SurvivorGame.entity.Player;

    public class Enemy extends GameObject { //sollte später abstract parent von den enemies sein, grad zum Testen is aber da

        private static final float ENEMY_SIZE = 1f;

        private float maxStartHP = 1; //standard zuweisung für den start des Spieles

        private float currentMaxHP = maxStartHP; //wenn leben durch Items upgegraded werden können muss current skalierbar sein
        private float currentHP = maxStartHP; //current verändert sich, aber ist am start max


        private Player player;

        private Vector2 moveDirection;

        private float movementSpeed = 2f;

        private float damage = 1f;

        private boolean dead = false;

        public Enemy(float x, float y, Player player)
        {
            super(x, y, ENEMY_SIZE, ENEMY_SIZE,new Texture(Gdx.files.internal("Placeholder/enemy1PH.png")));
            this.player = player;
        }

        @Override
        public void update(float deltaTime)
        {
            moveDirection = player.getCenter().sub(this.getCenter()).nor().scl(movementSpeed * deltaTime);

            collider.setPosition(collider.getX() + moveDirection.x, collider.getY() + moveDirection.y);
        }

        public float getDamage()
        {
            return damage;
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
