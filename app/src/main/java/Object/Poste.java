package Object;

import java.util.ArrayList;

/**
 * Created by JULIANEDUARDO on 24/03/2015.
 */
public class Poste {
    private String  nodoPoste;
    private int     itemPoste;
    private double  longitudPoste;
    private double  latitudPoste;
    private String  tipoPoste;
    private String  compartidoPoste;
    private String  estadoPoste;
    private String  materialPoste;
    private int     alturaPoste;
    private String  estructuraPoste;
    private String  observacionPoste;

    private String  equipoNombre;
    private String  equipoUnidades;
    private int     equipoCapacidad;

    private String  lineaCalibreA;
    private String  lineaCalibreB;
    private String  lineaCalibreC;
    private String  lineaCalibreAP;
    private String  lineaCalibreN;
    private String  lineaConductor;

    private Luminaria luminaria;
    private ArrayList<Luminaria> listaLuminarias;

    public Poste(){
        this.listaLuminarias = new ArrayList<>();
    }

    public String getNodoPoste() {
        return nodoPoste;
    }

    public void setNodoPoste(String nodoPoste) {
        this.nodoPoste = nodoPoste;
    }

    public int getItemPoste() {
        return itemPoste;
    }

    public void setItemPoste(int itemPoste) {
        this.itemPoste = itemPoste;
    }

    public double getLatitudPoste() {
        return latitudPoste;
    }

    public void setLatitudPoste(double latitudGPoste) {
        this.latitudPoste = latitudGPoste;
    }

    public double getLongitudPoste() {
        return longitudPoste;
    }

    public void setLongitudPoste(double longitudPoste) {
        this.longitudPoste = longitudPoste;
    }

    public String getTipoPoste() {
        return tipoPoste;
    }

    public void setTipoPoste(String tipoPoste) {
        this.tipoPoste = tipoPoste;
    }

    public String getCompartidoPoste() {
        return compartidoPoste;
    }

    public void setCompartidoPoste(String compartidoPoste) {
        this.compartidoPoste = compartidoPoste;
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

    public int getAlturaPoste() {
        return alturaPoste;
    }

    public void setAlturaPoste(int alturaPoste) {
        this.alturaPoste = alturaPoste;
    }

    public String getObservacionPoste() {
        return observacionPoste;
    }

    public void setObservacionPoste(String observacionPoste) {
        this.observacionPoste = observacionPoste;
    }

    public String getEstructuraPoste() {
        return estructuraPoste;
    }

    public void setEstructuraPoste(String estructuraPoste) {
        this.estructuraPoste = estructuraPoste;
    }

    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }

    public String getEquipoUnidades() {
        return equipoUnidades;
    }

    public void setEquipoUnidades(String equipoUnidades) {
        this.equipoUnidades = equipoUnidades;
    }

    public int getEquipoCapacidad() {
        return equipoCapacidad;
    }

    public void setEquipoCapacidad(int equipoCapacidad) {
        this.equipoCapacidad = equipoCapacidad;
    }

    public String getLineaCalibreA() {
        return lineaCalibreA;
    }

    public void setLineaCalibreA(String lineaCalibreA) {
        this.lineaCalibreA = lineaCalibreA;
    }

    public String getLineaCalibreB() {
        return lineaCalibreB;
    }

    public void setLineaCalibreB(String lineaCalibreB) {
        this.lineaCalibreB = lineaCalibreB;
    }

    public String getLineaCalibreC() {
        return lineaCalibreC;
    }

    public void setLineaCalibreC(String lineaCalibreC) {
        this.lineaCalibreC = lineaCalibreC;
    }

    public String getLineaCalibreAP() {
        return lineaCalibreAP;
    }

    public void setLineaCalibreAP(String lineaCalibreAP) {
        this.lineaCalibreAP = lineaCalibreAP;
    }

    public String getLineaCalibreN() {
        return lineaCalibreN;
    }

    public void setLineaCalibreN(String lineaCalibreN) {
        this.lineaCalibreN = lineaCalibreN;
    }

    public String getLineaConductor() {
        return lineaConductor;
    }

    public void setLineaConductor(String lineaConductor) {
        this.lineaConductor = lineaConductor;
    }

    public ArrayList<Luminaria> getAllLuminarias(){
        return this.listaLuminarias;
    }

    public Luminaria getLuminaria(int _posicion){
        return this.listaLuminarias.get(_posicion);
    }

    public void addLuminaria(Luminaria _luminaria){
        this.listaLuminarias.add(_luminaria);
    }

    public void removeLuminaria(int _posicion){
        this.listaLuminarias.remove(_posicion);
    }

    public int getCountLuminarias(){
        return this.listaLuminarias.size();
    }
}
