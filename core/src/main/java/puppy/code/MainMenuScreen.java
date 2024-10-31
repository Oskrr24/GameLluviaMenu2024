package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements PantallaJuego {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private BotonMenuSimple botonIniciar;
    private BotonMenuSimple botonTutorial;
    private BotonMenuSimple botonObjetos;
    private Texture fondo;
    private BitmapFont font;
    private GlyphLayout titleLayout; // Usado para centrar el título
    private Color colorIniciar = Color.GREEN;
    private Color colorTutorial = Color.BLUE;
    private Color colorObjetos = Color.ORANGE;
    private final String titulo = "Bienvenido a AstroCatch!"; // Texto del título del juego

    public MainMenuScreen(final GameLluviaMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Inicializa el fondo y los botones
        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png"));
        botonIniciar = new BotonMenuSimple(100, 200, 200, 50, "Iniciar", colorIniciar, () -> game.setScreen(new GameScreen(game)));
        botonTutorial = new BotonMenuSimple(100, 150, 200, 50, "Tutorial", colorTutorial, () -> game.setScreen(new TutorialScreen(game)));
        botonObjetos = new BotonMenuSimple(100, 100, 200, 50, "Tutorial Objetos", colorObjetos, () -> game.setScreen(new TutorialObjetosScreen(game)));

        // Inicializa la fuente y el layout para el título
        font = new BitmapFont();
        font.getData().setScale(2); // Escala la fuente para el título
        titleLayout = new GlyphLayout(font, titulo);
    }

    @Override
    public void dibujar() {
        ScreenUtils.clear(0, 0.1f, 0.2f, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        // Dibuja el fondo
        game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Dibuja el título centrado en la parte superior
        float titleX = (camera.viewportWidth - titleLayout.width) / 2;
        float titleY = camera.viewportHeight - 50; // Ajusta para estar en la parte superior
        font.setColor(Color.WHITE); // Cambia el color del título si es necesario
        font.draw(game.getBatch(), titulo, titleX, titleY);

        // Dibuja los botones
        botonIniciar.dibujar(game.getBatch());
        botonTutorial.dibujar(game.getBatch());
        botonObjetos.dibujar(game.getBatch());

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
                botonObjetos.ejecutarAccion();
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
        font.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
