package com.test.SurvivorGame.core.rendering;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.entity.drops.ChestObject;
import com.test.SurvivorGame.entity.drops.ChestType;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.screen.GameState;
import com.test.SurvivorGame.screen.hud.*;
import com.test.SurvivorGame.world.maps.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Fireball;
import com.test.SurvivorGame.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.entity.enemy.Enemy;

public class Renderer {

    private final Batch batch;
    private final Viewport viewport;
    private final World world;
    private final ShapeRenderer shapeRenderer;
    private final PlayerData playerData;
    private final PauseMenuUI pauseMenu;
    private final LevelUpUI levelUpUI;
    private final ChestUI chestUI;
    private final SettingsUI settingsUI;
    private final MapFinishedUI mapFinishedUI;

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
    //Ab hier mancer
    private final Texture pyromancerTexture;
    private final Texture pyromanceridle2;
    private final Texture pyromanceridle3;
    private final Texture pyromanceridle4;
    private final Texture pyromancerback1;
    private final Texture pyromancerback2;
    private final Texture pyromancerback3;
    private final Texture pyromancerback4;
    private final Texture pyromancerback5;

    private final Texture pyromancerfront1;
    private final Texture pyromancerfront2;
    private final Texture pyromancerfront3;
    private final Texture pyromancerfront4;
    private final Texture pyromancerfront5;

    private final Texture pyromancerright1;
    private final Texture pyromancerright2;
    private final Texture pyromancerright3;
    private final Texture pyromancerright4;
    private final Texture pyromancerright5;
    private final Texture pyromancerright6;
    private final Texture pyromancerright7;
    private final Texture pyromancerright8;
    private final Texture pyromancerright9;

    private final Texture pyromancerleft1;
    private final Texture pyromancerleft2;
    private final Texture pyromancerleft3;
    private final Texture pyromancerleft4;
    private final Texture pyromancerleft5;
    private final Texture pyromancerleft6;
    private final Texture pyromancerleft7;
    private final Texture pyromancerleft8;
    private final Texture pyromancerleft9;

    private final Animation<TextureRegion> pyromanceridleAnimation;
    private final Animation<TextureRegion> pyromancerbackAnimation;
    private final Animation<TextureRegion> pyromancerfrontAnimation;
    private final Animation<TextureRegion> pyromancerrightAnimation;
    private final Animation<TextureRegion> pyromancerleftAnimation;
    private float pyromancerAnimationTime = 0f;

    private final Texture aeromancerTexture;
    private final Texture aeromanceridle2;
    private final Texture aeromanceridle3;
    private final Texture aeromanceridle4;

    private final Texture aeromancerback1;
    private final Texture aeromancerback2;
    private final Texture aeromancerback3;
    private final Texture aeromancerback4;
    private final Texture aeromancerback5;

    private final Texture aeromancerfront1;
    private final Texture aeromancerfront2;
    private final Texture aeromancerfront3;
    private final Texture aeromancerfront4;
    private final Texture aeromancerfront5;

    private final Texture aeromancerright1;
    private final Texture aeromancerright2;
    private final Texture aeromancerright3;
    private final Texture aeromancerright4;
    private final Texture aeromancerright5;
    private final Texture aeromancerright6;
    private final Texture aeromancerright7;
    private final Texture aeromancerright8;
    private final Texture aeromancerright9;
    private final Texture aeromancerright10;
    private final Texture aeromancerright11;
    private final Texture aeromancerright12;
    private final Texture aeromancerright13;
    private final Texture aeromancerright14;
    private final Texture aeromancerright15;
    private final Texture aeromancerright16;
    private final Texture aeromancerright17;
    private final Texture aeromancerright18;
    private final Texture aeromancerright19;
    private final Texture aeromancerright20;
    private final Texture aeromancerright21;
    private final Texture aeromancerright22;
    private final Texture aeromancerright23;
    private final Texture aeromancerright24;
    private final Texture aeromancerright25;

    private final Texture aeromancerleft1;
    private final Texture aeromancerleft2;
    private final Texture aeromancerleft3;
    private final Texture aeromancerleft4;
    private final Texture aeromancerleft5;
    private final Texture aeromancerleft6;
    private final Texture aeromancerleft7;
    private final Texture aeromancerleft8;
    private final Texture aeromancerleft9;
    private final Texture aeromancerleft10;
    private final Texture aeromancerleft11;
    private final Texture aeromancerleft12;
    private final Texture aeromancerleft13;
    private final Texture aeromancerleft14;
    private final Texture aeromancerleft15;
    private final Texture aeromancerleft16;
    private final Texture aeromancerleft17;
    private final Texture aeromancerleft18;
    private final Texture aeromancerleft19;
    private final Texture aeromancerleft20;
    private final Texture aeromancerleft21;
    private final Texture aeromancerleft22;
    private final Texture aeromancerleft23;
    private final Texture aeromancerleft24;
    private final Texture aeromancerleft25;

    private float aeromancerAnimationTime = 0f;

    private final Animation<TextureRegion> aeromanceridleAnimation;
    private final Animation<TextureRegion> aeromancerbackAnimation;
    private final Animation<TextureRegion> aeromancerfrontAnimation;
    private final Animation<TextureRegion> aeromancerrightAnimation;
    private final Animation<TextureRegion> aeromancerleftAnimation;

    private final Texture hydromancerTexture;
    private final Texture hydromanceridle2;
    private final Texture hydromanceridle3;
    private final Texture hydromanceridle4;

    private final Texture hydromancerback1;
    private final Texture hydromancerback2;
    private final Texture hydromancerback3;
    private final Texture hydromancerback4;
    private final Texture hydromancerback5;

    private final Texture hydromancerfront1;
    private final Texture hydromancerfront2;
    private final Texture hydromancerfront3;
    private final Texture hydromancerfront4;
    private final Texture hydromancerfront5;

    private final Texture hydromancerright1;
    private final Texture hydromancerright2;
    private final Texture hydromancerright3;
    private final Texture hydromancerright4;
    private final Texture hydromancerright5;
    private final Texture hydromancerright6;
    private final Texture hydromancerright7;
    private final Texture hydromancerright8;
    private final Texture hydromancerright9;

    private final Texture hydromancerleft1;
    private final Texture hydromancerleft2;
    private final Texture hydromancerleft3;
    private final Texture hydromancerleft4;
    private final Texture hydromancerleft5;
    private final Texture hydromancerleft6;
    private final Texture hydromancerleft7;
    private final Texture hydromancerleft8;
    private final Texture hydromancerleft9;

    private float hydromancerAnimationTime = 0f;

    private final Animation<TextureRegion> hydromanceridleAnimation;
    private final Animation<TextureRegion> hydromancerbackAnimation;
    private final Animation<TextureRegion> hydromancerfrontAnimation;
    private final Animation<TextureRegion> hydromancerrightAnimation;
    private final Animation<TextureRegion> hydromancerleftAnimation;

    private final Texture geomancerTexture;
    private final Texture geomanceridle2;
    private final Texture geomanceridle3;
    private final Texture geomanceridle4;

