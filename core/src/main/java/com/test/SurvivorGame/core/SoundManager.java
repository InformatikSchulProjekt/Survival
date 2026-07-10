package com.test.SurvivorGame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager {

    private static float volume = 1.0f;
    private static Music footstepSound;
    private static ObjectMap<String, Sound> sounds = new ObjectMap<>();

    /**
     * Spielt einen einmaligen Soundeffekt (z.B. Angriffs- oder Trefferklang) ab.
     * Lädt die angegebene Datei aus dem "sounds"-Ordner, spielt sie einmal ab
     * und setzt dabei die übergebene individuelle Lautstärke.
     * Tritt beim Laden/Abspielen ein Fehler auf (z.B. Datei nicht gefunden),
     * wird dieser abgefangen und als Fehlermeldung im Log ausgegeben,
     * damit das Spiel dadurch nicht abstürzt.
     *
     * @param fileName     Dateiname des Sounds innerhalb des Ordners "sounds/" (z.B. "hit.wav")
     * @param customVolume Lautstärke, mit der dieser Sound konkret abgespielt wird (0.0 bis 1.0)
     */
    public static void playSound(String fileName, float customVolume) {
        try {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + fileName));
            long id = sound.play();
            sound.setVolume(id, customVolume);


        } catch (Exception e) {
            Gdx.app.error("SoundManager", " konnte Sound nicht abspielen: " + fileName);
        }

    }

    /**
     * Spielt das Schrittgeräusch des Spielers ab.
     * Lädt die Musikdatei beim allerersten Aufruf einmalig (lazy loading) und
     * merkt sie sich danach in "footstepSound", damit sie nicht jedes Mal neu
     * von der Festplatte geladen werden muss. Spielt den Sound nur ab, wenn
     * er gerade nicht bereits läuft, damit sich die Schritte nicht überlagern.
     * Wird intern von {@link #updateFootsteps(boolean)} aufgerufen.
     */
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

    /**
     * Stoppt das Schrittgeräusch, falls gerade eines abgespielt wird.
     * Wird z.B. aufgerufen, sobald der Spieler stehen bleibt.
     */
    public static void stopFootsteps() {
        if (footstepSound != null && footstepSound.isPlaying()) {
            footstepSound.stop();
        }
    }

    /**
     * Aktualisiert den Schrittgeräusch-Status abhängig davon, ob sich der Spieler
     * gerade bewegt. Wird vermutlich jeden Frame aus der Update-Logik aufgerufen.
     * Bewegt sich der Spieler nicht mehr, werden die Schrittgeräusche gestoppt;
     * bewegt er sich, wird versucht, das Schrittgeräusch (weiter) abzuspielen.
     *
     * @param moving true, wenn sich der Spieler aktuell bewegt, sonst false
     */
    public static void updateFootsteps(boolean moving) {
        if (!moving) {
            stopFootsteps();
            return;
        }

        playFootstep();

    }
    /**
     * Setzt die globale Lautstärke, die für alle über den SoundManager
     * abgespielten Sounds als Referenzwert dient (z.B. für Lautstärkeregler
     * in den Einstellungen).
     *
     * @param volume neue globale Lautstärke (üblicherweise zwischen 0.0 und 1.0)
     */
    public static void setVolume(float volume){
        SoundManager.volume = volume;
    }

    /**
     * Gibt die aktuell eingestellte globale Lautstärke zurück.
     *
     * @return die aktuelle globale Lautstärke
     */
    public static float getVolume(){
        return volume;
    }
}
