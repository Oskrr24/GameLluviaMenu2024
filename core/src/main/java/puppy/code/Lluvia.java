package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import puppy.code.Gota.*;
import puppy.code.GotaFactory.*;

public class Lluvia {
    private Array<Gota> gotas;
    private long lastDropTime;
    private long lastVidaExtraDropTime = 0;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaEspecial;
    private Sound dropSound;
    private Music rainMusic;
    private boolean puntosDobles;
    private long tiempoPuntosDobles;
    private final long DURACION_PUNTOS_DOBLES = 6000000000L;
    private final float TAMANO_GOTA = 50f;
    private int puntosAlcanzados = 0;
    private Texture gotaVidaExtra;
    private boolean vidaExtraGenerada;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaEspecial, Sound ss, Music mm, Texture gotaVidaExtra) {
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaEspecial = gotaEspecial;
        this.gotaVidaExtra = gotaVidaExtra;
        puntosDobles = false;
        lastVidaExtraDropTime = 0;
        gotas = new Array<>();
        vidaExtraGenerada = false;
    }

    public void crear() {
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
        rainMusic.setVolume(0.05f);
    }

    private void crearGotaDeLluvia() {
        Gota nuevaGota;
        float xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
        float yPos = 480;

        // Usar el factory para crear la gota
        GotaFactory factory;
        if (MathUtils.random(1, 10) < 5) {
            factory = GotaFactoryProvider.getFactory(TipoGota.MALA);
            nuevaGota = factory.crearGota(gotaMala, dropSound, TAMANO_GOTA);
        } else {
            factory = GotaFactoryProvider.getFactory(TipoGota.BUENA);
            nuevaGota = factory.crearGota(gotaBuena, dropSound, TAMANO_GOTA);
        }

        nuevaGota.getArea().x = xPos;
        nuevaGota.getArea().y = yPos;
        gotas.add(nuevaGota);

        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(SpriteBatch batch,Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

        if (tarro.getVidas() == 1 && !vidaExtraGenerada) {
            GotaFactory vidaExtraFactory = GotaFactoryProvider.getFactory(TipoGota.VIDA_EXTRA);
            Gota nuevaGotaVidaExtra = vidaExtraFactory.crearGota(gotaVidaExtra, dropSound, TAMANO_GOTA);
            // Establecer posición aleatoria para la gota de vida extra
            nuevaGotaVidaExtra.getArea().x = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaVidaExtra.getArea().y = 480;
            gotas.add(nuevaGotaVidaExtra);
            vidaExtraGenerada = true;
            lastVidaExtraDropTime = TimeUtils.nanoTime();
        } else if (tarro.getVidas() > 1) {
            vidaExtraGenerada = false;
        }

        for (int i = 0; i < gotas.size; i++) {
            Gota gota = gotas.get(i);
            gota.actualizar(batch, Gdx.graphics.getDeltaTime(), tarro, puntosDobles);

            if (gota.getArea().y + TAMANO_GOTA < 0) {
                gotas.removeIndex(i);
                continue;
            }

            if (gota.getArea().overlaps(tarro.getArea())) {
                gota.manejarColision(tarro, puntosDobles);

                if (gota instanceof GotaBuena) {
                    puntosAlcanzados += 10;
                    verificarGeneracionGotaEspecial(tarro);
                }

                if (tarro.getVidas() <= 0) return false;

                gotas.removeIndex(i);
            }
        }

        if (puntosDobles && TimeUtils.nanoTime() - tiempoPuntosDobles > DURACION_PUNTOS_DOBLES) {
            puntosDobles = false;
        }
        return true;
    }

    private void verificarGeneracionGotaEspecial(Tarro tarro) {
        if (puntosAlcanzados >= 250 && puntosAlcanzados % 250 == 0) {
            GotaFactory especialFactory = GotaFactoryProvider.getFactory(TipoGota.ESPECIAL);
            Gota nuevaGotaEspecial = especialFactory.crearGota(gotaEspecial, dropSound, TAMANO_GOTA);
            // Establecer posición aleatoria para la gota especial
            nuevaGotaEspecial.getArea().x = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaEspecial.getArea().y = 480;
            gotas.add(nuevaGotaEspecial);
            activarPuntosDobles();
        }

        if (puntosAlcanzados >= 300 && puntosAlcanzados % 300 == 0) {
            GotaFactory vidaExtraFactory = GotaFactoryProvider.getFactory(TipoGota.VIDA_EXTRA);
            Gota nuevaGotaVidaExtra = vidaExtraFactory.crearGota(gotaVidaExtra, dropSound, TAMANO_GOTA);
            // Establecer posición aleatoria para la gota de vida extra
            nuevaGotaVidaExtra.getArea().x = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaVidaExtra.getArea().y = 480;
            gotas.add(nuevaGotaVidaExtra);
        }
    }

    private void activarPuntosDobles() {
        puntosDobles = true;
        tiempoPuntosDobles = TimeUtils.nanoTime();
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.dibujar(batch);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
