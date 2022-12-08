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

        if (ganador) {
            Label title = new Label("HAS GANADO, HAS CONSEGUIDO " + puntos + " PUNTOS", game.gameSkin, "title");
            title.setAlignment(Align.center);
            title.setY(Gdx.graphics.getHeight() * 2 / 3);
            title.setWidth(Gdx.graphics.getWidth());
            stage.addActor(title);
        } else {
            Label title = new Label("HAS PERDIDO, HAS CONSEGUIDO " + puntos + " PUNTOS", game.gameSkin, "title");
            title.setAlignment(Align.center);
            title.setY(Gdx.graphics.getHeight() * 2 / 3);
            title.setWidth(Gdx.graphics.getWidth());
            stage.addActor(title);
        }

        TextButton startButton = new TextButton("VOLVER A JUGAR", game.gameSkin);
        startButton.setWidth(Gdx.graphics.getWidth() / 4);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2);
        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(startButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
