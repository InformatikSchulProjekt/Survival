package com.test.SurvivorGame.world.system;

import com.test.SurvivorGame.core.data.PlayerData;

public class RunTimerSystem {
    private boolean survivalTimePaused = false;
    private float survivalTime; // Vergangene Survivaltime, die im HUD angezeigt wird
    private float passedTime; // Echte vergangene Zeit

    public RunTimerSystem(PlayerData playerData) {
        survivalTime = playerData.wave * 120f;
        passedTime = survivalTime;
    }

    public void update(float deltaTime) {
        passedTime += deltaTime;

        if (!survivalTimePaused) {
            survivalTime += deltaTime;
        }
    }

    public float getSurvivalTime() {
        return survivalTime;
    }

    public float getPassedTime() {
        return passedTime;
    }

    public boolean isSurvivalTimePaused() {
        return survivalTimePaused;
    }

    public void setSurvivalTimePaused(boolean paused) {
        this.survivalTimePaused = paused;
    }
}
