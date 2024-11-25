package puppy.code.Gota;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import puppy.code.Tarro;

public class GotaVidaExtra extends Gota {
    private static final float TAMANO_GOTA = 50f;

    public GotaVidaExtra(Texture textura, Sound sonido, float tamaño) {
        super(textura, sonido, 4, tamaño);
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
        tarro.aumentarVida();
    }

    public static GotaVidaExtra generarGotaVidaExtra(Texture textura, Sound sonido, Array<Gota> gotasExistentes) {
        GotaVidaExtra nuevaGotaVidaExtra = new GotaVidaExtra(textura, sonido, TAMANO_GOTA);
        float xPos;
        float yPos = 480;

        do {
            xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaVidaExtra.getArea().x = xPos;
            nuevaGotaVidaExtra.getArea().y = yPos;
        } while (hayColisionConGotas(nuevaGotaVidaExtra, gotasExistentes));

        return nuevaGotaVidaExtra;
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
