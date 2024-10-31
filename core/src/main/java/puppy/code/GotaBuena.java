package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GotaBuena extends Gota {
    public GotaBuena(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 2, tamaño);
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
        jugarSonido();
        tarro.sumarPuntos(puntosDobles ? 20 : 10);
    }
}

