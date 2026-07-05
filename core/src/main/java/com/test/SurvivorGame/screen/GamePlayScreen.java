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
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.screen.HuD.PauseMenuRenderer;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.core.SoundManager;

public class GamePlayScreen extends ScreenAdapter {
    private final DataLoader dataLoader;
    private final PlayerState playerState;
    private final AbilityService abilityService;

    public final float screenWidth = 16f;
    public final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
    private final Renderer renderer;
    private final ShapeRenderer shapeRenderer;

    private final World world;
    private final GameMap map = new GameMap();

    private Vector2 playerMoveDirection = new Vector2();

    private GameState state;

    private final PauseMenuRenderer pauseMenuRenderer;

    public GamePlayScreen(Main game, DataLoader dataLoader)
    {
        // "TestMap" ist obv. temporär da soll dann die ausgewählte Map rein.
        this.dataLoader = dataLoader;
        PlayerData playerData = dataLoader.getPlayerData("TestMap");
        // bis ability slots gui da:
        playerData.abilitySlots[0] = "melee";
        playerData.abilitySlots[1] = "fireball";
        playerData.abilitySlots[2] = "fire_arrow";
        playerData.abilitySlots[3] = "firestorm";

        playerData.playerClass = "pyromancer"; // temporär bis Klasse picken logic da.

        this.playerState = new PlayerState(playerData);
        this.world = new World(screenWidth, screenHeight, playerState, map);

        this.shapeRenderer = new ShapeRenderer();

        state = GameState.PLAYING;

        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight, world, shapeRenderer,playerData);

        this.pauseMenuRenderer = new PauseMenuRenderer(shapeRenderer);

        pauseMenuRenderer.setResumeListener(new Runnable() {
            @Override
            public void run() {
                state = GameState.PLAYING;
                Gdx.input.setInputProcessor(null);
            }
        });

        this.abilityService = new AbilityService(playerState, world, renderer.getViewport());
        playerState.setupAbilityService(abilityService);


    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);   // passt sich der Bildschirmgröße an
    }

    private void processInput() // sollte später eigene klasse werde, oder? hier nur zum, rumtesten ig
    {
        playerMoveDirection.setZero(); // damits nicht wächst

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if (state == GameState.PLAYING)
            {
                state = GameState.PAUSED;

                Gdx.input.setInputProcessor(pauseMenuRenderer.getStage());

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

        // temporär um zu saven, weil es noch keine andere Optionen gibt.
        dataLoader.savePlayerData("TestMap", playerState.getPlayerData());
    }

    @Override
    public void render(float deltaTime)
    {
        processInput();

        if(state == GameState.PLAYING)
        {
            updateLogic(deltaTime, map);
        }

        float renderDeltaTime = state == GameState.PLAYING ? deltaTime : 0f;
        renderer.render(map, world, renderDeltaTime, state); //animationen

        if (state == GameState.PAUSED) {
            pauseMenuRenderer.render();
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
        map.dispose();
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

        abilityService.activate(abilityId);
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
