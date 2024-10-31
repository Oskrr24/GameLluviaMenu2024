package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro {
    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50; // Tiempo máximo en que el tarro está herido
    private int tiempoHerido;

    public Tarro(Texture tex, Sound ss) {
        bucketImage = tex;
        sonidoHerido = ss;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }

    public void crear() {
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 17;
        bucket.width = 80;
        bucket.height = 90;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax; // Reiniciar tiempo herido
        sonidoHerido.play(); // Reproducir sonido de daño
    }

    public void dibujar(SpriteBatch batch) {
        if (!herido)  
          batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        else {

          //batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
          batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5), bucket.width, bucket.height);
          tiempoHerido--;
          if (tiempoHerido<=0) herido = false;
        }
    } 

    public void actualizarMovimiento() {
        // Movimiento desde teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= velx * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += velx * Gdx.graphics.getDeltaTime();
        }
        // Limitar movimiento a los bordes izquierdo y derecho
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }

    public void destruir() {
        bucketImage.dispose(); // Liberar recursos de la imagen
    }

    public boolean estaHerido() {
        return herido; // Devuelve el estado herido
    }
    
    public void aumentarVida() {
        this.vidas++; // Aumenta la vida del jugador
    }
}