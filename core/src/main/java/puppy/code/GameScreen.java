package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;       
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;
    private Sound hurtSound; // Mantener referencias para liberarlas en dispose
    private Sound dropSound;
    private Music rainMusic;
    private Texture fondo;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch(); // Usar el batch proporcionado por game
        this.font = game.getFont();
        
        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png")); // Reemplaza "background.png" con el nombre de tu imagen de fondo

        // Cargar texturas y sonidos
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaEspecial = new Texture(Gdx.files.internal("estrella.png")); // Textura para la gota especial
        Texture gotaVidaExtra = new Texture(Gdx.files.internal("vidaExtra.png")); // Textura vida extra
        
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        lluvia = new Lluvia(gota, gotaMala, gotaEspecial,dropSound, rainMusic, gotaVidaExtra); // Pasar la gota vida extra a lluvia
        
        // Configurar cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Inicialización de objetos
        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        // Verificar si la tecla ESC se presiona para pausar el juego
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            pause(); // Llama al método pause() para activar el menú de pausa
            return; // Evita que el código continúe ejecutándose después de pausar
        }

        // Actualizar matrices de la cámara
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibuja la imagen de fondo
        batch.draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Dibuja textos y elementos de juego
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        if (!tarro.estaHerido()) {
            // Movimiento del tarro desde teclado
            tarro.actualizarMovimiento();

            // Caída de la lluvia
            if (!lluvia.actualizarMovimiento(tarro)) {
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());

                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch); // Asegúrate de que esta función dibuje todas las gotas correctamente

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Este método está vacío pero se puede usar para ajustar la cámara si es necesario
    }

    @Override
    public void show() {
        // Continuar la música de lluvia cuando la pantalla se muestra
        lluvia.continuar();
    }

    @Override
    public void hide() {
        // Método vacío; podemos pausar la música si es necesario o realizar otras acciones
    }

    @Override
    public void pause() {
        // Pausar la música y cambiar a pantalla de pausa
        lluvia.pausar();
        // Cambiar a la pantalla de pausa
        game.setScreen(new PausaScreen(game, this)); // 'this' ahora es válido
    }

    @Override
    public void resume() {
        // Retomar cualquier configuración pausada en esta clase
    }

    @Override
    public void dispose() {
        // Liberar recursos de tarro, lluvia y sonidos
        tarro.destruir();
        lluvia.destruir();
        hurtSound.dispose(); // Liberar el sonido de daño
        dropSound.dispose(); // Liberar el sonido de gota
        rainMusic.dispose(); // Liberar la música de lluvia
        fondo.dispose();
    }
}
