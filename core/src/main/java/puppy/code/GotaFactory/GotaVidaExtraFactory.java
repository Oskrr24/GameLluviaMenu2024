package puppy.code.GotaFactory;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.Gota.Gota;
import puppy.code.Gota.GotaVidaExtra;

public class GotaVidaExtraFactory implements GotaFactory {
    @Override
    public Gota crearGota(Texture textura, Sound sonido, float tamaño) {
        return new GotaVidaExtra(textura, sonido, tamaño);
    }
}

