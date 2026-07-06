package com.test.SurvivorGame.core.data;

import java.util.HashMap;
import java.util.Map;

public class SaveData {
    public GeneralData generalData = new GeneralData();
    public Map<String, MapSaveData> maps = new HashMap<>();
}
