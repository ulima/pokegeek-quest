package ceids.ulima.edu.pe.pokequest.beans;

/**
 * Created by CarlosGabriel on 4/09/2016.
 */
public class Opcion {
    private String codigo;
    private String texto;

    public Opcion(String codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public Opcion() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return codigo + ") " + texto;
    }
}
