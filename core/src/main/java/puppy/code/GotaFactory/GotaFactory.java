package puppy.code.GotaFactory;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.Gota.Gota;

public interface GotaFactory {
    Gota crearGota(Texture textura, Sound sonido, float tamaño);
}
