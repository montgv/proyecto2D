package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends ScreenAdapter {
    private final XmasGame game;
    private Stage stage;
    private OrthographicCamera camara;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Santa santa;
    private Rudolph rudolph;
    private Elfo elfo;
    private Array<ObjetosRestan> objetosRestanArray;
    private Array<ObjetosSuman> objetosSumanArray;
    private float generadorTiempoRestan;
    private float generadorTiempoSuman;
    private Sound sonidoSumarPuntos;
    private Sound sonidoRestarPuntos;
    private Puntuacion puntuacion;

    private int personajeSeleccionado = 0;

    public GameScreen(XmasGame game, int personajeSeleccionado) {
        this.game = game;
        this.personajeSeleccionado = personajeSeleccionado;

        camara = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(camara));
        tiledMap = new TmxMapLoader().load("Mapa/tilemapXmasGame.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        generadorTiempoRestan = 0;
        generadorTiempoSuman = 0;
        sonidoSumarPuntos = Gdx.audio.newSound(Gdx.files.internal("Sonidos/sonidoSumaPuntos.wav"));
        sonidoRestarPuntos = Gdx.audio.newSound(Gdx.files.internal("Sonidos/sonidoRestaPuntos.wav"));
        puntuacion = new Puntuacion(new BitmapFont());
        puntuacion.setPosition(490, 40);
        puntuacion.puntuacion = 0;
        stage.addActor(puntuacion);


        switch (personajeSeleccionado) {
            case 0:
                santa = new Santa(393, 506);
                stage.addActor(santa);
                stage.setKeyboardFocus(santa);
                break;
            case 1:
                rudolph = new Rudolph(393, 506);
                stage.addActor(rudolph);
                stage.setKeyboardFocus(rudolph);
                break;
            case 2:
                elfo = new Elfo(393, 506);
                stage.addActor(elfo);
                stage.setKeyboardFocus(elfo);
                break;
        }

        objetosRestanArray = new Array<>();
        objetosResta();
        objetosSumanArray = new Array<>();
        objetosSuman();
    }

    private void objetosResta() {
        ObjetosRestan objeto = new ObjetosRestan(-20, MathUtils.random(69, 553));
        stage.addActor(objeto);
        objetosRestanArray.add(objeto);
    }

    private void objetosSuman() {
        ObjetosSuman objeto = new ObjetosSuman(-20, MathUtils.random(69,553));
        stage.addActor(objeto);
        objetosSumanArray.add(objeto);
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

        generadorTiempoRestan += delta;
        if (generadorTiempoRestan >= 2) {
            generadorTiempoRestan = 0;
            objetosResta();
        }

        generadorTiempoSuman += delta;
        if (generadorTiempoSuman >= 1) {
            generadorTiempoSuman = 0;
            objetosSuman();
        }

        //Desarrollo del juego
        switch (personajeSeleccionado) {
            case 0:
                for (int i = 0; i < objetosRestanArray.size; i++) {
                    if (objetosRestanArray.get(i).isVisible() && Intersector.overlaps(objetosRestanArray.get(i).getShape(), santa.getShape())) {
                        //Aqui resto los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion - 10;

                        sonidoRestarPuntos.play();
                        objetosRestanArray.get(i).setVisible(false);
                    }
                }

                for (int i = 0; i < objetosSumanArray.size; i++) {
                    if (objetosSumanArray.get(i).isVisible() && Intersector.overlaps(objetosSumanArray.get(i).getShape(), santa.getShape())) {
                        //Aqui sumo los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion + 5;

                        sonidoSumarPuntos.play();
                        objetosSumanArray.get(i).setVisible(false);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < objetosRestanArray.size; i++) {
                    if (objetosRestanArray.get(i).isVisible() && Intersector.overlaps(objetosRestanArray.get(i).getShape(), rudolph.getShape())) {
                        //Aqui resto los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion - 10;

                        sonidoRestarPuntos.play();
                        objetosRestanArray.get(i).setVisible(false);
                    }
                }

                for (int i = 0; i < objetosSumanArray.size; i++) {
                    if (objetosSumanArray.get(i).isVisible() && Intersector.overlaps(objetosSumanArray.get(i).getShape(), rudolph.getShape())) {
                        //Aqui sumo los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion + 5;

                        sonidoSumarPuntos.play();
                        objetosSumanArray.get(i).setVisible(false);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < objetosRestanArray.size; i++) {
                    if (objetosRestanArray.get(i).isVisible() && Intersector.overlaps(objetosRestanArray.get(i).getShape(), elfo.getShape())) {
                        //Aqui resto los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion - 10;

                        sonidoRestarPuntos.play();
                        objetosRestanArray.get(i).setVisible(false);
                    }
                }

                for (int i = 0; i < objetosSumanArray.size; i++) {
                    if (objetosSumanArray.get(i).isVisible() && Intersector.overlaps(objetosSumanArray.get(i).getShape(), elfo.getShape())) {
                        //Aqui sumo los puntos
                        puntuacion.puntuacion = puntuacion.puntuacion + 5;

                        sonidoSumarPuntos.play();
                        objetosSumanArray.get(i).setVisible(false);
                    }
                }
                break;
        }



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
        sonidoRestarPuntos.dispose();
        sonidoSumarPuntos.dispose();
    }
}
