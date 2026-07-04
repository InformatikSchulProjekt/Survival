package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.test.SurvivorGame.entity.GameObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;


public abstract class AbilityObject extends GameObject{

    protected float rotation;

    public AbilityObject(float x, float y, float w, float h, Texture texture) {
        super(x, y, w, h, texture);
    }

    public AbilityObject(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public abstract boolean getExpired();

    public abstract float getDamage(); //damage aus der ability steuerungs klasse

    public abstract void onHit(Enemy enemy);

    @Override
    public boolean overlaps(GameObject other)
    {
        return Intersector.overlapConvexPolygons(getRotatedCollider(), getRectanglePolygon(other.getCollider()));
    }

    @Override
    public void draw(Batch batch)
    {
        if(texture == null) return;

        batch.draw(texture, collider.getX(), collider.getY(), collider.getWidth() / 2, collider.getHeight() / 2, collider.getWidth(), collider.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false
        );
    }

    @Override
    public void drawCollider(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.polygon(getRotatedCollider().getTransformedVertices());
    }

    private Polygon getRotatedCollider()
    {
        Polygon polygon = getRectanglePolygon(collider);
        polygon.setOrigin(collider.width / 2, collider.height / 2);
        polygon.setRotation(rotation);
        return polygon;
    }

    private Polygon getRectanglePolygon(Rectangle rectangle)
    {
        Polygon polygon = new Polygon(new float[] {
            0, 0,
            rectangle.width, 0,
            rectangle.width, rectangle.height,
            0, rectangle.height
        });
        polygon.setPosition(rectangle.x, rectangle.y);
        return polygon;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }
}
