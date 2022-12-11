package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TheEndScreen extends ScreenAdapter {
    private final XmasGame game;
    private Stage stage;

    public TheEndScreen(XmasGame game, int puntos, boolean ganador) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        //Con el boolean que hemos extraido del la clase GameScreen si es verdadero pone el texto que ha ganado
        // y los puntos y sino pone el texto que ha perdido y los puntos
        if (ganador) {
            Label title = new Label("Has ganado!!!!!!\nHas conseguido " + puntos + " puntos", game.gameSkin, "title");
            title.setAlignment(Align.center);
            title.setY(Gdx.graphics.getHeight() * 2 / 3);
            title.setWidth(Gdx.graphics.getWidth());
            stage.addActor(title);
        } else {
            Label title = new Label("Has perdido :( :( :(\nHas conseguido " + puntos + " puntos", game.gameSkin, "title");
            title.setAlignment(Align.center);
            title.setY(Gdx.graphics.getHeight() * 2 / 3);
            title.setWidth(Gdx.graphics.getWidth());
            stage.addActor(title);
        }

        //Añadimos el boton de volver a jugar y su posicion, cuando pulsamos el boton que se nos vaya a la pantalla de inicio
        TextButton startButton = new TextButton("Volver a jugar", game.gameSkin);
        startButton.setWidth(Gdx.graphics.getWidth() / 4);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2);
        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(startButton);

        //Añadimos el boton de salir y su posicion, cuando pulsamos el boton que se nos cierre el juego
        TextButton exitButton = new TextButton("Salir", game.gameSkin);
        exitButton.setWidth(Gdx.graphics.getWidth() / 4);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 4 - exitButton.getHeight() / 2);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(exitButton);
    }

    //Metodo que se llama para mostrar la pantalla, este caso se muestra el escenario
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    //Metodo que se llama para dibujar un frame, primero se dibuja sin nada y acontinuacion el escenario
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    //Metodo que se llama para que se esconda la pantalla
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    //Metodo que se llama para liberar recursos
    @Override
    public void dispose() {
        stage.dispose();
    }
}