    private final Texture geomancerback1;
    private final Texture geomancerback2;
    private final Texture geomancerback3;
    private final Texture geomancerback4;
    private final Texture geomancerback5;

    private final Texture geomancerfront1;
    private final Texture geomancerfront2;
    private final Texture geomancerfront3;
    private final Texture geomancerfront4;
    private final Texture geomancerfront5;

    private final Texture geomancerright1;
    private final Texture geomancerright2;
    private final Texture geomancerright3;
    private final Texture geomancerright4;
    private final Texture geomancerright5;
    private final Texture geomancerright6;
    private final Texture geomancerright7;
    private final Texture geomancerright8;
    private final Texture geomancerright9;

    private final Texture geomancerleft1;
    private final Texture geomancerleft2;
    private final Texture geomancerleft3;
    private final Texture geomancerleft4;
    private final Texture geomancerleft5;
    private final Texture geomancerleft6;
    private final Texture geomancerleft7;
    private final Texture geomancerleft8;
    private final Texture geomancerleft9;

    private float geomancerAnimationTime = 0f;

    private final Animation<TextureRegion> geomanceridleAnimation;
    private final Animation<TextureRegion> geomancerbackAnimation;
    private final Animation<TextureRegion> geomancerfrontAnimation;
    private final Animation<TextureRegion> geomancerrightAnimation;
    private final Animation<TextureRegion> geomancerleftAnimation;
    //Ab hier Enemy1
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
    private final Texture enemy1right5;
    private final Texture enemy1left1;
    private final Texture enemy1left2;
    private final Texture enemy1left3;
    private final Texture enemy1left4;
    private final Texture enemy1left5;
    private final Animation<TextureRegion> enemy1idleAnimation;
    private final Animation<TextureRegion> enemy1backAnimation;
    private final Animation<TextureRegion> enemy1frontAnimation;
    private final Animation<TextureRegion> enemy1rightAnimation;
    private final Animation<TextureRegion> enemy1leftAnimation;
    private final Texture bossTexture;
    private final Texture bossidle2;
    private final Texture bossidle3;
    private final Texture bossback1;
    private final Texture bossback2;
    private final Texture bossfront1;
    private final Texture bossfront2;
    private final Texture bossright1;
    private final Texture bossright2;
    private final Texture bossleft1;
    private final Texture bossleft2;
    private final Animation<TextureRegion> bossidleAnimation;
    private final Animation<TextureRegion> bossbackAnimation;
    private final Animation<TextureRegion> bossfrontAnimation;
    private final Animation<TextureRegion> bossrightAnimation;
    private final Animation<TextureRegion> bossleftAnimation;

    // Fireball
    private final Texture fireball0;
    private final Texture fireball1;
    private final Texture fireball2;
    private final Texture fireball3;
    private final Texture fireball4;
    private final Texture fireball5;
    private final Texture fireball6;
    private final Texture fireball7;
    private final Texture fireball8;
    private final Texture fireball9;
    private final Texture fireball10;
    private final Texture fireball11;
    private final Animation<TextureRegion> fireballMovementAnimation;
    private final Animation<TextureRegion> fireballExplosionAnimation;
    private final HUDRenderer hud;


    //FireArrow
    private final Texture firearrow0;
    private final Texture firearrow1;
    private final Texture firearrow2;
    private final Texture firearrow3;
    private final Animation<TextureRegion> fireArrowAnimation;

    //WaterBlast
    private final Texture waterblast0;
    private final Texture waterblast1;
    private final Texture waterblast2;
    private final Texture waterblast3;
    private final Texture waterblast4;
    private final Animation<TextureRegion> waterBlastAnimation;

    //Chest
    private final Texture normalChestTexture;
    private final Texture normalChest1;
    private final Texture normalChest2;
    private final Texture normalChest3;
    private final Texture normalChest4;
    private final Texture normalChest5;
    private final Animation<TextureRegion> normalChestClosedAnimation;
    private final Animation<TextureRegion> normalChestOpeningAnimation;
    private final Animation<TextureRegion> normalChestOpenedAnimation;
    //legendaryChest
    private final Texture legendaryChestTexture;
    private final Texture legendaryChest1;
    private final Texture legendaryChest2;
    private final Texture legendaryChest3;
    private final Texture legendaryChest4;
    private final Texture legendaryChest5;
    private final Animation<TextureRegion> legendaryChestOpenedAnimation;
    private final Animation<TextureRegion> legendaryChestOpeningAnimation;
    private final Animation<TextureRegion> legendaryChestClosedAnimation;

