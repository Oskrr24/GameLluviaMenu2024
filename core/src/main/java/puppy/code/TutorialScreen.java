package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements PantallaJuego {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private Texture fondo;
    private BitmapFont font;

    public TutorialScreen(final GameLluviaMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        fondo = new Texture(Gdx.files.internal("fondo-juego-2.png")); // Asegúrate de tener la imagen en la carpeta assets
        font = new BitmapFont(); // Fuente para mostrar texto
        font.getData().setScale(2.0f); // Escala 2x del tamaño original

        font.setColor(Color.WHITE);
    }

    @Override
    public void dibujar() {
        ScreenUtils.clear(0, 0, 0, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        // Dibuja el fondo
        game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Muestra los controles y la información del tutorial
        font.draw(game.getBatch(), "Controles:", 100, 350);
        font.draw(game.getBatch(), "- Mover nave: flecha izquierda / derecha", 100, 300);
        font.draw(game.getBatch(), "- Pausar juego: ESC", 100, 250);
        font.draw(game.getBatch(), "- Recoger gotas buenas: Evita las malas!", 100, 200);

        font.draw(game.getBatch(), "Toque la pantalla para volver al menú", 100, 100);
        
        game.getBatch().end();
    }

    @Override
    public void manejarEntradas() {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game)); // Vuelve al menú principal al tocar la pantalla
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
