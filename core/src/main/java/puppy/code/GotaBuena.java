package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GotaBuena extends Gota {
    public GotaBuena(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 2, tamaño); // Tipo 2 para gotas buenas
    }

    @Override
    public void caer(float deltaTime) {
        area.y -= 300 * deltaTime; // Movimiento hacia abajo
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y, tamaño, tamaño); // Dibuja la gota
    }
}

