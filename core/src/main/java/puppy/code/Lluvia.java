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
    private Array<Gota> gotas;              // Array de gotas
    private long lastDropTime;              // Tiempo de la última gota generada
    private long lastVidaExtraDropTime = 0; // Inicializar tiempo de última gota de vida extra;       // Tiempo de la última gota especial generada
    private Texture gotaBuena;              // Textura para gotas buenas
    private Texture gotaMala;               // Textura para gotas malas
    private Texture gotaEspecial;           // Textura para gotas especiales
    private Sound dropSound;                // Sonido de las gotas
    private Music rainMusic;                // Música de fondo
    private boolean puntosDobles;            // Estado de puntos dobles
    private long tiempoPuntosDobles;        // Tiempo de duración de puntos dobles
    private final long DURACION_PUNTOS_DOBLES = 5000000000L; // Duración de puntos dobles en nanosegundos
    private final float TAMANO_GOTA = 50f;  // Tamaño de las gotas}
    private int puntosAlcanzados = 0; // Para llevar la cuenta de los puntos alcanzados
    private Texture gotaVidaExtra; // Textura para gotas potenciadoras
     private boolean vidaExtraGenerada;


    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaEspecial, Sound ss, Music mm, Texture gotaVidaExtra) {
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaEspecial = gotaEspecial;
        this.gotaVidaExtra = gotaVidaExtra;
        puntosDobles = false;
        lastVidaExtraDropTime = 0; // Inicializar tiempo de última gota de vida extra
        gotas = new Array<>(); // Inicializar el array de gotas
        vidaExtraGenerada = false;
    }

    public void crear() {
        crearGotaDeLluvia(); // Crear la primera gota
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Gota nuevaGota;
        float xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
        float yPos = 480; // Parte superior de la pantalla

        // Generación de tipo de gota: buena (2), mala (1)
        if (MathUtils.random(1, 10) < 5) {
            nuevaGota = new GotaMala(gotaMala, dropSound, TAMANO_GOTA);
        } else {
            nuevaGota = new GotaBuena(gotaBuena, dropSound, TAMANO_GOTA);
        }

        nuevaGota.getArea().x = xPos;
        nuevaGota.getArea().y = yPos;
        gotas.add(nuevaGota); // Agregar la nueva gota al array

        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
    if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

    if (tarro.getVidas() == 1 && !vidaExtraGenerada) { 
        generarGotaVidaExtra();
        vidaExtraGenerada = true; // Marca que se ha generado una vida extra
        lastVidaExtraDropTime = TimeUtils.nanoTime(); // Actualiza el tiempo de la última gota de vida extra
    } else if (tarro.getVidas() > 1) {
        vidaExtraGenerada = false; // Resetea la bandera si el tarro tiene más de 1 vida
    }
    
    for (int i = 0; i < gotas.size; i++) {
        Gota gota = gotas.get(i);
        gota.caer(Gdx.graphics.getDeltaTime()); // Actualizar caída de la gota

        // Verificar si la gota salió de la pantalla
        if (gota.getArea().y + TAMANO_GOTA < 0) {
            gotas.removeIndex(i); // Remueve gotas que salieron de la pantalla
            continue;
        }

        if (gota.getArea().overlaps(tarro.getArea())) {
            int tipo = gota.getTipo();
            switch (tipo) {
                case 1:
                    tarro.dañar(); // Dañar al tarro
                    if (tarro.getVidas() <= 0) return false;
                    break;
                case 2:
                    tarro.sumarPuntos(puntosDobles ? 20 : 10); // Sumar puntos
                    gota.jugarSonido(); // Sonido al recoger gota
                    // Actualizar puntos alcanzados
                    puntosAlcanzados += 10; // O 20 si hay puntos dobles
                    verificarGeneracionGotaEspecial(tarro); // Verificar si se debe generar una gota especial
                    break;
                case 3:
                    break;
                case 4:
                    gota.jugarSonido(); // Sonido al recoger la gota de vida extra
                    tarro.aumentarVida(); // Llama al método para agregar una vida al tarro
                    break;
                default:
                    break;
            }
            
            gotas.removeIndex(i); // Remueve la gota recogida
        }
    }

    // Verificar si el efecto de puntos dobles ha expirado
    if (puntosDobles && TimeUtils.nanoTime() - tiempoPuntosDobles > DURACION_PUNTOS_DOBLES) {
        puntosDobles = false; // Desactiva puntos dobles
    }
    return true;
}

    private void verificarGeneracionGotaEspecial(Tarro tarro) {
    // Comprobar si los puntos alcanzados son un múltiplo de 250
        if (puntosAlcanzados >= 250 && puntosAlcanzados % 250 == 0) {
            generarGotaEspecial();
        }
        
        if (puntosAlcanzados >= 500 && puntosAlcanzados % 500 == 0) {
            generarGotaVidaExtra();
        }
    }
    
    private void generarGotaVidaExtra() {
        GotaVidaExtra nuevaGotaPotenciador = new GotaVidaExtra(gotaVidaExtra, dropSound, TAMANO_GOTA); // Gota potenciadora
        float xPos;
        float yPos = 480; // Parte superior de la pantalla

        // Generar una nueva posición hasta encontrar una válida
        do {
            xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaPotenciador.getArea().x = xPos;
            nuevaGotaPotenciador.getArea().y = yPos;
        } while (hayColisionConGotas(nuevaGotaPotenciador));

        gotas.add(nuevaGotaPotenciador); // Agregar la nueva gota potenciadora
    }
    private void generarGotaEspecial() {
        GotaEspecial nuevaGotaEspecial = new GotaEspecial(gotaEspecial, dropSound, TAMANO_GOTA); // Gota especial
        float xPos;
        float yPos = 480; // Parte superior de la pantalla

        // Generar una nueva posición hasta encontrar una válida
        do {
            xPos = MathUtils.random(0, 800 - TAMANO_GOTA);
            nuevaGotaEspecial.getArea().x = xPos;
            nuevaGotaEspecial.getArea().y = yPos;
        } while (hayColisionConGotas(nuevaGotaEspecial));

        gotas.add(nuevaGotaEspecial); // Agregar la nueva gota especial

        // Activar efecto de puntos dobles
        activarPuntosDobles(); // Llama a un método para activar los puntos dobles
    }

    private boolean hayColisionConGotas(Gota nuevaGota) {
        // Verificar colisión con otras gotas
        for (Gota gota : gotas) {
            if (gota.getArea().overlaps(nuevaGota.getArea())) {
                return true; // Hay colisión
            }
        }
        return false; // No hay colisión
    }

    private void activarPuntosDobles() {
        puntosDobles = true; // Activa puntos dobles
        tiempoPuntosDobles = TimeUtils.nanoTime(); // Establece el tiempo de activación
        }
    
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.dibujar(batch); // Dibuja cada gota
        }
    }

    public void destruir() {
        dropSound.dispose(); // Libera sonido de gotas
        rainMusic.dispose(); // Libera música de fondo
    }
    
    public void pausar() {
        rainMusic.stop(); // Detiene la música
    }

    public void continuar() {
        rainMusic.play(); // Reanuda la música
    }
}