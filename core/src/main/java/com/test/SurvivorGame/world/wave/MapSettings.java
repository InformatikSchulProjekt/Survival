package com.test.SurvivorGame.world.wave;

import java.util.ArrayList;

public class MapSettings {

    private final ArrayList<EnemyType> bossTypes;

    private float waveTime;
    private float startInterval;
    private float endInterval;

    public MapSettings(ArrayList<EnemyType> bossTypes,
                       float waveTime,
                       float startInterval,
                       float endInterval)
    {
        this.bossTypes = bossTypes;

        this.waveTime = waveTime;
        this.startInterval = startInterval;
        this.endInterval = endInterval;
    }

    public ArrayList<EnemyType> getBossTypes() {
        return bossTypes;
    }

    public float getWaveTime()
    {
        return waveTime;
    }

    public float getStartInterval()
    {
        return startInterval;
    }

    public float getEndInterval()
    {
        return endInterval;
    }
}
