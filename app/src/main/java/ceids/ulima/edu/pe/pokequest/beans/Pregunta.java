package ceids.ulima.edu.pe.pokequest.beans;

/**
 * Created by CarlosGabriel on 4/09/2016.
 */
public class Pregunta {
    int key;
    Opcion opcion;
    String pregunta;
    int respuesta;

    public Pregunta() {
    }

    public Pregunta(int key, Opcion opcion, String pregunta, int respuesta) {
        this.key = key;
        this.opcion = opcion;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
}
