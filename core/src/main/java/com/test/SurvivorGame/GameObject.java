package com.test.SurvivorGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;


public abstract class GameObject { //abstract weil es ja kein "Ding" namens Game Object gibt

    protected final Rectangle collider;
    protected Texture texture;

    public GameObject(float x, float y, float w, float h, Texture texture) {
        this.collider =  new Rectangle(x,y,w,h);
        this.texture = texture;
    }

    public GameObject(float x, float y, float w, float h) { // alternativer Konstruktor falls es nur ein "Bereichtaster" ist oder sowas in der Art
        this.collider =  new Rectangle(x,y,w,h);
    }

    public boolean overlaps(GameObject other)
    {
        return collider.overlaps(other.collider);
    }

    public void draw(Batch batch)
    {
        if(texture == null) return;

        batch.draw(texture, collider.x, collider.y, collider.width, collider.height); // eig wäre besser wenn textur unabhängig von collider-größe gezeichnet wird,
    }                                                                           // weil hier grad die texture auf die collider-größe gestretched wird, solange man nicht was einbaut aber so is eindeutig simpler

    abstract void update(float deltaTime);
}

