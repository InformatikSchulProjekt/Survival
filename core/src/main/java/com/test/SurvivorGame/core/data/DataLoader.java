package com.test.SurvivorGame.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class DataLoader {
    private static final String SAVE_FILE = "save/save_data.json";

    private final Json json;
    private SaveData saveData;

    public DataLoader() {
        this.json = new Json();
        this.json.setUsePrototypes(false); // Tells LibGDX: Do not skip fields just because they have the same value as a default object.

        // Lädt den Save einmal und hält ihn danach im Speicher.
        this.saveData = loadSaveData();
    }

    public MapSaveData getMapSaveData(String map) {
        // Erstellt automatisch einen neuen Map-Save, falls noch keiner existiert.
        if (!saveData.maps.containsKey(map)) {
            saveData.maps.put(map, new MapSaveData());
        }

        return saveData.maps.get(map);
    }

    public void saveSaveData() {
        FileHandle file = Gdx.files.local(SAVE_FILE);

        // Schreibt den aktuellen Speicherstand als JSON-Datei.
        file.writeString(json.prettyPrint(saveData), false);
    }

    private SaveData loadSaveData() {
        FileHandle file = Gdx.files.local(SAVE_FILE);

        // Wenn noch kein Save existiert, wird ein leerer Speicherstand genutzt.
        if (!file.exists()) {
            return new SaveData();
        }

        SaveData loadedSaveData = json.fromJson(SaveData.class, file);

        // Schutz gegen ungültige Save-Dateien.
        if (loadedSaveData == null) {
            return new SaveData();
        }

        return loadedSaveData;
    }

    public GeneralData getGeneralData() {
        return saveData.generalData;
    }

    public void saveGeneralData(GeneralData generalData) {
        saveData.generalData = generalData;
        saveSaveData();
    }

    public void saveSurvivalTimeIfBest(String map, int survivalTime) {
        MapSaveData mapSaveData = getMapSaveData(map);

        if (survivalTime > mapSaveData.bestSurvivalTime) {
            mapSaveData.bestSurvivalTime = survivalTime;
            saveSaveData();
            System.out.println("NEUE BEST SURVIVAL TIME:" + survivalTime); // debug
        }
    }

    public PlayerData getPlayerData(String map) {
        PlayerData playerData = getMapSaveData(map).playerData;
        if (playerData == null) { // => PlayerData war leer.
            playerData = new PlayerData();
        }
        return playerData;
    }

    public void savePlayerData(String map, PlayerData playerData) {
        getMapSaveData(map).playerData = playerData;
        saveSaveData();
    }

    public void clearPlayerData(String map) {
        savePlayerData(map, null);
    }

    public boolean hasPlayerData(String map) {
        return getMapSaveData(map).playerData != null;
    }
}
