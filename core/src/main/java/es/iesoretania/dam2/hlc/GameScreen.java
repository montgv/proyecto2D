package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends ScreenAdapter {
    private final XmasGame game;
    private Stage stage;
    private OrthographicCamera camara;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Santa santa;

    public GameScreen(XmasGame game) {
        this.game = game;
        camara = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(camara));
        tiledMap = new TmxMapLoader().load("tilemapXmasGame.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        santa = new Santa(camara.viewportWidth / 2, camara.viewportHeight / 2);

        stage.addActor(santa);
        stage.setKeyboardFocus(santa);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Desarrollo del juego


        mapRenderer.setView(camara);
        mapRenderer.render();
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
        mapRenderer.dispose();
        tiledMap.dispose();
    }
}
