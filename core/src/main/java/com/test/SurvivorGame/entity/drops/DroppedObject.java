package com.test.SurvivorGame.entity.drops;

import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.entity.GameObject;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.maps.GameMap;

public abstract class DroppedObject extends GameObject {
    private float despawnTimer;
    private boolean despawned = false;
    private final Player player;

    public DroppedObject(float x, float y, float w, float h, Texture texture, Player player) {
        super(x, y, w, h, texture);
        this.player = player;
        this.despawnTimer = getDespawnTime();
    }

    protected abstract float getDespawnTime();

    public boolean isDespawned() {
        return despawned;
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        if(overlaps(player)) { // => Spieler berührt also sammelt Drop ein
            player.getPlayerState().collectDrop(this);
        }

        if (despawned) {
            return;
        }

        despawnTimer -= deltaTime;

        if (despawnTimer <= 0f) {
            despawned = true;
        }
    }
}
