package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tiempo extends Actor {
    public int tiempo;
    private BitmapFont font;

    public Tiempo(BitmapFont font) {
        this.font = font;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Tiempo: " + tiempo, getX(), getY());
    }
}
