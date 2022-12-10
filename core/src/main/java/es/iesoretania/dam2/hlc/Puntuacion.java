package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Puntuacion extends Actor {
    public int puntuacion;
    private BitmapFont font;

    public Puntuacion(BitmapFont font) {
        this.font = font;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Puntuación: " + puntuacion, getX(), getY());
    }
}
