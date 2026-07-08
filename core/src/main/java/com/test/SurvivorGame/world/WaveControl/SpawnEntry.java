package com.test.SurvivorGame.world.WaveControl;

public class SpawnEntry {

    private EnemyType enemyType;
    private float chance;

    public SpawnEntry(EnemyType enemyType, float chance)
    {
        this.enemyType = enemyType;
        this.chance = chance;
    }

    public EnemyType getEnemyType()
    {
        return enemyType;
    }

    public float getChance()
    {
        return chance;
    }
}
