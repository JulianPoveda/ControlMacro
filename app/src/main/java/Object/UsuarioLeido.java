package Object;

/**
 * Created by JULIANEDUARDO on 20/02/2015.
 */
public class UsuarioLeido extends UsuarioEMSA {
    private int     numeroPoste;
    private int     lectura;
    private String  observacion;
    private boolean leido;


    public UsuarioLeido(){
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

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
