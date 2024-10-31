package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialObjetosScreen implements PantallaJuego {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private Texture fondo;
    private BitmapFont font;
    private Texture gotaBuenaTexture;
    private Texture gotaMalaTexture;
    private Texture tarroTexture;
    private Texture gotaEspecial;

    public TutorialObjetosScreen(final GameLluviaMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png"));
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);

        gotaBuenaTexture = new Texture(Gdx.files.internal("drop.png"));
        gotaMalaTexture = new Texture(Gdx.files.internal("dropBad.png"));
        tarroTexture = new Texture(Gdx.files.internal("bucket.png"));
        gotaEspecial = new Texture(Gdx.files.internal("estrella.png"));
    }

    @Override
    public void dibujar() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Mostrar texturas y descripciones de los objetos
        game.getBatch().draw(gotaBuenaTexture, 100, 400, 64, 64);
        font.draw(game.getBatch(), "Nebulosa: +10 puntos", 180, 430);

        game.getBatch().draw(gotaMalaTexture, 100, 300, 64, 64);
        font.draw(game.getBatch(), "Meteorito: -1 vida", 180, 330);

        game.getBatch().draw(gotaEspecial, 100, 200, 64, 64);
        font.draw(game.getBatch(), "Estrella: Puntos dobles!", 180, 230);
        
        game.getBatch().draw(tarroTexture, 100, 100, 64, 64);
        font.draw(game.getBatch(), "Nave: Recoge las estrellas", 180, 130);

        game.getBatch().end();
    }

    @Override
    public void manejarEntradas() {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game)); // Volver al menú principal al tocar
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
        gotaBuenaTexture.dispose();
        gotaMalaTexture.dispose();
        tarroTexture.dispose();
        gotaEspecial.dispose(); // Asegúrate de liberar la textura de la gota especial
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
