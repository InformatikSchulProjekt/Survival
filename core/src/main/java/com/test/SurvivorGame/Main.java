package com.test.SurvivorGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private Batch batch; // ist so ein sammler für sprites die dann an die GPU gesendet werden

    @Override
    public void create() // wird genau einmal aufgerufen, wenn das Spiel startet.
    {
        batch = new SpriteBatch(); // ist kurzgesagt ein auf 2d speziallisierte Batch-Art

        setScreen(new GamePlayScreen(this)); //das setzt den Screen den wir nutzen wollen
    }

    @Override
    public void dispose() // Speicher und GPU-Ressourcen sauber freigeben
    {
        super.dispose();
        batch.dispose();
    }

    public Batch getBatch()
    {
        return batch;
    }
}
//test1
