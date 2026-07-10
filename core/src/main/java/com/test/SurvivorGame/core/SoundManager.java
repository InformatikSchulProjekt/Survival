package com.test.SurvivorGame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager {

    private static float volume = 1.0f;
    private static Music footstepSound;
    private static ObjectMap<String, Sound> sounds = new ObjectMap<>();

    public static void playSound(String fileName, float customVolume) {
        try {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + fileName));
            long id = sound.play();
            sound.setVolume(id, customVolume);


        } catch (Exception e) {
            Gdx.app.error("SoundManager", " konnte Sound nicht abspielen: " + fileName);
        }

    }

    private static void playFootstep() {
        if (footstepSound == null) {
            footstepSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/footstepsPlayer.wav"));
            footstepSound.setLooping(false);
        }

        if (footstepSound.isPlaying()) {
            return;
        }

        try {
            footstepSound.play();
        } catch (Exception e) {
            Gdx.app.error("SoundManager", "couldn't play the footstep sound");
        }
    }

    public static void stopFootsteps() {
        if (footstepSound != null && footstepSound.isPlaying()) {
            footstepSound.stop();
        }
    }

    public static void updateFootsteps(boolean moving) {
        if (!moving) {
            stopFootsteps();
            return;
        }

        playFootstep();

    }
    public static void setVolume(float volume){
        SoundManager.volume = volume;
    }
    public static float getVolume(){
        return volume;
    }
}
