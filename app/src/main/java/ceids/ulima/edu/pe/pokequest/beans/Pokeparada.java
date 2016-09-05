package ceids.ulima.edu.pe.pokequest.beans;

/**
 * Created by CarlosGabriel on 4/09/2016.
 */
public class Pokeparada {
    String imagen;
    int latitud;
    int longitud;
    String nombre;
    int pregunta;
    int puntos;

    public Pokeparada(String imagen, int latitud, int longitud, String nombre, int pregunta, int puntos) {
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.pregunta = pregunta;
        this.puntos = puntos;
    }

    public Pokeparada() {
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPregunta() {
        return pregunta;
    }

    public void setPregunta(int pregunta) {
        this.pregunta = pregunta;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}