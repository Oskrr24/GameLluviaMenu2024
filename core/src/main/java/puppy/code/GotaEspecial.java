package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GotaEspecial extends Gota {
    public GotaEspecial(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 3, tamaño); // Tipo 3 para gotas especiales
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
