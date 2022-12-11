package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObjetosSuman extends Actor {
    private Texture imagen;
    private TextureRegion actual;

    public ObjetosSuman(float x, float y) {
        //Genero un numero aleatorio entre 0 y 4
        int aleatorio = MathUtils.random(0,4);

        //Segun sea el valor de aleatorio, a imagen le asignaremos una textura diferente de entre varios resultados
        switch (aleatorio) {
            case 0:
                imagen = new Texture(Gdx.files.internal("SumarPuntos/bastonGrande.png"));
                break;
            case 1:
                imagen = new Texture(Gdx.files.internal("SumarPuntos/bastonPequenio.png"));
                break;
            case 2:
                imagen = new Texture(Gdx.files.internal("SumarPuntos/regaloAzul.png"));
                break;
            case 3:
                imagen = new Texture(Gdx.files.internal("SumarPuntos/regaloRojo.png"));
                break;
            case 4:
                imagen = new Texture(Gdx.files.internal("SumarPuntos/regaloVerde.png"));
                break;
        }
        //Asignamos a actual la imagen que corresponda despues del switch
        actual = new TextureRegion(imagen);
        //Asignamos valor a la posicion x e y
        setX(x);
        setY(y);
    }

    //Dibujamos la textura en la posicion x e y
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(actual, getX(), getY());
    }

    //Aparece el objetosSuman por la parte izquierda del mapa o pantalla
    @Override
    public void act(float delta) {
        super.act(delta);
        this.moveBy(90 * delta, 0);
    }

    //Metodo que nos ayuda a la hora de las colisiones, como hay diferentes objetos de diferente tamaño lo he
    // ajustado al al ancho mas grande y al alto mas grande
    public Rectangle getShape() {
        return new Rectangle(getX(), getY(), 16, 32);
    }
}
