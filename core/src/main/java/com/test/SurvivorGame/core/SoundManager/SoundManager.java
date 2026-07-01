package com.test.SurvivorGame.core.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

public static void playSound(String fileName) {
    try {
        Sound sound= Gdx.audio.newSound(Gdx.files.internal("sounds/"+fileName));
        sound.play();
    }
    catch (Exception e) {
        Gdx.app.error("SoundManager"," konnte Sound nicht abspielen: "+ fileName);
        }
    }
}
