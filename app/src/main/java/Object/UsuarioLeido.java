package Object;

import android.content.Context;

/**
 * Created by JULIANEDUARDO on 20/02/2015.
 */
public class UsuarioLeido extends UsuarioEMSA {
    private static UsuarioLeido ourInstance;
    private int numeroPoste;
    private int lectura;
    private int fotos;
    private String observacion;
    private String new_nodo;
    private boolean leido;


    public static UsuarioLeido getInstance() {
        if (ourInstance == null) {
            ourInstance = new UsuarioLeido();
        }
        return ourInstance;
    }


    private UsuarioLeido() {
        super();
    }


    public int getNumeroPoste() {
        return numeroPoste;
    }

    public void setNumeroPoste(int numeroPoste) {
        this.numeroPoste = numeroPoste;
    }

    public int getLectura() {
        return lectura;
    }

    public void setLectura(int lectura) {
        this.lectura = lectura;
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNew_nodo() {
        return new_nodo;
    }

    public void setNew_nodo(String new_nodo) {
        this.new_nodo = new_nodo;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public int getFotos() {
        return fotos;
    }

    public void setFotos(int fotos) {
        this.fotos = fotos;
    }
}
