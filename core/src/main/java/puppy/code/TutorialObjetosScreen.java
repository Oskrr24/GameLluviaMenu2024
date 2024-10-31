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
    private Texture gotaVidaExtra;

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
        gotaVidaExtra = new Texture(Gdx.files.internal("vidaExtra.png"));
        
    }

    @Override
  public void dibujar() {
    ScreenUtils.clear(0, 0, 0.2f, 1);
    game.getBatch().setProjectionMatrix(camera.combined);

    game.getBatch().begin();
    game.getBatch().draw(fondo, 0, 0, camera.viewportWidth, camera.viewportHeight);

    // Variables de posición inicial y espaciado para los iconos y texto descriptivo
    float xIcon = 100;           // Posición X de los iconos
    float yStart = 400;          // Posición Y inicial de los iconos
    float iconSize = 64;         // Tamaño de los iconos
    float textOffsetX = 80;      // Desplazamiento en X del texto respecto al icono
    float textOffsetY = 30;      // Ajuste de posición en Y del texto respecto al icono
    float spacing = 90;          // Espacio entre cada elemento

    // Dibuja cada elemento en la posición ajustada
    game.getBatch().draw(gotaBuenaTexture, xIcon, yStart, iconSize, iconSize);
    font.draw(game.getBatch(), "Nebulosa: +10 puntos", xIcon + textOffsetX, yStart + textOffsetY);

    game.getBatch().draw(gotaMalaTexture, xIcon, yStart - spacing, iconSize, iconSize);
    font.draw(game.getBatch(), "Meteorito: -1 vida", xIcon + textOffsetX, yStart - spacing + textOffsetY);

    game.getBatch().draw(gotaEspecial, xIcon, yStart - 2 * spacing, iconSize, iconSize);
    font.draw(game.getBatch(), "Estrella: Puntos dobles!", xIcon + textOffsetX, yStart - 2 * spacing + textOffsetY);

    game.getBatch().draw(gotaVidaExtra, xIcon, yStart - 3 * spacing, iconSize, iconSize);
    font.draw(game.getBatch(), "Satélite: Vida Extra!", xIcon + textOffsetX, yStart - 3 * spacing + textOffsetY);

    game.getBatch().draw(tarroTexture, xIcon, yStart - 4 * spacing, iconSize, iconSize);
    font.draw(game.getBatch(), "Nave: Recoge las estrellas", xIcon + textOffsetX, yStart - 4 * spacing + textOffsetY);

    // Agregar el mensaje en el lado derecho de la pantalla
    String mensaje = "Click para ir atrás";
    float mensajeX = camera.viewportWidth - 10 - font.getRegion().getRegionWidth(); // 10 px de margen
    float mensajeY = camera.viewportHeight / 2; // Centrado verticalmente

    font.draw(game.getBatch(), mensaje, mensajeX, mensajeY);

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
