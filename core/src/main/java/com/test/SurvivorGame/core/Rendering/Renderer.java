package com.test.SurvivorGame.core.Rendering;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.maps.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.test.SurvivorGame.entity.enemy.Enemy1;
import com.test.SurvivorGame.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class Renderer {

    private final Batch batch;
    private final Viewport viewport;
    private final World world;

    private final Texture playerTexture;
    private final Texture idle2;
    private final Texture idle3;
    private final Texture idle4;
    private final Texture back1;
    private final Texture back2;
    private final Texture front1;
    private final Texture front2;
    private final Texture front3;
    private final Texture right1;
    private final Texture right2;
    private final Texture right3;
    private final Texture right4;
    private final Texture left1;
    private final Texture left2;
    private final Texture left3;
    private final Texture left4;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> backAnimation;
    private final Animation<TextureRegion> frontAnimation;
    private final Animation<TextureRegion> rightAnimation;
    private final Animation<TextureRegion> leftAnimation;
    private float playerAnimationTime = 0f;

    //Ab hier Enemy1
    private float enemy1AnimationTime = 0f;
    private final Texture enemy1Texture;
    private final Texture enemy1idle2;
    private final Texture enemy1idle3;
    private final Texture enemy1idle4;
    private final Texture enemy1back1;
    private final Texture enemy1back2;
    private final Texture enemy1front1;
    private final Texture enemy1front2;
    private final Texture enemy1front3;
    private final Texture enemy1right1;
    private final Texture enemy1right2;
    private final Texture enemy1right3;
    private final Texture enemy1right4;
    private final Texture enemy1left1;
    private final Texture enemy1left2;
    private final Texture enemy1left3;
    private final Texture enemy1left4;
    private final Animation<TextureRegion> enemy1idleAnimation;
    private final Animation<TextureRegion> enemy1backAnimation;
    private final Animation<TextureRegion> enemy1frontAnimation;
    private final Animation<TextureRegion> enemy1rightAnimation;
    private final Animation<TextureRegion> enemy1leftAnimation;








    public Renderer(Batch batch, float screenWidth, float screenHeight, World world) {
        this.batch = batch;
        this.viewport = new FillViewport(screenWidth, screenHeight);

        this.world = world;

        this.playerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        TextureRegion[][] frames = TextureRegion.split(playerTexture, 64, 64);
        idle2 = new Texture(Gdx.files.internal("Player/idle 2.png"));
        idle3 = new Texture(Gdx.files.internal("Player/idle 3.png"));
        idle4 = new Texture(Gdx.files.internal("Player/idle 4.png"));
        idleAnimation = new Animation<>(0.4f,
            new TextureRegion(idle2),
            new TextureRegion(idle3),
            new TextureRegion(idle4));
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        front1 = new Texture(Gdx.files.internal("Player/front 1.png"));
        front2 = new Texture(Gdx.files.internal("Player/front 2.png"));
        front3 = new Texture(Gdx.files.internal("Player/front 3.png"));
        frontAnimation = new Animation<>(0.2f,
            new TextureRegion(front1),
            new TextureRegion(front2),
            new TextureRegion(front3));
        frontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        back1 = new Texture(Gdx.files.internal("Player/back 1.png"));
        back2 = new Texture(Gdx.files.internal("Player/back 2.png"));
        backAnimation = new Animation<>(0.2f,
            new TextureRegion(back1),
            new TextureRegion(back2));
        backAnimation.setPlayMode(Animation.PlayMode.LOOP);
        right1 = new Texture(Gdx.files.internal("Player/right 1.png"));
        right2 = new Texture(Gdx.files.internal("Player/right 2.png"));
        right3 = new Texture(Gdx.files.internal("Player/right 3.png"));
        right4 = new Texture(Gdx.files.internal("Player/right 4.png"));
        rightAnimation = new Animation<>(0.2f,
            new TextureRegion(right1),
            new TextureRegion(right2),
            new TextureRegion(right3),
            new TextureRegion(right4));
        rightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        left1 = new Texture(Gdx.files.internal("Player/left 1.png"));
        left2 = new Texture(Gdx.files.internal("Player/left 2.png"));
        left3 = new Texture(Gdx.files.internal("Player/left 3.png"));
        left4 = new Texture(Gdx.files.internal("Player/left 4.png"));
        leftAnimation = new Animation<>(0.2f,
            new TextureRegion(left1),
            new TextureRegion(left2),
            new TextureRegion(left3),
            new TextureRegion(left4));
        leftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //Enemy1 ab hier
        this.enemy1Texture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        TextureRegion[][] enemy1frames = TextureRegion.split(enemy1Texture, 64, 64);
        enemy1idle2 = new Texture(Gdx.files.internal("Enemy1/idle 2.png"));
        enemy1idle3 = new Texture(Gdx.files.internal("Enemy1/idle 3.png"));
        enemy1idle4 = new Texture(Gdx.files.internal("Enemy1/idle 4.png"));
        enemy1idleAnimation = new Animation<>(0.4f,
            new TextureRegion(enemy1idle2),
            new TextureRegion(enemy1idle3),
            new TextureRegion(enemy1idle4));
        enemy1idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        enemy1front1 = new Texture(Gdx.files.internal("Enemy1/front 1.png"));
        enemy1front2 = new Texture(Gdx.files.internal("Enemy1/front 2.png"));
        enemy1front3 = new Texture(Gdx.files.internal("Enemy1/front 3.png"));
        enemy1frontAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1front1),
            new TextureRegion(enemy1front2),
            new TextureRegion(enemy1front3));
        enemy1frontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        enemy1back1 = new Texture(Gdx.files.internal("Enemy1/back 1.png"));
        enemy1back2 = new Texture(Gdx.files.internal("Enemy1/back 2.png"));
        enemy1backAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1back1),
            new TextureRegion(enemy1back2));
        enemy1backAnimation.setPlayMode(Animation.PlayMode.LOOP);
        enemy1right1 = new Texture(Gdx.files.internal("Enemy1/right 1.png"));
        enemy1right2 = new Texture(Gdx.files.internal("Enemy1/right 2.png"));
        enemy1right3 = new Texture(Gdx.files.internal("Enemy1/right 3.png"));
        enemy1right4 = new Texture(Gdx.files.internal("Enemy1/right 4.png"));
        enemy1rightAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1right1),
            new TextureRegion(enemy1right2),
            new TextureRegion(enemy1right3),
            new TextureRegion(enemy1right4));
        enemy1rightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        enemy1left1 = new Texture(Gdx.files.internal("Enemy1/left 1.png"));
        enemy1left2 = new Texture(Gdx.files.internal("Enemy1/left 2.png"));
        enemy1left3 = new Texture(Gdx.files.internal("Enemy1/left 3.png"));
        enemy1left4 = new Texture(Gdx.files.internal("Enemy1/left 4.png"));
        enemy1leftAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1left1),
            new TextureRegion(enemy1left2),
            new TextureRegion(enemy1left3),
            new TextureRegion(enemy1left4));
        enemy1leftAnimation.setPlayMode(Animation.PlayMode.LOOP);


    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render(GameMap map, World world, float deltaTime) //hab ich jetzt so umgeändert, damit nun auch player aus world beutzt wird
    {                                                //und dass jetzt auch gegner gerendert werden
        ScreenUtils.clear(Color.BLUE);

        //das updated die viewport kamera und sorgt dafür, dass der Spieler verfolgt wird davon
        viewport.apply();
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();

        float halfW = viewport.getWorldWidth() / 2f;
        float halfH = viewport.getWorldHeight() / 2f;

        float mapW = map.getWorldWidth();
        float mapH = map.getWorldHeight();

        float targetX = world.getPlayer().getX() + world.getPlayer().getWidth() / 2f;
        float targetY = world.getPlayer().getY() + world.getPlayer().getHeight() / 2f;

       //das fixiert die kamera zur map sodass man nicht rausschauen kann
        float camX = MathUtils.clamp(targetX, Math.min(halfW, mapW/2f), Math.max(halfW, mapW - halfW));
        float camY = MathUtils.clamp(targetY, Math.min(halfH, mapH/2f), Math.max(halfH, mapH - halfH));
        cam.position.set(camX, camY, 0f);
        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        renderMap(map);
        renderPlayer(world.getPlayer(), deltaTime);

        for (Enemy1 enemy1 : world.getEnemies1()) {
            renderEnemy1(enemy1, deltaTime);
        }


        batch.end();
    }

    private void renderPlayer(Player player, float deltaTime) {
        playerAnimationTime += deltaTime;

        Animation<TextureRegion> animation;

        if (!player.isMoving()) {
            animation = idleAnimation;
        } else {
            switch (player.getFacingDirection()) {
                case UP:
                    animation = frontAnimation;
                    break;
                case LEFT:
                    animation = leftAnimation;
                    break;
                case RIGHT:
                    animation = rightAnimation;
                    break;
                case DOWN:
                default:
                    animation = backAnimation;
                    break;
            }
        }
        TextureRegion currentFrame = animation.getKeyFrame(playerAnimationTime);


        batch.draw(
            currentFrame,
            player.getX(),
            player.getY(),
            player.getWidth(),
            player.getHeight()
        );
    }
    private void renderEnemy1(Enemy1 enemy1, float deltaTime) {
        enemy1AnimationTime += deltaTime;

        Animation<TextureRegion> animation;

        if (!enemy1.isMoving()) {
            animation = enemy1idleAnimation;
        } else {
            switch (enemy1.getFacingDirection()) {
                case UP:
                    animation = enemy1frontAnimation;
                    break;
                case LEFT:
                    animation = enemy1leftAnimation;
                    break;
                case RIGHT:
                    animation = enemy1rightAnimation;
                    break;
                case DOWN:
                default:
                    animation = enemy1backAnimation;
                    break;
            }
        }
        TextureRegion currentFrame = animation.getKeyFrame(enemy1AnimationTime);


        batch.draw(
            currentFrame,
            enemy1.getX(),
            enemy1.getY(),
            enemy1.getWidth(),
            enemy1.getHeight()
        );
    }

    private void renderMap(GameMap map) {
        batch.draw(
            map.getTexture(),
            0f,
            0f,
            map.getWorldWidth(),
            map.getWorldHeight()
        );
    }
    public Viewport getViewport() {
        return viewport;
    }
    public void dispose() {
        idle2.dispose();
        idle3.dispose();
        idle4.dispose();
        back1.dispose();
        back2.dispose();
        front1.dispose();
        front2.dispose();
        front3.dispose();
        right1.dispose();
        right2.dispose();
        right3.dispose();
        right4.dispose();
        left1.dispose();
        left2.dispose();
        left3.dispose();
        left4.dispose();
        enemy1idle2.dispose();
        enemy1idle3.dispose();
        enemy1idle4.dispose();
        enemy1back1.dispose();
        enemy1back2.dispose();
        enemy1front1.dispose();
        enemy1front2.dispose();
        enemy1front3.dispose();
        enemy1right1.dispose();
        enemy1right2.dispose();
        enemy1right3.dispose();
        enemy1right4.dispose();
        enemy1left1.dispose();
        enemy1left2.dispose();
        enemy1left3.dispose();
        enemy1left4.dispose();
    }
}
