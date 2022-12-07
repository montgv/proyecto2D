package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Proyecto2DGame extends Game {
	BitmapFont font;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	public Skin gameSkin;

	@Override
	public void create() {
		font = new BitmapFont();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		gameSkin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
	}
}