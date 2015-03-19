package Object;

/**
 * Created by JULIANEDUARDO on 20/02/2015.
 */
public class UsuarioEMSA {

    //Datos de referencia en la base de datos
    private int         id;
    private String      fecha_programacion;
    private String      nodo;
    private int         cuenta;
    private String      marca_medidor;
    private String      serie_medidor;
    private String      nombre;
    private String      direccion;
    private String      estado;
    private String      vinculacion;

    public UsuarioEMSA(){
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_programacion() {
        return fecha_programacion;
    }

    public void setFecha_programacion(String fecha_programacion) {
        this.fecha_programacion = fecha_programacion;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getMarca_medidor() {
        return marca_medidor;
    }

    public void setMarca_medidor(String marca_medidor) {
        this.marca_medidor = marca_medidor;
    }

    public String getSerie_medidor() {
        return serie_medidor;
    }

    public void setSerie_medidor(String serie_medidor) {
        this.serie_medidor = serie_medidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVinculacion() {
        return vinculacion;
    }

    public void setVinculacion(String vinculacion) {
        this.vinculacion = vinculacion;
    }
}
