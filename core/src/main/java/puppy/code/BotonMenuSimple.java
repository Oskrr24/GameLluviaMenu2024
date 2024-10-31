package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class BotonMenuSimple implements BotonMenu {
    private float x, y, width, height;
    private String texto;
    private Runnable accion;
    private Color color;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public BotonMenuSimple(float x, float y, float width, float height, String texto, Color color, Runnable accion) {
        this.x = x - width / 2;  // Centrado horizontal
        this.y = y - height / 2; // Centrado vertical
        this.width = width;
        this.height = height;
        this.texto = texto;
        this.color = color;
        this.accion = accion;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Dibujar el botón rectangular
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();

        // Dibujar el texto en el centro del botón
        font.setColor(Color.WHITE);
        font.draw(batch, texto, x + width / 2, y + height / 2 + font.getCapHeight() / 2, 0, Align.center, false);
    }

    @Override
    public boolean fuePresionado(float px, float py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public void ejecutarAccion() {
        if (accion != null) {
            accion.run();
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
