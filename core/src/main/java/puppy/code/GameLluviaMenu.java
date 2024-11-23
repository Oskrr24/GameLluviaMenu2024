package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluviaMenu extends Game {

    // Instancia única de la clase (Singleton)
    private static GameLluviaMenu instance;

    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;

    // Constructor privado para evitar instanciación externa
    private GameLluviaMenu() {}

    // Método estático para obtener la única instancia
    public static GameLluviaMenu getInstance() {
        if (instance == null) {
            instance = new GameLluviaMenu();
        }
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // usa la fuente Arial predeterminada de libGDX
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // importante!
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHigherScore() {
        return higherScore;
    }

    public void setHigherScore(int higherScore) {
        this.higherScore = higherScore;
    }
}
