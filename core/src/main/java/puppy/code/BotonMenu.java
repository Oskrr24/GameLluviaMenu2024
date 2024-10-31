package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface BotonMenu {
    void dibujar(SpriteBatch batch);
    boolean fuePresionado(float x, float y);
    void ejecutarAccion();
}
