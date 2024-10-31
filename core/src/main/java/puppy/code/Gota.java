package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Gota {
    protected Rectangle area;
    protected Texture textura;
    protected Sound sonido;
    protected int tipo;
    protected float tamaño;
    
    public Gota(Texture textura, Sound sonido, int tipo, float tamaño) {
        this.textura = textura;
        this.sonido = sonido;
        this.tipo = tipo;
        this.tamaño = tamaño;
        this.area = new Rectangle();
        this.area.setSize(tamaño);
    }

    public abstract void caer(float deltaTime);
    
    public abstract void dibujar(SpriteBatch batch);
    
    // Método abstracto para manejar la colisión con el tarro
    public abstract void manejarColision(Tarro tarro, boolean puntosDobles);
    
    public Rectangle getArea() {
        return area;
    }

    public int getTipo() {
        return tipo;
    }

    public void jugarSonido() {
        if (sonido != null) {
            sonido.play(0.05f);
        }
    }
}