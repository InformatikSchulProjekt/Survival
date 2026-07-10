package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.core.input.InputManager;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.rendering.Renderer;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.screen.hud.*;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.world.maps.MapRegistry;

import java.util.function.IntConsumer;

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

    private Vector2 playerMoveDirection = new Vector2();

    private GameState state = GameState.PLAYING;
    private final String map;

    private final InputManager inputManager;

    private final PauseMenuUI pauseMenu;
    private final LevelUpUI levelUpUI;
    private final ChestUI chestUI;
    private final SettingsUI settingsUI;
    private final InventoryUI inventoryUI;
    private final AbilitiesUI abilitiesUI;
    private final MapFinishedUI mapFinishedUI;
    private final DeathScreenUI deathScreenUI;

    public GamePlayScreen(Main game, DataLoader dataLoader, String map)
    {
        this.main = game;
        this.map = map;

        this.gameMap = MapRegistry.getMap(map);

        this.dataLoader = dataLoader;
        PlayerData playerData = dataLoader.getPlayerData(map);

        this.playerState = new PlayerState(playerData, this);
        this.world = new World(playerState, gameMap, map, dataLoader, this);

        this.shapeRenderer = new ShapeRenderer();

        pauseMenu = new PauseMenuUI(shapeRenderer);
        levelUpUI = new LevelUpUI(shapeRenderer);
        chestUI = new ChestUI(shapeRenderer);
        settingsUI = new SettingsUI(dataLoader, shapeRenderer);
        mapFinishedUI = new MapFinishedUI(shapeRenderer);
        inventoryUI = new InventoryUI(shapeRenderer);
        inventoryUI.setPlayerState(playerState);

        abilitiesUI = new AbilitiesUI(shapeRenderer);
        abilitiesUI.setPlayerState(playerState);

        deathScreenUI = new DeathScreenUI(shapeRenderer);

        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight, world, shapeRenderer,playerData,pauseMenu,levelUpUI,chestUI, settingsUI, inventoryUI, abilitiesUI, deathScreenUI, mapFinishedUI);
        this.abilityService = new AbilityService(playerState, world, renderer.getViewport());
        this.inputManager = new InputManager(world, abilityService, dataLoader);

        abilitiesUI.setAbilityService(abilityService);

        playerState.setupAbilityService(abilityService);

        setupPauseMenu();
        setupLevelUpUI();
        setupChestUI();
        setupSettingsUI();
        setupInventoryUI();
        setupAbilitiesUI();
        setupMapFinishedUI();
        setupDeathScreenUI();
    }

    private void setupPauseMenu() {
        pauseMenu.setResumeListener(new Runnable() {
            @Override
            public void run() {
                state = GameState.PLAYING;
                Gdx.input.setInputProcessor(null);
            }
        });
        pauseMenu.setBackToMenuListener(new Runnable() {
            @Override
            public void run() {
                backToMenuWithoutSaving();
            }
        });
        pauseMenu.setGiveUpListener(new Runnable() {
            @Override
            public void run() {
                gameOver();
            }
        });
        pauseMenu.setSettingsListener(new Runnable() {
            @Override
            public void run() {
                state = GameState.SETTINGS;
                Gdx.input.setInputProcessor(settingsUI.getStage());
            }
        });
        pauseMenu.setInventoryListener(new Runnable() {
            @Override
            public void run() {

                state = GameState.INVENTORY;

                inventoryUI.refresh();

                Gdx.input.setInputProcessor(
                    inventoryUI.getStage()
                );
            }
        });
        pauseMenu.setAbilitiesListener(new Runnable() {
            @Override
            public void run() {

                state = GameState.ABILITIES;

                abilitiesUI.refresh();

                Gdx.input.setInputProcessor(
                    abilitiesUI.getStage()
                );
            }
        });
    }

    private void setupLevelUpUI() {
        levelUpUI.setAbilityRegistry(abilityService.getAbilityRegistry());
        levelUpUI.setPlayerState(playerState);
        levelUpUI.setOptionChosenListener(new IntConsumer() {
            @Override
            public void accept(int optionIndex) {
                playerState.chooseAbilityOption(optionIndex);

                if (!playerState.isAwaitingLevelUpChoice()) {
                    // keine weitere Auswahl offen => zurück ins Spiel
                    state = GameState.PLAYING;
                    Gdx.input.setInputProcessor(null);
                } else {
                    // z.B. 2 Level auf einmal aufgestiegen => nächste Karten zeigen
                    levelUpUI.showOptions(playerState.getPendingAbilityOptions());
                }
            }
        });
    }

    private void setupChestUI() {
        chestUI.setOptionChosenListener(new IntConsumer() {
            @Override
            public void accept(int optionIndex) {
                playerState.chooseChestItem(optionIndex);

                state = GameState.PLAYING;
                Gdx.input.setInputProcessor(null);
            }
        });
    }

    private void setupSettingsUI() {
        settingsUI.setBackListener(new Runnable() {
            @Override
            public void run() {
                state = GameState.PAUSED;
                Gdx.input.setInputProcessor(pauseMenu.getStage());
            }
        });
        settingsUI.setResetListener(new Runnable() {
            @Override
            public void run() {
                dataLoader.resetKeybinds();
                settingsUI.refreshButtons();
            }
        });
    }

    private void setupInventoryUI(){

        inventoryUI.setBackListener(new Runnable() {

            @Override
            public void run() {

                state = GameState.PAUSED;

                Gdx.input.setInputProcessor(
                    pauseMenu.getStage()
                );

            }
        });

    }

    private void setupAbilitiesUI() {

        abilitiesUI.setBackListener(new Runnable() {

            @Override
            public void run() {

                state = GameState.PAUSED;

                Gdx.input.setInputProcessor(
                    pauseMenu.getStage()
                );

            }
        });

    }

    private void setupDeathScreenUI() {

        deathScreenUI.setRestartListener(new Runnable() {
            @Override
            public void run() {
                gameDone(true);
            }
        });

        deathScreenUI.setBackToTitleListener(new Runnable() {
            @Override
            public void run() {
                gameDone(false);
            }
        });

    }

    private void setupMapFinishedUI() {
        mapFinishedUI.setBackToMenuListener(new Runnable() {
            @Override
            public void run() {
                gameDone(false);
            }
        });

        mapFinishedUI.setKeepPlayingListener(new Runnable() {
            @Override
            public void run() {
                world.getSpawnSystem().startInfiniteMode();

                state = GameState.PLAYING;
                Gdx.input.setInputProcessor(null);
            }
        });
    }

    // Wird gecalled wenn game vorbei
    private void gameDone(boolean restart) {
        state = GameState.PAUSED;

        saveSurvivalTime();

        if (restart) {
            // Neuer Run mit der gleichen Klasse, aber sonst komplett von 0
            // (kein clearPlayerData, sonst würde beim Neustart die playerClass fehlen und der Start crashen).
            restartWithSameClass();
        } else {
            dataLoader.clearPlayerData(map);
        }

        main.gameOver(restart, map);
    }

    private void restartWithSameClass() {
        String playerClass = playerState.getPlayerData().playerClass;

        PlayerData freshPlayerData = new PlayerData();
        freshPlayerData.playerClass = playerClass;

        dataLoader.savePlayerData(map, freshPlayerData);
    }

    private void backToMenuWithoutSaving() {
        state = GameState.PAUSED;

        main.setScreen(new TitleScreen(main, dataLoader));
    }

    // wird gecalled wenn Spiel verloren
    public void gameOver(boolean restart) {
        SoundManager.playSound("gameOver.wav");
        showDeathScreen();
    }

    public void gameOver() {
        gameOver(false);
    }

    private void showDeathScreen() {
        state = GameState.DEAD;

        playerMoveDirection.setZero();
        SoundManager.stopFootsteps();
        world.getPlayer().updateMoveDirection(playerMoveDirection);

        deathScreenUI.show(world.getSurvivalTime());

        Gdx.input.setInputProcessor(deathScreenUI.getStage());
    }

    public void saveSurvivalTime() {
        int survivalTime = (int) world.getSurvivalTime();
        dataLoader.saveSurvivalTimeIfBest(map, survivalTime);
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }

    public void showGameFinishedUI() {
        System.out.println("SHOW GAME FINISHED UI");
        state = GameState.MAP_FINISHED;
        Gdx.input.setInputProcessor(mapFinishedUI.getStage());
    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);   // passt sich der Bildschirmgröße an
    }

    private void processInput()
    {
        if (state == GameState.LEVEL_UP
            || state == GameState.CHEST_OPENING
            || state == GameState.SETTINGS
            || state == GameState.MAP_FINISHED
            || state == GameState.INVENTORY
            || state == GameState.ABILITIES
            || state == GameState.DEAD
        ) {
            return;
        }

        if (inputManager.processInput())
        {
            state = GameState.PAUSED;
            Gdx.input.setInputProcessor(pauseMenu.getStage());
        }
    }

    @Override
    public void render(float deltaTime)
    {

        if (state != GameState.LEVEL_UP && state != GameState.CHEST_OPENING && state != GameState.DEAD && playerState.isAwaitingLevelUpChoice())
        {
            // Level-Up-Auswahl steht an: Spiel anhalten und Karten anzeigen
            state = GameState.LEVEL_UP;

            playerMoveDirection.setZero();
            SoundManager.stopFootsteps();
            world.getPlayer().updateMoveDirection(playerMoveDirection);

            levelUpUI.showOptions(playerState.getPendingAbilityOptions());
            Gdx.input.setInputProcessor(levelUpUI.getStage());
        }
        else if (state != GameState.LEVEL_UP && state != GameState.CHEST_OPENING && state != GameState.DEAD && playerState.isAwaitingChestChoice())
        {
            // Chest wurde geöffnet: Spiel anhalten und Item-Karten anzeigen
            state = GameState.CHEST_OPENING;

            playerMoveDirection.setZero();
            SoundManager.stopFootsteps();
            world.getPlayer().updateMoveDirection(playerMoveDirection);

            chestUI.showOptions(playerState.getPendingChestItems());
            Gdx.input.setInputProcessor(chestUI.getStage());
        }

        processInput();

        if(state == GameState.PLAYING)
        {
            updateLogic(deltaTime);
        }

        float renderDeltaTime = state == GameState.PLAYING ? deltaTime : 0f;
        renderer.render(gameMap, world, renderDeltaTime, state); //animationen

        switch (state) {

            case PAUSED:
                pauseMenu.render();
                break;

            case INVENTORY:
                inventoryUI.render();
                break;

            case ABILITIES:
                abilitiesUI.render();
                break;

            case SETTINGS:
                settingsUI.render();
                break;

            case MAP_FINISHED:
                mapFinishedUI.render();
                break;

            case DEAD:
                deathScreenUI.render();
                break;
        }
        if (state == GameState.SETTINGS &&
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            state = GameState.PAUSED;
            Gdx.input.setInputProcessor(pauseMenu.getStage());
        }

    }

    private void updateLogic(float deltaTime)
    {
        world.update(deltaTime);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        gameMap.dispose();

    }
}
