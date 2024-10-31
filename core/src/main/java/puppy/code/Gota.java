package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Gota {
    protected Rectangle area; // Área de la gota
    protected Texture textura; // Textura de la gota
    protected Sound sonido; // Sonido asociado a la gota
    protected int tipo; // Tipo de gota (buena, mala, especial)
    protected float tamaño; // Tamaño de la gota

    public Gota(Texture textura, Sound sonido, int tipo, float tamaño) {
        this.textura = textura;
        this.sonido = sonido;
        this.tipo = tipo;
        this.tamaño = tamaño;
        this.area = new Rectangle();
        this.area.setSize(tamaño); // Configura el tamaño de la gota
    }

    public abstract void caer(float deltaTime); // Método para actualizar la caída de la gota
    public abstract void dibujar(SpriteBatch batch); // Método para dibujar la gota

    public Rectangle getArea() {
        return area; // Devuelve el área de la gota
    }

    public int getTipo() {
        return tipo; // Devuelve el tipo de la gota
    }

    public void jugarSonido() {
        if (sonido != null) {
            sonido.play(); // Reproduce el sonido de la gota
        }
    }
}