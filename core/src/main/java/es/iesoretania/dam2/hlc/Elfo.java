package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Elfo extends Actor {
    enum VerticalMovement {UP, NONE, DOWN}
    enum HorizontalMovement {LEFT, NONE, RIGHT}

    HorizontalMovement horizontalMovement;
    VerticalMovement verticalMovement;

    static Texture imagen = new Texture(Gdx.files.internal("elfoGrande.png"));

    private TextureRegion reposoArriba;
    private TextureRegion reposoAbajo;
    private TextureRegion reposoIzq;
    private TextureRegion reposoDer;

    private TextureRegion[] movimiento;

    private TextureRegion actual;

    private Animation<TextureRegion> animationArriba;
    private Animation<TextureRegion> animationAbajo;
    private Animation<TextureRegion> animationIzq;
    private Animation<TextureRegion> animationDer;
    private Animation<TextureRegion> animationActual;

    private float stateTime = 0f;

    public Elfo(float x, float y) {
        //Cargamos las diferentes texturas de esta manera para solo tenerlo que hacer una vez y que sea optimo
        if (reposoAbajo == null) {
            reposoAbajo = new TextureRegion(imagen, 96,0,32,48);
            reposoIzq = new TextureRegion(imagen, 96,48,32,48);
            reposoDer = new TextureRegion(imagen, 96,96,32,48);
            reposoArriba = new TextureRegion(imagen, 96,144,32,48);

            //Cargamos un array de texturas, las diferentes texturas cuando va hacia abajo para que realice la animacion
            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,0,32,48),
                    new TextureRegion(imagen, 32,0,32,48),
                    new TextureRegion(imagen, 64,0,32,48)
            };
            animationAbajo = new Animation<>(0.0999f, movimiento);

            //Cargamos un array de texturas, las diferentes texturas cuando va hacia arriba para que realice la animacion
            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,144,32,48),
                    new TextureRegion(imagen, 32,144,32,48),
                    new TextureRegion(imagen, 64,144,32,48)
            };
            animationArriba = new Animation<>(0.0999f, movimiento);

            //Cargamos un array de texturas, las diferentes texturas cuando va hacia la izquierda para que realice la animacion
            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,48,32,48),
                    new TextureRegion(imagen, 32,48,32,48),
                    new TextureRegion(imagen, 64,48,32,48)
            };
            animationIzq = new Animation<>(0.0999f, movimiento);

            //Cargamos un array de texturas, las diferentes texturas cuando va hacia la derecha para que realice la animacion
            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,96,32,48),
                    new TextureRegion(imagen, 32,96,32,48),
                    new TextureRegion(imagen, 64,96,32,48)
            };
            animationDer = new Animation<>(0.0999f, movimiento);
        }
        //Indicamos la posicion actual en ese momento
        actual = reposoAbajo;
        //
        addListener(new SantaListener());
        //Asignamos valor a la posicion x e y
        setX(x);
        setY(y);
    }

    //Dibujamos la textura en la posicion x e y
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(actual, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;

        //Indicamos al actor que tiene que hacer cuando pulsamos esa tecla
        if (verticalMovement == VerticalMovement.UP) {
            this.moveBy(0, 100 * delta);
            actual = animationArriba.getKeyFrame(stateTime, true);
            animationActual = animationArriba;
        }

        //Indicamos al actor que tiene que hacer cuando pulsamos esa tecla
        if (verticalMovement == VerticalMovement.DOWN) {
            this.moveBy(0, -100 * delta);
            actual = animationAbajo.getKeyFrame(stateTime, true);
            animationActual = animationAbajo;
        }

        //Indicamos al actor que tiene que hacer cuando pulsamos esa tecla
        if (horizontalMovement == HorizontalMovement.LEFT) {
            this.moveBy(-100 * delta, 0);
            actual = animationIzq.getKeyFrame(stateTime, true);
            animationActual = animationIzq;
        }

        //Indicamos al actor que tiene que hacer cuando pulsamos esa tecla
        if (horizontalMovement == HorizontalMovement.RIGHT) {
            this.moveBy(100 * delta, 0);
            actual = animationDer.getKeyFrame(stateTime, true);
            animationActual = animationDer;
        }

        //Indicamos al actor que posicion tiene cuando no pulsamos esas teclas
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.NONE) {
            if (animationActual == animationAbajo) actual = reposoAbajo;
            if (animationActual == animationArriba) actual = reposoArriba;
            if (animationActual == animationDer) actual = reposoDer;
            if (animationActual == animationIzq) actual = reposoIzq;
        }

        //Indicamos al actor que no se salga de los limites de la pantalla en el eje x y en el eje y que en nuestro
        //caso no puede cruzar el mar y tampoco puede pisar la zona de la casa (setY(553)) y los arboles (setY(69))
        if (getX() < 0) setX(0);
        if (getX() + 32 > 800) setX(800 - 32);
        if (getY() < 69) setY(69);
        if (getY() > 553) setY(553);
    }

    //Metodo que nos ayuda a la hora de las colisiones
    public Rectangle getShape() {
        return new Rectangle(getX(), getY(), 32, 48);
    }

    //
    class SantaListener extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.DOWN:
                    verticalMovement = VerticalMovement.DOWN;
                    break;
                case Input.Keys.UP:
                    verticalMovement = VerticalMovement.UP;
                    break;
                case Input.Keys.LEFT:
                    horizontalMovement = HorizontalMovement.LEFT;
                    break;
                case Input.Keys.RIGHT:
                    horizontalMovement = HorizontalMovement.RIGHT;
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.DOWN:
                    if (verticalMovement == VerticalMovement.DOWN) {
                        verticalMovement = VerticalMovement.NONE;
                    }
                    break;
                case Input.Keys.UP:
                    if (verticalMovement == VerticalMovement.UP) {
                        verticalMovement = VerticalMovement.NONE;
                    }
                    break;
                case Input.Keys.LEFT:
                    if (horizontalMovement == HorizontalMovement.LEFT) {
                        horizontalMovement = HorizontalMovement.NONE;
                    }
                    break;
                case Input.Keys.RIGHT:
                    if (horizontalMovement == HorizontalMovement.RIGHT) {
                        horizontalMovement = HorizontalMovement.NONE;
                    }
                    break;
            }
            return true;
        }
    }
}
