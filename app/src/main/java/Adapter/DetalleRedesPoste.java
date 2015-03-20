package Adapter;

/**
 * Created by sypelcdesarrollo on 19/03/15.
 */
public class DetalleRedesPoste {

    protected String itemPoste;
    protected String gpsLat;
    protected String gpsLong;
    protected String tipoPoste;
    protected String compartido;
    protected String estadoPoste;
    protected String materialPoste;
    protected String alturaPoste;
    protected String estructuraPoste;
    protected String observacionesPoste;

    public DetalleRedesPoste(String _itemposte, String _gpsLat, String _gpsLong, String _tipoposte, String _compartido, String _estadoposte, String _materialposte, String _alturaposte, String _estructuraposte, String _observaciones){
        super();
        this.itemPoste          =   _itemposte;
        this.gpsLat             =   _gpsLat;
        this.gpsLong            =   _gpsLong;
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

    public void setItemPoste(String itemPoste) {
        this.itemPoste = itemPoste;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(String gpsLong) {
        this.gpsLong = gpsLong;
    }

    public String getTipoPoste() {
        return tipoPoste;
    }

    public void setTipoPoste(String tipoPoste) {
        this.tipoPoste = tipoPoste;
    }

    public String getCompartido() {
        return compartido;
    }

    public void setCompartido(String compartido) {
        this.compartido = compartido;
    }

    public String getEstadoPoste() {
        return estadoPoste;
    }

    public void setEstadoPoste(String estadoPoste) {
        this.estadoPoste = estadoPoste;
    }

    public String getMaterialPoste() {
        return materialPoste;
    }

    public void setMaterialPoste(String materialPoste) {
        this.materialPoste = materialPoste;
    }

    public String getAlturaPoste() {
        return alturaPoste;
    }

    public void setAlturaPoste(String alturaPoste) {
        this.alturaPoste = alturaPoste;
    }

    public String getEstructuraPoste() {
        return estructuraPoste;
    }

    public void setEstructuraPoste(String estructuraPoste) {
        this.estructuraPoste = estructuraPoste;
    }

    public String getObservacionesPoste() {
        return observacionesPoste;
    }

    public void setObservacionesPoste(String observacionesPoste) {
        this.observacionesPoste = observacionesPoste;
    }
}
