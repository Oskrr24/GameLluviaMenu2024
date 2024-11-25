package puppy.code.Gota;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.Tarro;

public class GotaMala extends Gota {
    public GotaMala(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 1, tamaño);
    }

    @Override
    public void caer(float deltaTime) {
        area.y -= 300 * deltaTime;
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y, tamaño, tamaño);
    }

    @Override
    public void manejarColision(Tarro tarro, boolean puntosDobles) {
        tarro.dañar();
    }
}
