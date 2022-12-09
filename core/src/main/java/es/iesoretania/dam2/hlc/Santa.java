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


public class Santa extends Actor {
    enum VerticalMovement {UP, NONE, DOWN}
    enum HorizontalMovement {LEFT, NONE, RIGHT}

    HorizontalMovement horizontalMovement;
    VerticalMovement verticalMovement;

    static Texture imagen = new Texture(Gdx.files.internal("santa.png"));

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

    public Santa() {
        if (reposoAbajo == null) {
            reposoAbajo = new TextureRegion(imagen, 96,0,32,32);
            reposoIzq = new TextureRegion(imagen, 96,32,32,32);
            reposoDer = new TextureRegion(imagen, 96,64,32,32);
            reposoArriba = new TextureRegion(imagen, 96,96,32,32);

            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,0,32,32),
                    new TextureRegion(imagen, 32,0,32,32),
                    new TextureRegion(imagen, 64,0,32,32)
            };

            animationAbajo = new Animation<>(0.0999f, movimiento);

            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,32,32,32),
                    new TextureRegion(imagen, 32,32,32,32),
                    new TextureRegion(imagen, 64,32,32,32)
            };

            animationIzq = new Animation<>(0.0999f, movimiento);

            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,64,32,32),
                    new TextureRegion(imagen, 32,64,32,32),
                    new TextureRegion(imagen, 64,64,32,32)
            };

            animationDer = new Animation<>(0.0999f, movimiento);

            movimiento = new TextureRegion[] {
                    new TextureRegion(imagen, 0,96,32,32),
                    new TextureRegion(imagen, 32,96,32,32),
                    new TextureRegion(imagen, 64,96,32,32)
            };

            animationArriba = new Animation<>(0.0999f, movimiento);
        }

        actual = reposoAbajo;

        addListener(new SantaListener());

        setX(getStage().getViewport().getScreenWidth() / 2);
        setY(getStage().getViewport().getScreenHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(actual, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;

        if (verticalMovement == VerticalMovement.UP) {
            this.moveBy(0, 100 * delta);
            actual = animationArriba.getKeyFrame(stateTime, true);
            animationActual = animationArriba;
        }

        if (verticalMovement == VerticalMovement.DOWN) {
            this.moveBy(0, -100 * delta);
            actual = animationAbajo.getKeyFrame(stateTime, true);
            animationActual = animationAbajo;
        }

        if (horizontalMovement == HorizontalMovement.LEFT) {
            this.moveBy(-100 * delta, 0);
            actual = animationIzq.getKeyFrame(stateTime, true);
            animationActual = animationIzq;
        }

        if (horizontalMovement == HorizontalMovement.RIGHT) {
            this.moveBy(100 * delta, 0);
            actual = animationDer.getKeyFrame(stateTime, true);
            animationActual = animationDer;
        }

        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.NONE) {
            if (animationActual == animationAbajo) actual = reposoAbajo;
            if (animationActual == animationArriba) actual = reposoArriba;
            if (animationActual == animationDer) actual = reposoDer;
            if (animationActual == animationIzq) actual = reposoIzq;
        }
    }

    public Rectangle getShape() {
        return new Rectangle(getX(), getY(), 32, 32);
    }

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
