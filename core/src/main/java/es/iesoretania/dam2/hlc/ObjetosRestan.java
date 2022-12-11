package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObjetosRestan extends Actor {
    private Texture imagen;
    private TextureRegion actual;

    public ObjetosRestan(float x, float y) {
        //Genero un numero aleatorio entre 0 y 3
        int aleatorio = MathUtils.random(0,3);

        //Segun sea el valor de aleatorio, a imagen le asignaremos una textura diferente de entre varios resultados
        switch (aleatorio) {
            case 0:
                imagen = new Texture(Gdx.files.internal("RestarPuntos/munieco.png"));
                break;
            case 1:
                imagen = new Texture(Gdx.files.internal("RestarPuntos/troncoGrande.png"));
                break;
            case 2:
                imagen = new Texture(Gdx.files.internal("RestarPuntos/troncoPequenio.png"));
                break;
            case 3:
                imagen = new Texture(Gdx.files.internal("RestarPuntos/troncos.png"));
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

    //Aparece el objetosRestan por la parte izquierda del mapa o pantalla
    @Override
    public void act(float delta) {
        super.act(delta);
        this.moveBy(90 * delta, 0);
    }

    //Metodo que nos ayuda a la hora de las colisiones, como hay diferentes objetos de diferente tamaño lo he
    // ajustado al al ancho mas grande y al alto mas grande
    public Rectangle getShape() {
        return new Rectangle(getX(), getY(), 32, 32);
    }
}
