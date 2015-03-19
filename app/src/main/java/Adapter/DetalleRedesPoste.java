package Adapter;

/**
 * Created by sypelcdesarrollo on 19/03/15.
 */
public class DetalleRedesPoste {

    protected String itemPoste;
    protected String pocisionGPS;
    protected String tipoPoste;
    protected String compartido;
    protected String estadoPoste;
    protected String materialPoste;
    protected String alturaPoste;
    protected String estructuraPoste;
    protected String observacionesPoste;

    public DetalleRedesPoste(String _itemposte, String _pocisiongps, String _tipoposte, String _compartido, String _estadoposte, String _materialposte, String _alturaposte, String _estructuraposte, String _observaciones){
        super();
        this.itemPoste          =   _itemposte;
        this.pocisionGPS        =   _pocisiongps;
        this.tipoPoste          =   _tipoposte;
        this.compartido         =   _compartido;
        this.estadoPoste        =   _estadoposte;
        this.materialPoste      =   _materialposte;
        this.alturaPoste        =   _alturaposte;
        this.estructuraPoste    =   _estructuraposte;
        this.observacionesPoste =   _observaciones;
    }

    public String getItemPoste() {
        return itemPoste;
    }

    public String getPocisionGPS() {
        return pocisionGPS;
    }

    public String getTipoPoste() {
        return tipoPoste;
    }

    public String getCompartido() {
        return compartido;
    }

    public String getEstadoPoste() {
        return estadoPoste;
    }

    public String getMaterialPoste() {
        return materialPoste;
    }

    public String getAlturaPoste() {
        return alturaPoste;
    }

    public String getEstructuraPoste() {
        return estructuraPoste;
    }

    public String getObservacionesPoste() {
        return observacionesPoste;
    }

    public void setItemPoste(String itemPoste) {
        this.itemPoste = itemPoste;
    }

    public void setPocisionGPS(String pocisionGPS) {
        this.pocisionGPS = pocisionGPS;
    }

    public void setTipoPoste(String tipoPoste) {
        this.tipoPoste = tipoPoste;
    }

    public void setCompartido(String compartido) {
        this.compartido = compartido;
    }

    public void setEstadoPoste(String estadoPoste) {
        this.estadoPoste = estadoPoste;
    }

    public void setMaterialPoste(String materialPoste) {
        this.materialPoste = materialPoste;
    }

    public void setAlturaPoste(String alturaPoste) {
        this.alturaPoste = alturaPoste;
    }

    public void setEstructuraPoste(String estructuraPoste) {
        this.estructuraPoste = estructuraPoste;
    }

    public void setObservacionesPoste(String observacionesPoste) {
        this.observacionesPoste = observacionesPoste;
    }
}