    public Renderer(Batch batch,
                    float screenWidth,
                    float screenHeight,
                    World world,
                    ShapeRenderer shapeRenderer,
                    PlayerData playerData,
                    PauseMenuUI pauseMenu,
                    LevelUpUI levelUpUI,
                    ChestUI chestUI,
                    SettingsUI settingsUI, MapFinishedUI mapFinishedUI) {
        this.batch = batch;
        this.viewport = new FillViewport(screenWidth, screenHeight);
        this.playerData = playerData;

        this.world = world;
        this.shapeRenderer = shapeRenderer; // für debug der collider
        this.hud = new HUDRenderer(batch, shapeRenderer);
        this.pauseMenu = pauseMenu;
        this.levelUpUI = levelUpUI;
        this.chestUI = chestUI;
        this.settingsUI = settingsUI;
        this.mapFinishedUI = mapFinishedUI;

        this.normalChestTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        TextureRegion[][] normalChestframes = TextureRegion.split(normalChestTexture, 64, 64);
        normalChest1 = new Texture(Gdx.files.internal("Chest/chest1.png"));
        normalChestClosedAnimation = new Animation<>(0.4f,
            new TextureRegion(normalChest1));
        normalChestClosedAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        normalChest2 = new Texture(Gdx.files.internal("Chest/chest2.png"));
        normalChest3 = new Texture(Gdx.files.internal("Chest/chest3.png"));
        normalChest4 = new Texture(Gdx.files.internal("Chest/chest4.png"));
        normalChest5 = new Texture(Gdx.files.internal("Chest/chest5.png"));
        normalChestOpeningAnimation = new Animation<>(0.2f,
            new TextureRegion(normalChest2),
            new TextureRegion(normalChest3),
            new TextureRegion(normalChest4),
            new TextureRegion(normalChest5));
        normalChestOpeningAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        normalChestOpenedAnimation = new Animation<>(0.4f,
            new TextureRegion(normalChest5));
        normalChestOpenedAnimation.setPlayMode(Animation.PlayMode.NORMAL);


        //LegendaryChest
        this.legendaryChestTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        TextureRegion[][] legendaryChestframes = TextureRegion.split(legendaryChestTexture, 64, 64);
        legendaryChest1 = new Texture(Gdx.files.internal("Chest/BossChest1.png"));
        legendaryChestClosedAnimation = new Animation<>(0.4f,
            new TextureRegion(legendaryChest1));
        normalChestClosedAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        legendaryChest2 = new Texture(Gdx.files.internal("Chest/BossChest2.png"));
        legendaryChest3 = new Texture(Gdx.files.internal("Chest/BossChest3.png"));
        legendaryChest4 = new Texture(Gdx.files.internal("Chest/BossChest4.png"));
        legendaryChest5 = new Texture(Gdx.files.internal("Chest/BossChest5.png"));
        legendaryChestOpeningAnimation = new Animation<>(0.2f,
            new TextureRegion(legendaryChest2),
            new TextureRegion(legendaryChest3),
            new TextureRegion(legendaryChest4),
            new TextureRegion(legendaryChest5));
        legendaryChestOpeningAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        legendaryChestOpenedAnimation = new Animation<>(0.4f,
            new TextureRegion(legendaryChest5));
        legendaryChestOpenedAnimation.setPlayMode(Animation.PlayMode.NORMAL);

//old Player ...
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
        //pyromancer ab hier
        this.pyromancerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
        pyromanceridle2 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c1.png"));
        pyromanceridle3 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c1.png"));
        pyromanceridle4 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c1.png"));
        pyromanceridleAnimation = new Animation<>(0.4f,
            new TextureRegion(pyromanceridle2),
            new TextureRegion(pyromanceridle3),
            new TextureRegion(pyromanceridle4));
        pyromanceridleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        pyromancerfront1 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r1_c2.png"));
        pyromancerfront2 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r1_c3.png"));
        pyromancerfront3 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r1_c4.png"));
        pyromancerfront4 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r1_c5.png"));
        pyromancerfront5 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r1_c2.png"));

        pyromancerfrontAnimation = new Animation<>(0.2f,
            new TextureRegion(pyromancerfront1),
            new TextureRegion(pyromancerfront2),
            new TextureRegion(pyromancerfront3),
            new TextureRegion(pyromancerfront4),
            new TextureRegion(pyromancerfront5));
        pyromancerfrontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        pyromancerback1 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c1.png"));
        pyromancerback2 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c2.png"));
        pyromancerback3 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c3.png"));
        pyromancerback4 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c4.png"));
        pyromancerback5 = new Texture(Gdx.files.internal("Mancer/Pyromancer/sprite_r4_c5.png"));

        pyromancerbackAnimation = new Animation<>(0.2f,
            new TextureRegion(pyromancerback1),
            new TextureRegion(pyromancerback2),
            new TextureRegion(pyromancerback3),
            new TextureRegion(pyromancerback4),
            new TextureRegion(pyromancerback5));
        pyromancerbackAnimation.setPlayMode(Animation.PlayMode.LOOP);

        pyromancerright1 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r1_c1.png"));
        pyromancerright2 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r1_c2.png"));
        pyromancerright3 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r1_c3.png"));
        pyromancerright4 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r1_c4.png"));
        pyromancerright5 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r2_c1.png"));
        pyromancerright6 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r2_c2.png"));
        pyromancerright7 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r2_c3.png"));
        pyromancerright8 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r2_c4.png"));
        pyromancerright9 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Right/sprite_r2_c5.png"));

        pyromancerrightAnimation = new Animation<>(0.08f,
            new TextureRegion(pyromancerright1),
            new TextureRegion(pyromancerright2),
            new TextureRegion(pyromancerright3),
            new TextureRegion(pyromancerright4),
            new TextureRegion(pyromancerright5),
            new TextureRegion(pyromancerright6),
            new TextureRegion(pyromancerright7),
            new TextureRegion(pyromancerright8),
            new TextureRegion(pyromancerright9));
        pyromancerrightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        pyromancerleft1 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r1_c1.png"));
        pyromancerleft2 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r1_c2.png"));
        pyromancerleft3 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r1_c3.png"));
        pyromancerleft4 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r1_c4.png"));
        pyromancerleft5 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r2_c1.png"));
        pyromancerleft6 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r2_c2.png"));
        pyromancerleft7 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r2_c3.png"));
        pyromancerleft8 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r2_c4.png"));
        pyromancerleft9 = new Texture(Gdx.files.internal("Mancer/Pyromancer/Left/sprite_r2_c5.png"));


        pyromancerleftAnimation = new Animation<>(0.08f,
            new TextureRegion(pyromancerleft1),
            new TextureRegion(pyromancerleft2),
            new TextureRegion(pyromancerleft3),
            new TextureRegion(pyromancerleft4),
            new TextureRegion(pyromancerleft5),
            new TextureRegion(pyromancerleft6),
            new TextureRegion(pyromancerleft7),
            new TextureRegion(pyromancerleft8),
            new TextureRegion(pyromancerleft9));
        pyromancerleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //Ab hier aeromancer
        // ===================== AEROMANCER =====================
        this.aeromancerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));

        aeromanceridle2 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c1.png"));
        aeromanceridle3 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c1.png"));
        aeromanceridle4 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c1.png"));

        aeromanceridleAnimation = new Animation<>(0.4f,
            new TextureRegion(aeromanceridle2),
            new TextureRegion(aeromanceridle3),
            new TextureRegion(aeromanceridle4));
        aeromanceridleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        aeromancerfront1 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r1_c2.png"));
        aeromancerfront2 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r1_c3.png"));
        aeromancerfront3 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r1_c4.png"));
        aeromancerfront4 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r1_c5.png"));
        aeromancerfront5 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r1_c2.png"));

        aeromancerfrontAnimation = new Animation<>(0.2f,
            new TextureRegion(aeromancerfront1),
            new TextureRegion(aeromancerfront2),
            new TextureRegion(aeromancerfront3),
            new TextureRegion(aeromancerfront4),
            new TextureRegion(aeromancerfront5));
        aeromancerfrontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        aeromancerback1 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c1.png"));
        aeromancerback2 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c2.png"));
        aeromancerback3 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c3.png"));
        aeromancerback4 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c4.png"));
        aeromancerback5 = new Texture(Gdx.files.internal("Mancer/Aeromancer/sprite_r4_c5.png"));

        aeromancerbackAnimation = new Animation<>(0.2f,
            new TextureRegion(aeromancerback1),
            new TextureRegion(aeromancerback2),
            new TextureRegion(aeromancerback3),
            new TextureRegion(aeromancerback4),
            new TextureRegion(aeromancerback5));
        aeromancerbackAnimation.setPlayMode(Animation.PlayMode.LOOP);

        aeromancerright1 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r1_c1.png"));
        aeromancerright2 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r1_c2.png"));
        aeromancerright3 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r1_c3.png"));
        aeromancerright4 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r1_c4.png"));
        aeromancerright5 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r1_c5.png"));
        aeromancerright6 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r2_c1.png"));
        aeromancerright7 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r2_c2.png"));
        aeromancerright8 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r2_c3.png"));
        aeromancerright9 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r2_c4.png"));
        aeromancerright10 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r2_c5.png"));
        aeromancerright11 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r3_c1.png"));
        aeromancerright12 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r3_c2.png"));
        aeromancerright13 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r3_c3.png"));
        aeromancerright14 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r3_c4.png"));
        aeromancerright15 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r3_c5.png"));
        aeromancerright16 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r4_c1.png"));
        aeromancerright17 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r4_c2.png"));
        aeromancerright18 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r4_c3.png"));
        aeromancerright19 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r4_c4.png"));
        aeromancerright20 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r4_c5.png"));
        aeromancerright21 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r5_c1.png"));
        aeromancerright22 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r5_c2.png"));
        aeromancerright23 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r5_c3.png"));
        aeromancerright24 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r5_c4.png"));
        aeromancerright25 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Right/sprite_r5_c5.png"));

        aeromancerrightAnimation = new Animation<>(0.06f,
            new TextureRegion(aeromancerright1),
            new TextureRegion(aeromancerright2),
            new TextureRegion(aeromancerright3),
            new TextureRegion(aeromancerright4),
            new TextureRegion(aeromancerright5),
            new TextureRegion(aeromancerright6),
            new TextureRegion(aeromancerright7),
            new TextureRegion(aeromancerright8),
            new TextureRegion(aeromancerright9),
            new TextureRegion(aeromancerright10),
            new TextureRegion(aeromancerright11),
            new TextureRegion(aeromancerright12),
            new TextureRegion(aeromancerright13),
            new TextureRegion(aeromancerright14),
            new TextureRegion(aeromancerright15),
            new TextureRegion(aeromancerright16),
            new TextureRegion(aeromancerright17),
            new TextureRegion(aeromancerright18),
            new TextureRegion(aeromancerright19),
            new TextureRegion(aeromancerright20),
            new TextureRegion(aeromancerright21),
            new TextureRegion(aeromancerright22),
            new TextureRegion(aeromancerright23),
            new TextureRegion(aeromancerright24),
            new TextureRegion(aeromancerright25));
        aeromancerrightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        aeromancerleft1 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r1_c1.png"));
        aeromancerleft2 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r1_c2.png"));
        aeromancerleft3 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r1_c3.png"));
        aeromancerleft4 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r1_c4.png"));
        aeromancerleft5 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r1_c5.png"));
        aeromancerleft6 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r2_c1.png"));
        aeromancerleft7 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r2_c2.png"));
        aeromancerleft8 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r2_c3.png"));
        aeromancerleft9 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r2_c4.png"));
        aeromancerleft10 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r2_c5.png"));
        aeromancerleft11 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r3_c1.png"));
        aeromancerleft12 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r3_c2.png"));
        aeromancerleft13 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r3_c3.png"));
        aeromancerleft14 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r3_c4.png"));
        aeromancerleft15 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r3_c5.png"));
        aeromancerleft16 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r4_c1.png"));
        aeromancerleft17 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r4_c2.png"));
        aeromancerleft18 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r4_c3.png"));
        aeromancerleft19 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r4_c4.png"));
        aeromancerleft20 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r4_c5.png"));
        aeromancerleft21 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r5_c1.png"));
        aeromancerleft22 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r5_c2.png"));
        aeromancerleft23 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r5_c3.png"));
        aeromancerleft24 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r5_c4.png"));
        aeromancerleft25 = new Texture(Gdx.files.internal("Mancer/Aeromancer/Left/sprite_r5_c5.png"));

        aeromancerleftAnimation = new Animation<>(0.06f,
            new TextureRegion(aeromancerleft1),
            new TextureRegion(aeromancerleft2),
            new TextureRegion(aeromancerleft3),
            new TextureRegion(aeromancerleft4),
            new TextureRegion(aeromancerleft5),
            new TextureRegion(aeromancerleft6),
            new TextureRegion(aeromancerleft7),
            new TextureRegion(aeromancerleft8),
            new TextureRegion(aeromancerleft9),
            new TextureRegion(aeromancerleft10),
            new TextureRegion(aeromancerleft11),
            new TextureRegion(aeromancerleft12),
            new TextureRegion(aeromancerleft13),
            new TextureRegion(aeromancerleft14),
            new TextureRegion(aeromancerleft15),
            new TextureRegion(aeromancerleft16),
            new TextureRegion(aeromancerleft17),
            new TextureRegion(aeromancerleft18),
            new TextureRegion(aeromancerleft19),
            new TextureRegion(aeromancerleft20),
            new TextureRegion(aeromancerleft21),
            new TextureRegion(aeromancerleft22),
            new TextureRegion(aeromancerleft23),
            new TextureRegion(aeromancerleft24),
            new TextureRegion(aeromancerleft25));
        aeromancerleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //hydromancer ab hier

        this.hydromancerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));

        hydromanceridle2 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c1.png"));
        hydromanceridle3 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c1.png"));
        hydromanceridle4 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c1.png"));

        hydromanceridleAnimation = new Animation<>(0.4f,
            new TextureRegion(hydromanceridle2),
            new TextureRegion(hydromanceridle3),
            new TextureRegion(hydromanceridle4));
        hydromanceridleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hydromancerfront1 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r1_c2.png"));
        hydromancerfront2 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r1_c3.png"));
        hydromancerfront3 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r1_c4.png"));
        hydromancerfront4 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r1_c5.png"));
        hydromancerfront5 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r1_c2.png"));

        hydromancerfrontAnimation = new Animation<>(0.2f,
            new TextureRegion(hydromancerfront1),
            new TextureRegion(hydromancerfront2),
            new TextureRegion(hydromancerfront3),
            new TextureRegion(hydromancerfront4),
            new TextureRegion(hydromancerfront5));
        hydromancerfrontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hydromancerback1 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c1.png"));
        hydromancerback2 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c2.png"));
        hydromancerback3 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c3.png"));
        hydromancerback4 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c4.png"));
        hydromancerback5 = new Texture(Gdx.files.internal("Mancer/Aquamancer/sprite_r4_c5.png"));


        hydromancerbackAnimation = new Animation<>(0.2f,
            new TextureRegion(hydromancerback1),
            new TextureRegion(hydromancerback2),
            new TextureRegion(hydromancerback3),
            new TextureRegion(hydromancerback4),
            new TextureRegion(hydromancerback5));
        hydromancerbackAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hydromancerright1 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r1_c1.png"));
        hydromancerright2 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r1_c2.png"));
        hydromancerright3 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r1_c3.png"));
        hydromancerright4 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r1_c4.png"));
        hydromancerright5 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r2_c1.png"));
        hydromancerright6 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r2_c2.png"));
        hydromancerright7 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r2_c3.png"));
        hydromancerright8 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r2_c4.png"));
        hydromancerright9 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Right/sprite_r2_c5.png"));


        hydromancerrightAnimation = new Animation<>(0.08f,
            new TextureRegion(hydromancerright1),
            new TextureRegion(hydromancerright2),
            new TextureRegion(hydromancerright3),
            new TextureRegion(hydromancerright4),
            new TextureRegion(hydromancerright5),
            new TextureRegion(hydromancerright6),
            new TextureRegion(hydromancerright7),
            new TextureRegion(hydromancerright8),
            new TextureRegion(hydromancerright9));
        hydromancerrightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hydromancerleft1 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r1_c1.png"));
        hydromancerleft2 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r1_c2.png"));
        hydromancerleft3 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r1_c3.png"));
        hydromancerleft4 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r1_c4.png"));
        hydromancerleft5 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r2_c1.png"));
        hydromancerleft6 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r2_c2.png"));
        hydromancerleft7 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r2_c3.png"));
        hydromancerleft8 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r2_c4.png"));
        hydromancerleft9 = new Texture(Gdx.files.internal("Mancer/Aquamancer/Left/sprite_r2_c5.png"));

        hydromancerleftAnimation = new Animation<>(0.08f,
            new TextureRegion(hydromancerleft1),
            new TextureRegion(hydromancerleft2),
            new TextureRegion(hydromancerleft3),
            new TextureRegion(hydromancerleft4),
            new TextureRegion(hydromancerleft5),
            new TextureRegion(hydromancerleft6),
            new TextureRegion(hydromancerleft7),
            new TextureRegion(hydromancerleft8),
            new TextureRegion(hydromancerleft9));
        hydromancerleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //geomancer ab hier

        this.geomancerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));

        geomanceridle2 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c1.png"));
        geomanceridle3 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c1.png"));
        geomanceridle4 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c1.png"));

        geomanceridleAnimation = new Animation<>(0.4f,
            new TextureRegion(geomanceridle2),
            new TextureRegion(geomanceridle3),
            new TextureRegion(geomanceridle4));
        geomanceridleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        geomancerfront1 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r1_c2.png"));
        geomancerfront2 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r1_c3.png"));
        geomancerfront3 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r1_c4.png"));
        geomancerfront4 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r1_c5.png"));
        geomancerfront5 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r1_c2.png"));

        geomancerfrontAnimation = new Animation<>(0.2f,
            new TextureRegion(geomancerfront1),
            new TextureRegion(geomancerfront2),
            new TextureRegion(geomancerfront3),
            new TextureRegion(geomancerfront4),
            new TextureRegion(geomancerfront5));
        geomancerfrontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        geomancerback1 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c1.png"));
        geomancerback2 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c2.png"));
        geomancerback3 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c3.png"));
        geomancerback4 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c4.png"));
        geomancerback5 = new Texture(Gdx.files.internal("Mancer/Geomancer/sprite_r4_c5.png"));

        geomancerbackAnimation = new Animation<>(0.2f,
            new TextureRegion(geomancerback1),
            new TextureRegion(geomancerback2),
            new TextureRegion(geomancerback3),
            new TextureRegion(geomancerback4),
            new TextureRegion(geomancerback5));
        geomancerbackAnimation.setPlayMode(Animation.PlayMode.LOOP);

        geomancerright1 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r1_c1.png"));
        geomancerright2 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r1_c2.png"));
        geomancerright3 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r1_c3.png"));
        geomancerright4 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r1_c4.png"));
        geomancerright5 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r2_c1.png"));
        geomancerright6 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r2_c2.png"));
        geomancerright7 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r2_c3.png"));
        geomancerright8 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r2_c4.png"));
        geomancerright9 = new Texture(Gdx.files.internal("Mancer/Geomancer/Right/sprite_r2_c5.png"));

        geomancerrightAnimation = new Animation<>(0.08f,
            new TextureRegion(geomancerright1),
            new TextureRegion(geomancerright2),
            new TextureRegion(geomancerright3),
            new TextureRegion(geomancerright4),
            new TextureRegion(geomancerright5),
            new TextureRegion(geomancerright6),
            new TextureRegion(geomancerright7),
            new TextureRegion(geomancerright8),
            new TextureRegion(geomancerright9));
        geomancerrightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        geomancerleft1 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r1_c1.png"));
        geomancerleft2 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r1_c2.png"));
        geomancerleft3 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r1_c3.png"));
        geomancerleft4 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r1_c4.png"));
        geomancerleft5 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r2_c1.png"));
        geomancerleft6 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r2_c2.png"));
        geomancerleft7 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r2_c3.png"));
        geomancerleft8 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r2_c4.png"));
        geomancerleft9 = new Texture(Gdx.files.internal("Mancer/Geomancer/Left/sprite_r2_c5.png"));

        geomancerleftAnimation = new Animation<>(0.08f,
            new TextureRegion(geomancerleft1),
            new TextureRegion(geomancerleft2),
            new TextureRegion(geomancerleft3),
            new TextureRegion(geomancerleft4),
            new TextureRegion(geomancerleft5),
            new TextureRegion(geomancerleft6),
            new TextureRegion(geomancerleft7),
            new TextureRegion(geomancerleft8),
            new TextureRegion(geomancerleft9));
        geomancerleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

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
        enemy1right5 = new Texture(Gdx.files.internal("Enemy1/right 5.png"));
        enemy1rightAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1right1),
            new TextureRegion(enemy1right2),
            new TextureRegion(enemy1right3),
            new TextureRegion(enemy1right4),
            new TextureRegion(enemy1right5));
        enemy1rightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        enemy1left1 = new Texture(Gdx.files.internal("Enemy1/left 1.png"));
        enemy1left2 = new Texture(Gdx.files.internal("Enemy1/left 2.png"));
        enemy1left3 = new Texture(Gdx.files.internal("Enemy1/left 3.png"));
        enemy1left4 = new Texture(Gdx.files.internal("Enemy1/left 4.png"));
        enemy1left5 = new Texture(Gdx.files.internal("Enemy1/left 5.png"));
        enemy1leftAnimation = new Animation<>(0.2f,
            new TextureRegion(enemy1left1),
            new TextureRegion(enemy1left2),
            new TextureRegion(enemy1left3),
            new TextureRegion(enemy1left4),
            new TextureRegion(enemy1left5));
        enemy1leftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        //ab hier boss
        this.bossTexture = new Texture(Gdx.files.internal("Boss/BossIdle1.png"));
        TextureRegion[][] bossframes = TextureRegion.split(bossTexture, 64, 64);
        bossidle2 = new Texture(Gdx.files.internal("Boss/BossIdle1.png"));
        bossidle3 = new Texture(Gdx.files.internal("Boss/BossIdle2.png"));
        bossidleAnimation = new Animation<>(0.4f,
            new TextureRegion(bossidle2),
            new TextureRegion(bossidle3));
        bossidleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        bossfront1 = new Texture(Gdx.files.internal("Boss/BossFront1.png"));
        bossfront2 = new Texture(Gdx.files.internal("Boss/BossFront2.png"));
        bossfrontAnimation = new Animation<>(0.2f,
            new TextureRegion(bossfront1),
            new TextureRegion(bossfront2));
        bossfrontAnimation.setPlayMode(Animation.PlayMode.LOOP);

        bossback1 = new Texture(Gdx.files.internal("Boss/BossIdle1.png"));
        bossback2 = new Texture(Gdx.files.internal("Boss/BossIdle2.png"));
        bossbackAnimation = new Animation<>(0.2f,
            new TextureRegion(bossback1),
            new TextureRegion(bossback2));
        bossbackAnimation.setPlayMode(Animation.PlayMode.LOOP);
        bossright1 = new Texture(Gdx.files.internal("Boss/BossRight1.png"));
        bossright2 = new Texture(Gdx.files.internal("Boss/BossRight2.png"));
        bossrightAnimation = new Animation<>(0.2f,
            new TextureRegion(bossright1),
            new TextureRegion(bossright2));
        bossrightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        bossleft1 = new Texture(Gdx.files.internal("Boss/Bossleft1.png"));
        bossleft2 = new Texture(Gdx.files.internal("Boss/Bossleft2.png"));
        bossleftAnimation = new Animation<>(0.2f,
            new TextureRegion(bossleft1),
            new TextureRegion(bossleft2));
        bossleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Fireball Animation laden
        fireball0 = new Texture(Gdx.files.internal("Ability/fireball0000.png"));
        fireball1 = new Texture(Gdx.files.internal("Ability/fireball0001.1.png"));
        fireball2 = new Texture(Gdx.files.internal("Ability/fireball0001.png"));
        fireball3 = new Texture(Gdx.files.internal("Ability/fireball0002.png"));
        fireball4 = new Texture(Gdx.files.internal("Ability/fireball0003.png"));
        fireball5 = new Texture(Gdx.files.internal("Ability/fireball0004.png"));
        fireball6 = new Texture(Gdx.files.internal("Ability/fireball0005.png"));
        fireball7 = new Texture(Gdx.files.internal("Ability/fireball0006.png"));
        fireball8 = new Texture(Gdx.files.internal("Ability/fireball0007.png"));
        fireball9 = new Texture(Gdx.files.internal("Ability/fireball0008.png"));
        fireball10 = new Texture(Gdx.files.internal("Ability/fireball0009.png"));
        fireball11 = new Texture(Gdx.files.internal("Ability/fireball0010.png"));

        // Movement Animation: 0000-0001
        fireballMovementAnimation = new Animation<>(0.2f,
            new TextureRegion(fireball0),
            new TextureRegion(fireball1),
            new TextureRegion(fireball2));
        fireballMovementAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        // Explosion Animation: 0002-0010
        fireballExplosionAnimation = new Animation<>(0.08f,
            new TextureRegion(fireball3),
            new TextureRegion(fireball4),
            new TextureRegion(fireball5),
            new TextureRegion(fireball6),
            new TextureRegion(fireball7),
            new TextureRegion(fireball8),
            new TextureRegion(fireball9),
            new TextureRegion(fireball10),
            new TextureRegion(fireball11));
        fireballExplosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        // Ab hier Fire Arow
        firearrow0 = new Texture (Gdx.files.internal("Ability/fireArrow1.png"));
        firearrow1 = new Texture (Gdx.files.internal("Ability/fireArrow2.png"));
        firearrow2 = new Texture (Gdx.files.internal("Ability/fireArrow3.png"));
        firearrow3 = new Texture (Gdx.files.internal("Ability/fireArrow4.png"));

        // Ab hier Fire Arrow Animation
        fireArrowAnimation = new Animation<>(0.08f,
            new TextureRegion(firearrow0),
            new TextureRegion(firearrow1),
            new TextureRegion(firearrow2),
            new TextureRegion(firearrow3));
        fireArrowAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        // ab hier water blast
        waterblast0 = new Texture (Gdx.files.internal("Ability/waterBlast1.png"));
        waterblast1 = new Texture (Gdx.files.internal("Ability/waterBlast2.png"));
        waterblast2 = new Texture (Gdx.files.internal("Ability/waterBlast3.png"));
        waterblast3 = new Texture (Gdx.files.internal("Ability/waterBlast4.png"));
        waterblast4 = new Texture (Gdx.files.internal("Ability/waterBlast5.png"));

        //ab hier water blast animation
        waterBlastAnimation = new Animation<>(0.08f,
            new TextureRegion(waterblast0),
            new TextureRegion(waterblast1),
            new TextureRegion(waterblast2),
            new TextureRegion(waterblast3),
            new TextureRegion(waterblast4));
        waterBlastAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.resize(width, height); //hier etwas für Hud screen dazu
        pauseMenu.resize(width, height);
        levelUpUI.resize(width, height);
        chestUI.resize(width, height);
        settingsUI.resize(width, height);
        mapFinishedUI.resize(width, height);
    }
    public void render(GameMap map, World world, float deltaTime, GameState gameState)
    {

        ScreenUtils.clear(Color.BLUE);

        //das updated die viewport kamera und sorgt dafür, dass der Spieler verfolgt wird davon
        viewport.apply();
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();

        float halfW = viewport.getWorldWidth() / 2f;
        float halfH = viewport.getWorldHeight() / 2f;

        float targetX = world.getPlayer().getX() + world.getPlayer().getWidth() / 2f;
        float targetY = world.getPlayer().getY() + world.getPlayer().getHeight() / 2f;

        float camX = targetX;
        float camY = targetY;

        cam.position.set(camX, camY, 0f);
        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        renderMap(map, cam);
        renderPlayer(world.getPlayer(), world.getPlayer().getPlayerState(), deltaTime);

        for (Enemy enemy : world.getEnemies()) {
            if (!(enemy instanceof Boss)) {
                renderEnemy(enemy);
            }
        }
        for (Enemy e : world.getEnemies()) {
            if (e instanceof Boss) {
                renderBoss((Boss) e);
            }
        }
        for (DroppedObject drop : world.getDroppedObjects()) {

            if (drop instanceof ChestObject chest) {
                renderChest(chest);
            } else {
                drop.draw(batch);
            }
        }

        for(AbilityObject abilityObject : world.getAbilityObjects())
        {
            if (abilityObject instanceof Fireball) {
                renderFireball((Fireball) abilityObject, deltaTime);
            } else if (abilityObject instanceof FireArrowProjectile) {
                renderFireArrow((FireArrowProjectile) abilityObject, deltaTime);
            } else if (abilityObject instanceof WaterBlastProjectile) {
                renderWaterBlast((WaterBlastProjectile) abilityObject, deltaTime);
            }else {
                abilityObject.draw(batch);
            }
        }



        batch.end();

        DBcolliderRenderer();
        hud.render(
            world.getPlayer().getPlayerState(),
            world.getSurvivalTime()
        );

        if(gameState == GameState.PAUSED)
        {
            pauseMenu.render();
        }
        else if (gameState == GameState.LEVEL_UP)
        {
            levelUpUI.render();
        }
        else if (gameState == GameState.CHEST_OPENING)
        {
            chestUI.render();
        }

    }

    public void DBcolliderRenderer() //für Debug Purpose Collider anzeigen
    {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        world.getPlayer().drawCollider(shapeRenderer);

        for(Enemy enemy : world.getEnemies())
        {
            enemy.drawCollider(shapeRenderer);
        }
        for(AbilityObject abilityObject : world.getAbilityObjects())
        {
            abilityObject.drawCollider(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void renderPlayer(Player player, PlayerState playerState, float deltaTime) {

        playerAnimationTime += deltaTime;
        Animation<TextureRegion> animation;
        TextureRegion currentFrame;
        if (playerData.getPlayerClass().equals("pyromancer")) {
            pyromancerAnimationTime += deltaTime;
            if (!player.isMoving()) {
                animation = pyromanceridleAnimation;
            } else {
                switch (player.getFacingDirection()) {
                    case UP:
                        animation = pyromancerfrontAnimation;
                        break;
                    case LEFT:
                        animation = pyromancerleftAnimation;
                        break;
                    case RIGHT:
                        animation = pyromancerrightAnimation;
                        break;
                    case DOWN:
                    default:
                        animation = pyromancerbackAnimation;
                        break;
                }

            }
            currentFrame = animation.getKeyFrame(pyromancerAnimationTime);
        } else if (playerData.getPlayerClass().equals("aeromancer")) {
            aeromancerAnimationTime += deltaTime;

            if (!player.isMoving()) {
                animation = aeromanceridleAnimation;
            } else {
                switch (player.getFacingDirection()) {
                    case UP:
                        animation = aeromancerfrontAnimation;
                        break;
                    case LEFT:
                        animation = aeromancerleftAnimation;
                        break;
                    case RIGHT:
                        animation = aeromancerrightAnimation;
                        break;
                    case DOWN:
                    default:
                        animation = aeromancerbackAnimation;
                        break;
                }
            }
            currentFrame = animation.getKeyFrame(aeromancerAnimationTime);
        } else if (playerData.getPlayerClass().equals("hydromancer")) {
            hydromancerAnimationTime += deltaTime;

            if (!player.isMoving()) {
                animation = hydromanceridleAnimation;
            } else {
                switch (player.getFacingDirection()) {
                    case UP:
                        animation = hydromancerfrontAnimation;
                        break;
                    case LEFT:
                        animation = hydromancerleftAnimation;
                        break;
                    case RIGHT:
                        animation = hydromancerrightAnimation;
                        break;
                    case DOWN:
                    default:
                        animation = hydromancerbackAnimation;
                        break;
                }
            }
            currentFrame = animation.getKeyFrame(hydromancerAnimationTime);
        } else {
            geomancerAnimationTime += deltaTime;

            if (!player.isMoving()) {
                animation = geomanceridleAnimation;
            } else {
                switch (player.getFacingDirection()) {
                    case UP:
                        animation = geomancerfrontAnimation;
                        break;
                    case LEFT:
                        animation = geomancerleftAnimation;
                        break;
                    case RIGHT:
                        animation = geomancerrightAnimation;
                        break;
                    case DOWN:
                    default:
                        animation = geomancerbackAnimation;
                        break;
                }
            }
            currentFrame = animation.getKeyFrame(geomancerAnimationTime);
        }

        Color oldColor = new Color(batch.getColor());
        if (player.isDamageFlashing()) {
            float flashProgress = player.getDamageFlashProgress();
            float colorFade = 1f - flashProgress;
            batch.setColor(1f, 0.25f + 0.75f * colorFade, 0.25f + 0.75f * colorFade, 1f);
        }
        if (playerState.isDodgeEffectActive()) {
            batch.setColor(10f, 10f, 10f, 0.5f);
        }

        batch.draw(
            currentFrame,
            player.getX(),
            player.getY(),
            player.getWidth(),
            player.getHeight()
        );

        batch.setColor(oldColor);
    }

    private void renderEnemy(Enemy enemy) {

        Animation<TextureRegion> animation;

        if (!enemy.isMoving()) {
            animation = enemy1idleAnimation;
        } else {
            switch (enemy.getFacingDirection()) {
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
        TextureRegion currentFrame = animation.getKeyFrame(enemy.getAnimationTime());


        batch.draw(
            currentFrame,
            enemy.getX(),
            enemy.getY(),
            enemy.getWidth(),
            enemy.getHeight()
        );
    }
    private void renderBoss(Boss boss) {

        Animation<TextureRegion> animation;

        if (!boss.isMoving()) {
            animation = bossidleAnimation;
        } else {
            switch (boss.getFacingDirection()) {
                case UP:
                    animation = bossfrontAnimation;
                    break;
                case LEFT:
                    animation = bossleftAnimation;
                    break;
                case RIGHT:
                    animation = bossrightAnimation;
                    break;
                case DOWN:
                default:
                    animation = bossbackAnimation;
                    break;
            }
        }
        TextureRegion currentFrame = animation.getKeyFrame(boss.getAnimationTime());


        batch.draw(
            currentFrame,
            boss.getX(),
            boss.getY(),
            boss.getWidth(),
            boss.getHeight()
        );
    }
    private void renderChest(ChestObject chest) {

        Animation<TextureRegion> animation = null;

        if (chest.getChestType() == ChestType.NORMAL) {
            switch (chest.getState()) {
                case CLOSED:
                    animation = normalChestClosedAnimation;
                    break;
                case OPENING:
                    animation = normalChestOpeningAnimation;
                    break;
                case OPENED:
                    animation = normalChestOpenedAnimation;
                    break;
            }
        } else {
            switch (chest.getState()) {
                case CLOSED:
                    animation = legendaryChestClosedAnimation;
                    break;
                case OPENING:
                    animation = legendaryChestOpeningAnimation;
                    break;
                case OPENED:
                    animation = legendaryChestOpenedAnimation;
                    break;
            }
        }

        TextureRegion currentFrame = animation.getKeyFrame(chest.getAnimationTime(), false);

        batch.draw(
            currentFrame,
            chest.getX(),
            chest.getY(),
            chest.getWidth(),
            chest.getHeight()
        );
    }
    private void renderFireball(Fireball fireball, float deltaTime) {
        Animation<TextureRegion> animation;

        if (fireball.hasExploded()) {
            animation = fireballExplosionAnimation;
        } else {
            animation = fireballMovementAnimation;
        }

        TextureRegion currentFrame = animation.getKeyFrame(fireball.getAnimationTime(), false);

        batch.draw(
            currentFrame,
            fireball.getX(),
            fireball.getY(),
            fireball.getWidth()/2 ,
            fireball.getHeight() / 2,
            fireball.getWidth(),
            fireball.getHeight(),
            1,
            1,
            fireball.getRotation()
        );
    }
    private void renderFireArrow(FireArrowProjectile fireArrow, float deltaTime) {
        Animation<TextureRegion> animation;

        animation = fireArrowAnimation;

        TextureRegion currentFrame = animation.getKeyFrame(fireArrow.getAnimationTime());

        batch.draw(
            currentFrame,
            fireArrow.getX(),
            fireArrow.getY(),
            fireArrow.getWidth() / 2,
            fireArrow.getHeight() / 2,
            fireArrow.getWidth(),
            fireArrow.getHeight(),
            1,
            1,
            fireArrow.getRotation()
        );
    }
    private void renderWaterBlast(WaterBlastProjectile waterBlastProjectile, float deltaTime) {
        Animation<TextureRegion> animation;

        animation = waterBlastAnimation;

        TextureRegion currentFrame = animation.getKeyFrame(waterBlastProjectile.getAnimationTime());

        batch.draw(
            currentFrame,
            waterBlastProjectile.getX(),
            waterBlastProjectile.getY(),
            waterBlastProjectile.getWidth() / 2,
            waterBlastProjectile.getHeight() / 2,
            waterBlastProjectile.getWidth(),
            waterBlastProjectile.getHeight(),
            1,
            1,
            waterBlastProjectile.getRotation()
        );
    }
    private void renderMap(GameMap map, OrthographicCamera cam) {
        float tileWidth = map.getWorldWidth();
        float tileHeight = map.getWorldHeight();
        float left = cam.position.x - viewport.getWorldWidth() / 2f;
        float right = cam.position.x + viewport.getWorldWidth() / 2f;
        float bottom = cam.position.y - viewport.getWorldHeight() / 2f;
        float top = cam.position.y + viewport.getWorldHeight() / 2f;

        int startX = MathUtils.floor(left / tileWidth) - 1;
        int endX = MathUtils.floor(right / tileWidth) + 1;
        int startY = MathUtils.floor(bottom / tileHeight) - 1;
        int endY = MathUtils.floor(top / tileHeight) + 1;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                batch.draw(
                    map.getTexture(),
                    x * tileWidth,
                    y * tileHeight,
                    tileWidth,
                    tileHeight
                );
            }
        }
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

        pyromanceridle2.dispose();
        pyromanceridle3.dispose();
        pyromanceridle4.dispose();
        pyromancerfront1.dispose();
        pyromancerfront2.dispose();
        pyromancerfront3.dispose();
        pyromancerfront4.dispose();
        pyromancerfront5.dispose();
        pyromancerback1.dispose();
        pyromancerback2.dispose();
        pyromancerback3.dispose();
        pyromancerback4.dispose();
        pyromancerback5.dispose();
        pyromancerright1.dispose();
        pyromancerright2.dispose();
        pyromancerright3.dispose();
        pyromancerright4.dispose();
        pyromancerright5.dispose();
        pyromancerright6.dispose();
        pyromancerright7.dispose();
        pyromancerright8.dispose();
        pyromancerright9.dispose();
        pyromancerleft1.dispose();
        pyromancerleft2.dispose();
        pyromancerleft3.dispose();
        pyromancerleft4.dispose();
        pyromancerleft5.dispose();
        pyromancerleft6.dispose();
        pyromancerleft7.dispose();
        pyromancerleft8.dispose();
        pyromancerleft9.dispose();

        aeromanceridle2.dispose();
        aeromanceridle3.dispose();
        aeromanceridle4.dispose();
        aeromancerfront1.dispose();
        aeromancerfront2.dispose();
        aeromancerfront3.dispose();
        aeromancerfront4.dispose();
        aeromancerfront5.dispose();
        aeromancerback1.dispose();
        aeromancerback2.dispose();
        aeromancerback3.dispose();
        aeromancerback4.dispose();
        aeromancerback5.dispose();
        aeromancerright1.dispose();
        aeromancerright2.dispose();
        aeromancerright3.dispose();
        aeromancerright4.dispose();
        aeromancerright5.dispose();
        aeromancerright6.dispose();
        aeromancerright7.dispose();
        aeromancerright8.dispose();
        aeromancerright9.dispose();
        aeromancerright10.dispose();
        aeromancerright11.dispose();
        aeromancerright12.dispose();
        aeromancerright13.dispose();
        aeromancerright14.dispose();
        aeromancerright15.dispose();
        aeromancerright16.dispose();
        aeromancerright17.dispose();
        aeromancerright18.dispose();
        aeromancerright19.dispose();
        aeromancerright20.dispose();
        aeromancerright21.dispose();
        aeromancerright22.dispose();
        aeromancerright23.dispose();
        aeromancerright24.dispose();
        aeromancerright25.dispose();
        aeromancerleft1.dispose();
        aeromancerleft2.dispose();
        aeromancerleft3.dispose();
        aeromancerleft4.dispose();
        aeromancerleft5.dispose();
        aeromancerleft6.dispose();
        aeromancerleft7.dispose();
        aeromancerleft8.dispose();
        aeromancerleft9.dispose();
        aeromancerleft10.dispose();
        aeromancerleft11.dispose();
        aeromancerleft12.dispose();
        aeromancerleft13.dispose();
        aeromancerleft14.dispose();
        aeromancerleft15.dispose();
        aeromancerleft16.dispose();
        aeromancerleft17.dispose();
        aeromancerleft18.dispose();
        aeromancerleft19.dispose();
        aeromancerleft20.dispose();
        aeromancerleft21.dispose();
        aeromancerleft22.dispose();
        aeromancerleft23.dispose();
        aeromancerleft24.dispose();
        aeromancerleft25.dispose();

        hydromanceridle2.dispose();
        hydromanceridle3.dispose();
        hydromanceridle4.dispose();
        hydromancerfront1.dispose();
        hydromancerfront2.dispose();
        hydromancerfront3.dispose();
        hydromancerfront4.dispose();
        hydromancerfront5.dispose();
        hydromancerback1.dispose();
        hydromancerback2.dispose();
        hydromancerback3.dispose();
        hydromancerback4.dispose();
        hydromancerback5.dispose();
        hydromancerright1.dispose();
        hydromancerright2.dispose();
        hydromancerright3.dispose();
        hydromancerright4.dispose();
        hydromancerright5.dispose();
        hydromancerright6.dispose();
        hydromancerright7.dispose();
        hydromancerright8.dispose();
        hydromancerright9.dispose();
        hydromancerleft1.dispose();
        hydromancerleft2.dispose();
        hydromancerleft3.dispose();
        hydromancerleft4.dispose();
        hydromancerleft5.dispose();
        hydromancerleft6.dispose();
        hydromancerleft7.dispose();
        hydromancerleft8.dispose();
        hydromancerleft9.dispose();

        geomanceridle2.dispose();
        geomanceridle3.dispose();
        geomanceridle4.dispose();
        geomancerfront1.dispose();
        geomancerfront2.dispose();
        geomancerfront3.dispose();
        geomancerfront4.dispose();
        geomancerfront5.dispose();
        geomancerback1.dispose();
        geomancerback2.dispose();
        geomancerback3.dispose();
        geomancerback4.dispose();
        geomancerback5.dispose();
        geomancerright1.dispose();
        geomancerright2.dispose();
        geomancerright3.dispose();
        geomancerright4.dispose();
        geomancerright5.dispose();
        geomancerright6.dispose();
        geomancerright7.dispose();
        geomancerright8.dispose();
        geomancerright9.dispose();
        geomancerleft1.dispose();
        geomancerleft2.dispose();
        geomancerleft3.dispose();
        geomancerleft4.dispose();
        geomancerleft5.dispose();
        geomancerleft6.dispose();
        geomancerleft7.dispose();
        geomancerleft8.dispose();
        geomancerleft9.dispose();
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
        bossidle2.dispose();
        bossidle3.dispose();
        bossback1.dispose();
        bossback2.dispose();
        bossfront1.dispose();
        bossfront2.dispose();
        bossright1.dispose();
        bossright2.dispose();
        bossleft1.dispose();
        bossleft2.dispose();
        fireball0.dispose();
        fireball1.dispose();
        fireball2.dispose();
        fireball3.dispose();
        fireball4.dispose();
        fireball5.dispose();
        fireball6.dispose();
        fireball7.dispose();
        fireball8.dispose();
        fireball9.dispose();
        fireball10.dispose();
        hud.dispose();
        levelUpUI.dispose();
        chestUI.dispose();
        normalChest1.dispose();
        normalChest2.dispose();
        normalChest3.dispose();
        normalChest4.dispose();
        normalChest5.dispose();
        legendaryChest1.dispose();
        legendaryChest2.dispose();
        legendaryChest3.dispose();
        legendaryChest4.dispose();
        legendaryChest5.dispose();

    }
}
