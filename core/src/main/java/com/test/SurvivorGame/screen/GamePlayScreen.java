package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.Rendering.Renderer;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.screen.HuD.PauseMenuRenderer;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.world.maps.MapRegistry;

public class GamePlayScreen extends ScreenAdapter {
    private final Main main;
    private final DataLoader dataLoader;
    private final PlayerState playerState;
    private final AbilityService abilityService;

    public final float screenWidth = 16f;
    public final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
    private final Renderer renderer;
    private final ShapeRenderer shapeRenderer;

    private final World world;
    private final GameMap gameMap;

    private final DeathScreen deathScreen;
    private float finalSurvivalTime = 0f;

    private Vector2 playerMoveDirection = new Vector2();

    private GameState state;
    private final String map;

    private final PauseMenuRenderer pauseMenu;
    public GamePlayScreen(Main game, DataLoader dataLoader, String map)
    {
        this.main = game;
        this.map = map;

        this.gameMap = MapRegistry.getMap(map);

        this.dataLoader = dataLoader;
        PlayerData playerData = dataLoader.getPlayerData(map);
        // bis ability slots gui da:
        playerData.abilitySlots[0] = "melee";
        playerData.abilitySlots[1] = "fireball";
        playerData.abilitySlots[2] = "fire_arrow";
        playerData.abilitySlots[3] = "firestorm";

        playerData.playerClass = "pyromancer"; // temporär bis Klasse picken logic da.

        this.playerState = new PlayerState(playerData);
        this.world = new World(screenWidth, screenHeight, playerState, gameMap);

        this.shapeRenderer = new ShapeRenderer();

        state = GameState.PLAYING;
        pauseMenu = new PauseMenuRenderer(shapeRenderer, playerState);


        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight, world, shapeRenderer,playerData,pauseMenu);

        pauseMenu.setResumeListener(new Runnable() {
            @Override
            public void run() {
                state = GameState.PLAYING;
                Gdx.input.setInputProcessor(null);
            }
        });
        pauseMenu.setSaveListener(new Runnable() {
            @Override
            public void run() {
                dataLoader.savePlayerData(map, playerState.getPlayerData());
            }
        });
        pauseMenu.setGiveUpListener(new Runnable() {
            @Override
            public void run() {
                playerState.gameOver();
                Gdx.input.setInputProcessor(null);
            }
        });
        pauseMenu.setSettingsListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("settingsScreen");
                Gdx.input.setInputProcessor(null);
            }
        });
        pauseMenu.setInventoryListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("inventoryScreen");
                Gdx.input.setInputProcessor(null);
            }
        });
        pauseMenu.setAbilitiesListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("abilitiesScreen");
                Gdx.input.setInputProcessor(null);
            }
        });

        this.abilityService = new AbilityService(playerState, world, renderer.getViewport());
        playerState.setupAbilityService(abilityService);
        this.deathScreen = new DeathScreen(game.getBatch(), shapeRenderer);


    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);// passt sich der Bildschirmgröße an
        deathScreen.resize(width, height);
    }

    private void processInput() // sollte später eigene klasse werde, oder? hier nur zum, rumtesten ig
    {
        playerMoveDirection.setZero(); // damits nicht wächst

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if (state == GameState.PLAYING)
            {
                state = GameState.PAUSED;

                Gdx.input.setInputProcessor(pauseMenu.getStage());

                // Spieler sofort anhalten
                playerMoveDirection.setZero();
                SoundManager.stopFootsteps();
                world.getPlayer().updateMoveDirection(playerMoveDirection);

            }
            else
            {
                state = GameState.PLAYING;
            }
        }

        if (state == GameState.PAUSED)
        {
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            playerMoveDirection.y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            playerMoveDirection.y -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            playerMoveDirection.x += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            playerMoveDirection.x -= 1;
        }

        if(!playerMoveDirection.isZero()) //wenns schräg geht normalisieren, aber wenn sich der Player nicht bewegt wird (x = 0,y = 0) / 0
        {
            playerMoveDirection.nor();
        }

        world.getPlayer().updateMoveDirection(playerMoveDirection);

        // Ability Keybinds
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        {
            activateAbilitySlot(0);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
        {
            activateAbilitySlot(1);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
        {
            activateAbilitySlot(2);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
        {
            activateAbilitySlot(3);
        }

    }

    @Override
    public void render(float deltaTime)
    {
        if (playerState.isGameOver()) {
            // Spiel einfrieren und Death Screen anzeigen, statt sofort neu zu starten
            finalSurvivalTime = world.getSurvivalTime();
            dataLoader.saveSurvivalTimeIfBest(map, (int) finalSurvivalTime);
            dataLoader.savePlayerData(map, new PlayerData()); // resetet PlayerData für Map

            playerMoveDirection.setZero();
            SoundManager.stopFootsteps();
            world.getPlayer().updateMoveDirection(playerMoveDirection);

            state = GameState.DEAD;
        }

        processInput();

        if(state == GameState.PLAYING)
        {
            updateLogic(deltaTime, gameMap);
        }

        float renderDeltaTime = state == GameState.PLAYING ? deltaTime : 0f;
        renderer.render(gameMap, world, renderDeltaTime, state); //animationen

        if (state == GameState.PAUSED) {
            pauseMenu.render();
        }
        if (state == GameState.DEAD)
        {
            deathScreen.render(finalSurvivalTime);
        }
    }
    private void processDeathInput()
    {
        boolean restartRequested = Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
            || Gdx.input.isKeyJustPressed(Input.Keys.R)
            || deathScreen.isRestartClicked();

        if (restartRequested)
        {
            main.gameOver();
        }
    }

    private void updateLogic(float deltaTime, GameMap map)
    {
        world.update(deltaTime, map);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        gameMap.dispose();
        deathScreen.dispose();
    }

    private void activateAbilitySlot(int slotIndex) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        if (slotIndex < 0 || slotIndex >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slotIndex);
        }

        String abilityId = abilitySlots[slotIndex];

        if (abilityId == null || abilityId.isBlank()) {
            return;
        }

        abilityService.activate(abilityId, world.getPassedTime());
    }

    // Methode die vom UI benutzt werden kann um 2 Ability Slots zu swappen
    private void swapAbilitySlots(int slot1, int slot2) {
        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        if (slot1 < 0 || slot1 >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slot1);
        }

        if (slot2 < 0 || slot2 >= abilitySlots.length) {
            throw new IllegalArgumentException("Invalid ability slot index: " + slot2);
        }

        if (slot1 == slot2) {
            System.out.println("[WARNING]: Tried to swap same slot!");
            return;
        }

        String temp = abilitySlots[slot1];
        abilitySlots[slot1] = abilitySlots[slot2];
        abilitySlots[slot2] = temp;
    }
}

