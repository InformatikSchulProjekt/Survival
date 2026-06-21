package com.test.SurvivorGame.core.Rendering;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.test.SurvivorGame.entity.enemy.enemy1;
import com.test.SurvivorGame.world.World;

public class Renderer {

    private final Batch batch;
    private final Viewport viewport;

    private final World world;

    private final Texture playerTexture;
    private final Texture idle1;
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


    public Renderer(Batch batch, float screenWidth, float screenHeight, World world) {
        this.batch = batch;
        this.viewport = new FitViewport(screenWidth, screenHeight);

        this.world = world;

        this.playerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        TextureRegion[][] frames = TextureRegion.split(playerTexture, 64, 64);
        idle1 = new Texture(Gdx.files.internal("Player/idle 1.png"));
        idle2 = new Texture(Gdx.files.internal("Player/idle 2.png"));
        idle3 = new Texture(Gdx.files.internal("Player/idle 3.png"));
        idle4 = new Texture(Gdx.files.internal("Player/idle 4.png"));
        idleAnimation = new Animation<>(0.2f,
            new TextureRegion(idle1),
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



    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render(World world, float deltaTime) //hab ich jetzt so umgeändert, damit nun auch player aus world beutzt wird
    {                                                //und dass jetzt auch gegner gerendert werden
        ScreenUtils.clear(Color.BLUE);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        renderPlayer(world.getPlayer(), deltaTime);

        for(enemy1 enemy : world.getEnemies())
        {
            enemy.draw(batch);
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
    private void renderMap(Map map) {

    }
    public Viewport getViewport() {
        return viewport;
    }
    public void dispose() {
        idle1.dispose();
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
    }

}
