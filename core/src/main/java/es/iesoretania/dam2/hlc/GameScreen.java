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
    private Tiempo tiempo;
    private float generadorTiempo;
    private boolean ganador;

    private int personajeSeleccionado = 0;

    public GameScreen(XmasGame game, int personajeSeleccionado) {
        this.game = game;
        this.personajeSeleccionado = personajeSeleccionado;

        //Asignamos valor a la camara y al stage que le pasamos la camara tambien
        camara = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(camara));
        //Asignamos valor al mapa
        tiledMap = new TmxMapLoader().load("Mapa/tilemapXmasGame.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //Variables que asigna cero y nos va a servir de temporizador
        generadorTiempoRestan = 0;
        generadorTiempoSuman = 0;
        generadorTiempo = 0;
        //Asignamos valor a los diferentes sonidos
        sonidoSumarPuntos = Gdx.audio.newSound(Gdx.files.internal("Sonidos/sonidoSumaPuntos.wav"));
        sonidoRestarPuntos = Gdx.audio.newSound(Gdx.files.internal("Sonidos/sonidoRestaPuntos.wav"));

        //Llamamos al actor puntuacion, le damos una posicion, un valor y lo añadimos al stage
        puntuacion = new Puntuacion(new BitmapFont());
        puntuacion.setPosition(490, 40);
        puntuacion.puntuacion = 0;
        stage.addActor(puntuacion);

        //Llamamos al actor tiempo, le damos una posicion, un valor y lo añadimos al stage
        tiempo = new Tiempo(new BitmapFont());
        tiempo.setPosition(150, 40);
        tiempo.tiempo = 60;
        stage.addActor(tiempo);

        //Segun sea el personaje seleccionado que proviene del MenuScreen, ponemos los diferentes casos
        //Llamamos al actor, lo posicionamos, lo incorporamos al stage y le decimos que capture los eventos del teclado
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

        //Asignamos valor a los array y llamamos al esos dos metodos
        objetosRestanArray = new Array<>();
        objetosResta();
        objetosSumanArray = new Array<>();
        objetosSuman();
    }

    //Metodo que genera objetos que restan puntos de forma aleatoria por el mapa pero sin pasar del agua
    // y del bosque y la casa, lo añade al stage y al array de objetos
    private void objetosResta() {
        ObjetosRestan objeto = new ObjetosRestan(-20, MathUtils.random(69, 553));
        stage.addActor(objeto);
        objetosRestanArray.add(objeto);
    }

    //Metodo que genera objetos que suman puntos de forma aleatoria por el mapa pero sin pasar del agua
    // y del bosque y la casa, lo añade al stage y al array de objetos
    private void objetosSuman() {
        ObjetosSuman objeto = new ObjetosSuman(-20, MathUtils.random(69,553));
        stage.addActor(objeto);
        objetosSumanArray.add(objeto);
    }

    //Metodo que se llama para mostrar la pantalla, este caso se muestra el escenario
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    //Metodo que se llama para dibujar un frame, primero se dibuja sin nada y acontinuacion esta comentado en el codigo
    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Generamos un temporizador, donde cada dos segundos genera un objectoResta
        generadorTiempoRestan += delta;
        if (generadorTiempoRestan >= 2) {
            objetosResta();
            generadorTiempoRestan = 0;
        }

        //Generamos un temporizador, donde cada segundo genera un objectoSuma
        generadorTiempoSuman += delta;
        if (generadorTiempoSuman >= 1) {
            objetosSuman();
            generadorTiempoSuman = 0;
        }

        //Generamos un temporizador que empieza en 60, donde va restando tiempo cada segundo que pasa
        generadorTiempo += delta;
        if (generadorTiempo >= 1) {
            tiempo.tiempo -= 1;
            generadorTiempo = 0;
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

        if (tiempo.tiempo == 0) {
            if (puntuacion.puntuacion > 0) {
                ganador = true;
                game.setScreen(new TheEndScreen(game, puntuacion.puntuacion, ganador));
            } else {
                ganador = false;
                game.setScreen(new TheEndScreen(game, puntuacion.puntuacion, ganador));
            }
        }

        //Dibujamos el mapa
        mapRenderer.setView(camara);
        mapRenderer.render();
        //Dibujamos al stage
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
        mapRenderer.dispose();
        tiledMap.dispose();
        sonidoRestarPuntos.dispose();
        sonidoSumarPuntos.dispose();
    }
}
