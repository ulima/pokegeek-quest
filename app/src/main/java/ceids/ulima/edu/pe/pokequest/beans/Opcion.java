package ceids.ulima.edu.pe.pokequest.beans;

/**
 * Created by CarlosGabriel on 4/09/2016.
 */
public class Opcion {
    String uno;
    String dos;
    String tres;
    String cuatro;

    public Opcion(String uno, String dos, String tres, String cuatro) {
        this.uno = uno;
        this.dos = dos;
        this.tres = tres;
        this.cuatro = cuatro;
    }

    public Opcion() {
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getTres() {
        return tres;
    }

    public void setTres(String tres) {
        this.tres = tres;
    }

    public String getCuatro() {
        return cuatro;
    }

    public void setCuatro(String cuatro) {
        this.cuatro = cuatro;
    }
}
