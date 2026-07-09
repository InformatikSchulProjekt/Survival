package com.test.SurvivorGame.world.system;

public class RunTimerSystem {

    private boolean survivalTimePaused = false;
    private float survivalTime = 0f; // Vergangene Survivaltime, die im HUD angezeigt wird
    private float passedTime = 0f; // Echte vergangene Zeit

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
