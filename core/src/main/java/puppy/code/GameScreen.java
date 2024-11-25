package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


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
        this.batch = game.getBatch();
        this.font = game.getFont();


        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png")); // Reemplaza "background.png" con el nombre de tu imagen de fondo


        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png"));

        hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaEspecial = new Texture(Gdx.files.internal("estrella.png"));
        Texture gotaVidaExtra = new Texture(Gdx.files.internal("vidaExtra.png")); // Textura vida extra

        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        lluvia = new Lluvia(gota, gotaMala, gotaEspecial,dropSound, rainMusic, gotaVidaExtra); // Pasar la gota vida extra a lluvia

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            pause();
            return;
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();

            if (!lluvia.actualizarMovimiento(batch, tarro)) {
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());

                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        lluvia.continuar();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {

        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

        tarro.destruir();
        lluvia.destruir();
        hurtSound.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        fondo.dispose();
    }

}
