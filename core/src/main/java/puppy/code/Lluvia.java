package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

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

        if (MathUtils.random(1, 10) < 5) {
            nuevaGota = new GotaMala(gotaMala, dropSound, TAMANO_GOTA);
        } else {
            nuevaGota = new GotaBuena(gotaBuena, dropSound, TAMANO_GOTA);
        }

        nuevaGota.getArea().x = xPos;
        nuevaGota.getArea().y = yPos;
        gotas.add(nuevaGota); 

        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

        if (tarro.getVidas() == 1 && !vidaExtraGenerada) {
            Gota nuevaGotaVidaExtra = GotaVidaExtra.generarGotaVidaExtra(gotaVidaExtra, dropSound, gotas);
            gotas.add(nuevaGotaVidaExtra);
            vidaExtraGenerada = true;
            lastVidaExtraDropTime = TimeUtils.nanoTime();
        } else if (tarro.getVidas() > 1) {
            vidaExtraGenerada = false;
        }

        for (int i = 0; i < gotas.size; i++) {
            Gota gota = gotas.get(i);
            gota.caer(Gdx.graphics.getDeltaTime());

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
            Gota nuevaGotaEspecial = GotaEspecial.generarGotaEspecial(gotaEspecial, dropSound, gotas);
            gotas.add(nuevaGotaEspecial);
            activarPuntosDobles();
        }
        
        if (puntosAlcanzados >= 300 && puntosAlcanzados % 300 == 0) {
            Gota nuevaGotaVidaExtra = GotaVidaExtra.generarGotaVidaExtra(gotaVidaExtra, dropSound, gotas);
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