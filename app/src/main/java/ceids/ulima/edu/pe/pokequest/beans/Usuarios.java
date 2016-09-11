package ceids.ulima.edu.pe.pokequest.beans;

/**
 * Created by CarlosGabriel on 10/09/2016.
 */
public class Usuarios {
    String codigo;
    String email;
    String pokeparadasRealizadas;
    int puntaje;

    public Usuarios() {
    }

    public Usuarios(String codigo, String email, String pokeparadasRealizadas, int puntaje) {
        this.codigo = codigo;
        this.email = email;
        this.pokeparadasRealizadas = pokeparadasRealizadas;
        this.puntaje = puntaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPokeparadasRealizadas() {
        return pokeparadasRealizadas;
    }

    public void setPokeparadasRealizadas(String pokeparadasRealizadas) {
        this.pokeparadasRealizadas = pokeparadasRealizadas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
