package com.test.SurvivorGame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input;
public class SoundManager  {

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
