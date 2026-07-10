package com.test.SurvivorGame.entity;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends GameObject {

    // rendering-relevante eigenschaften
    protected Direction facingDirection = Direction.DOWN;
    private static final float DAMAGE_FLASH_DURATION = 0.18f;
    protected float damageFlashTimer = 0f;

    // bewegung
    protected final Vector2 moveDirection = new Vector2();
    protected float movementSpeed;
    protected boolean isMoving = false;

    // status
    protected float maxHP;
    protected float currentHP;
    protected boolean alive = true;

    /**
     * Konstruktor für eine Entity (Basisklasse für Spieler, Gegner usw.).
     * Setzt Position und Größe über den Konstruktor von GameObject und
     * initialisiert Lebenspunkte sowie Bewegungsgeschwindigkeit.
     * Die aktuellen HP werden zu Beginn auf die maximalen HP gesetzt.
     *
     * @param x              Start-X-Position in der Welt
     * @param y              Start-Y-Position in der Welt
     * @param width          Breite der Entity (für Rendering/Collider)
     * @param height         Höhe der Entity (für Rendering/Collider)
     * @param maxHP          maximale Lebenspunkte der Entity
     * @param movementSpeed  Bewegungsgeschwindigkeit der Entity
     */
    public Entity(float x, float y, float width, float height, float maxHP, float movementSpeed)
    {
        super(x,y,width,height);

        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.movementSpeed = movementSpeed;
    }

    // abstrakte Methoden für Subklassen

    /**
     * Gibt zurück, in welche Richtung die Entity aktuell blickt bzw. sich zuletzt
     * bewegt hat. Wird vom Renderer genutzt, um die passende Animation
     * (front/back/left/right) auszuwählen.
     *
     * @return aktuelle Blickrichtung der Entity
     */
    public Direction getFacingDirection() {
        return facingDirection;
    }

    /**
     * Gibt zurück, ob sich die Entity aktuell bewegt.
     *
     * @return true, wenn sich die Entity gerade bewegt, sonst false
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Gibt zurück, ob die Entity sich gerade im "Schaden-Aufblitzen"-Effekt befindet
     * (z.B. rote Einfärbung nach einem Treffer). Der Effekt ist aktiv, solange
     * der interne Timer noch nicht abgelaufen ist.
     *
     * @return true, wenn der Damage-Flash-Effekt aktuell aktiv ist
     */
    public boolean isDamageFlashing() {
        return damageFlashTimer > 0f;
    }

    /**
     * Gibt den Fortschritt des Damage-Flash-Effekts als Wert zwischen 0 und 1 zurück
     * (0 = Effekt gerade erst gestartet, 1 = Effekt komplett abgeklungen).
     * Wird vom Renderer genutzt, um die Farbüberblendung stufenlos auslaufen zu lassen.
     *
     * @return normierter Fortschritt des Damage-Flash-Effekts (0.0 bis 1.0)
     */
    public float getDamageFlashProgress() {
        return Math.min(damageFlashTimer / DAMAGE_FLASH_DURATION, 1f);
    }

    // schaden
    /**
     * Fügt der Entity Schaden zu, indem die aktuellen Lebenspunkte reduziert werden,
     * und startet zusätzlich den visuellen Damage-Flash-Effekt.
     * Hinweis: hier findet noch keine Prüfung statt, ob die Entity dadurch stirbt
     * (currentHP könnte theoretisch unter 0 fallen) – das muss ggf. an anderer
     * Stelle (z.B. in einer Subklasse) behandelt werden.
     *
     * @param damage Menge an Schaden, die von den aktuellen HP abgezogen wird
     */
    public void takeDamage(float damage) {
        currentHP -= damage;
        startDamageFlash();
    }

    /**
     * Heilt die Entity um den angegebenen Betrag, ohne dabei die maximalen
     * Lebenspunkte zu überschreiten.
     *
     * @param amount Menge an Lebenspunkten, die geheilt werden sollen
     */
    public void heal(float amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
    }

    /**
     * Startet (bzw. setzt zurück) den Damage-Flash-Timer auf seine volle Dauer.
     * Wird intern von {@link #takeDamage(float)} aufgerufen.
     */
    protected void startDamageFlash() {
        damageFlashTimer = DAMAGE_FLASH_DURATION;
    }

    /**
     * Zählt den Damage-Flash-Timer jeden Frame um die vergangene Zeit herunter,
     * bis er 0 erreicht hat (Effekt ist dann beendet). Muss von Subklassen
     * in ihrer eigenen update()-Methode aufgerufen werden, damit der
     * Aufblitz-Effekt tatsächlich wieder ausläuft.
     *
     * @param deltaTime vergangene Zeit seit dem letzten Frame in Sekunden
     */
    protected void updateDamageFlash(float deltaTime) {
        if (damageFlashTimer > 0f) {
            damageFlashTimer = Math.max(0f, damageFlashTimer - deltaTime);
        }
    }

    // richtungs enum
    public enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }
    /**
     * Aktualisiert die Bewegungsrichtung der Entity und leitet daraus die
     * Blickrichtung (facingDirection) ab.
     * Ist der übergebene Vektor (0,0), wird die Entity als "nicht bewegend" markiert
     * und die Blickrichtung bleibt unverändert.
     * Andernfalls wird anhand des dominanten Achsenanteils entschieden, ob die
     * Entity horizontal (LEFT/RIGHT) oder vertikal (UP/DOWN) blickt – es wird
     * also immer nur eine der vier Hauptrichtungen gewählt, auch bei diagonaler Bewegung.
     *
     * @param moveDirectionUpdate neuer Bewegungsvektor (z.B. aus der Eingabe oder KI)
     */
    public void updateMoveDirection(Vector2 moveDirectionUpdate) {
        moveDirection.set(moveDirectionUpdate);
        isMoving = !moveDirection.isZero();
        if (!isMoving) return;

        if (Math.abs(moveDirection.x) > Math.abs(moveDirection.y)) {
            if (moveDirection.x > 0) {
                facingDirection = Direction.RIGHT;
            } else {
                facingDirection = Direction.LEFT;
            }
        } else {
            if (moveDirection.y > 0) {
                facingDirection = Direction.UP;
            } else {
                facingDirection = Direction.DOWN;
            }
        }
    }

}
