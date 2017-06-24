package rafalex.pdm.ugr.parquedelasciencias;

import java.io.Serializable;

public class Exposicion implements Serializable {
    int imagen;
    String titulo;
    String descripcion;
    String ini;
    String fin;

    public Exposicion(int img, String ti, String des, String ini, String fin) {
        this.imagen = img;
        this.titulo = ti;
        this.descripcion = des;
        this.ini = ini;
        this.fin = fin;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTDescripcion() {
        return descripcion;
    }

    public String getFechaIni() {
        return ini;
    }

    public String getFechaFin() {
        return fin;
    }
}
