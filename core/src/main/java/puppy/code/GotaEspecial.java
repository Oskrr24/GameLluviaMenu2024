package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GotaEspecial extends Gota {
    private static final float TAMANO_GOTA = 50f;

    public GotaEspecial(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 3, tamaño);
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
    }

    public static GotaEspecial generarGotaEspecial(Texture textura, Sound sonido, Array<Gota> gotasExistentes) {
        GotaEspecial nuevaGotaEspecial = new GotaEspecial(textura, sonido, TAMANO_GOTA);
        float xPos;
        float yPos = 480;

        do {
            xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaEspecial.getArea().x = xPos;
            nuevaGotaEspecial.getArea().y = yPos;
        } while (hayColisionConGotas(nuevaGotaEspecial, gotasExistentes));

        return nuevaGotaEspecial;
    }

    private static boolean hayColisionConGotas(Gota nuevaGota, Array<Gota> gotasExistentes) {
        for (Gota gota : gotasExistentes) {
            if (gota.getArea().overlaps(nuevaGota.getArea())) {
                return true;
            }
        }
        return false;
    }
}
