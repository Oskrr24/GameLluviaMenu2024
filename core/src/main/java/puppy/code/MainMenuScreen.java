package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements PantallaJuego {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private BotonMenuSimple botonIniciar;
    private BotonMenuSimple botonTutorial;
    private BotonMenuSimple botonObjetos;
    private Texture fondo;
    private Color colorIniciar = Color.GREEN;
    private Color colorTutorial = Color.BLUE;
    private Color colorObjetos = Color.ORANGE;

    public MainMenuScreen(final GameLluviaMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png"));
        
        botonIniciar = new BotonMenuSimple(400, 240, 200, 50, "Iniciar", colorIniciar, () -> game.setScreen(new GameScreen(game)));
        botonTutorial = new BotonMenuSimple(400, 180, 200, 50, "Tutorial", colorTutorial, () -> game.setScreen(new TutorialScreen(game)));
        botonObjetos = new BotonMenuSimple(400, 120, 200, 50, "Tutorial Objetos", colorObjetos, () -> game.setScreen(new TutorialObjetosScreen(game)));
    }

    @Override
    public void dibujar() {
        ScreenUtils.clear(0, 0.1f, 0.2f, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);
        
        // Dibuja los botones
        botonIniciar.dibujar(game.getBatch());
        botonTutorial.dibujar(game.getBatch());
        botonObjetos.dibujar(game.getBatch()); // Dibuja el nuevo botón "Tutorial Objetos"
        
        game.getBatch().end();
    }

    @Override
    public void manejarEntradas() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = 480 - Gdx.input.getY();

            if (botonIniciar.fuePresionado(x, y)) {
                botonIniciar.ejecutarAccion();
            } else if (botonTutorial.fuePresionado(x, y)) {
                botonTutorial.ejecutarAccion();
            } else if (botonObjetos.fuePresionado(x, y)) {
                botonObjetos.ejecutarAccion(); // Ejecuta la acción del botón "Tutorial Objetos"
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
        fondo.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
