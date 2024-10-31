package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements PantallaJuego {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private BotonMenuSimple botonReiniciar;
    private BotonMenuSimple botonSalir;
    private Color colorReiniciar = Color.GREEN;
    private Color colorSalir = Color.RED;
    private Texture fondo; // Variable para la textura de fondo

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Inicializa botones con texto y color
        botonReiniciar = new BotonMenuSimple(400, 240, 200, 50, "Reiniciar", colorReiniciar, () -> game.setScreen(new GameScreen(game)));
        botonSalir = new BotonMenuSimple(400, 180, 200, 50, "Salir", colorSalir, () -> Gdx.app.exit());
        
        // Cargar la textura de fondo
        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png"));
    }

    @Override
    public void dibujar() {
        ScreenUtils.clear(0, 0.1f, 0.2f, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        // Dibuja el fondo primero
        game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);
        
        // Dibuja los botones encima del fondo
        botonReiniciar.dibujar(game.getBatch());
        botonSalir.dibujar(game.getBatch());
        
        game.getBatch().end();
    }

    @Override
    public void manejarEntradas() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = 480 - Gdx.input.getY(); // Invertir coordenadas Y

            if (botonReiniciar.fuePresionado(x, y)) {
                botonReiniciar.ejecutarAccion();
            } else if (botonSalir.fuePresionado(x, y)) {
                botonSalir.ejecutarAccion();
            }
        }
    }

    @Override
    public void render(float delta) {
        dibujar();
        manejarEntradas();
    }

    @Override
    public void dispose() {
        fondo.dispose(); // Libera la textura de fondo
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
