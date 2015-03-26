package clases;

/**
 * Created by JULIANEDUARDO on 24/03/2015.
 */

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import Object.Poste;
import Object.Luminaria;
import controlmacro.FormInicioSession;
import sistema.SQLite;


public class ClassRedesPoste {
    private Context     ctx;
    private String      nodo;
    private Poste       myPoste;
    private Luminaria   myLuminaria;


    private ArrayList<ContentValues>_tempTabla      = new ArrayList<ContentValues>();
    private ContentValues           _tempRegistro   = new ContentValues();
    private SQLite  FcnSQL;


    public ClassRedesPoste(Context _ctx, String _nodo){
        this.ctx        = _ctx;
        this.nodo       = _nodo;
        this.FcnSQL     = new SQLite(this.ctx, FormInicioSession.path_files_app);
        this.myPoste    = new Poste(this.nodo);
        this.myLuminaria= new Luminaria();
    }


    public ContentValues getDataPoste(int _item){
        this.myPoste.setItemPoste(_item);
        return this.FcnSQL.SelectDataRegistro("nodo_postes",
                "longitud,latitud,tipo,compartido,estado,material,altura,estructura,observacion",
                "nodo='"+this.nodo+"' AND item="+this.myPoste.getItemPoste());
    }


    public ArrayList<ContentValues> listaPostes(){
        this._tempTabla = this.FcnSQL.SelectData("nodo_postes",
                "nodo,item,longitud,latitud,tipo,compartido,estado,material,altura,estructura,observacion",
                "nodo='"+this.nodo+"'");
        return this._tempTabla;
    }


    public ArrayList<ContentValues> getListaLuminarias(int _item){
        this.myPoste.setItemPoste(_item);
        this._tempTabla = this.FcnSQL.SelectData("postes_luminarias",
                "id,codigo,capacidad,tipo,estado,propietario,tierra",
                "nodo='"+this.nodo+"' AND item="+this.myPoste.getItemPoste());
        return this._tempTabla;
    }


    public boolean crearPoste(double _latitud, double _longitud, String _tipo, String _compartido, String _estado,
                              String _material, int _altura, String _estructura, String _observacion){
        this.myPoste.setNodoPoste(this.nodo);
        this.myPoste.setLatitudPoste(_latitud);
        this.myPoste.setLongitudPoste(_longitud);
        this.myPoste.setTipoPoste(_tipo);
        this.myPoste.setCompartidoPoste(_compartido);
        this.myPoste.setEstadoPoste(_estado);
        this.myPoste.setMaterialPoste(_material);
        this.myPoste.setAlturaPoste(_altura);
        this.myPoste.setEstructuraPoste(_estructura);
        this.myPoste.setObservacionPoste(_observacion);
        return this.registrarPoste();
    }


    public boolean editarPoste(String _item, double _latitud, double _longitud, String _tipo, String _compartido,
                                   String _estado, String _material, int _altura, String _estructura, String _observacion){
        this.myPoste.setLatitudPoste(_latitud);
        this.myPoste.setLongitudPoste(_longitud);
        this.myPoste.setTipoPoste(_tipo);
        this.myPoste.setCompartidoPoste(_compartido);
        this.myPoste.setEstadoPoste(_estado);
        this.myPoste.setMaterialPoste(_material);
        this.myPoste.setAlturaPoste(_altura);
        this.myPoste.setEstructuraPoste(_estructura);
        this.myPoste.setObservacionPoste(_observacion);
        return this.actualizarPoste(_item);
    }


    public boolean eliminarPoste(int _item){
        return this.FcnSQL.DeleteRegistro("nodo_postes","nodo='"+this.nodo+"' AND item="+_item);
    }


    public boolean crearEquipo(String _item, String _nombre, int _capacidad, String _unidades){
        this.myPoste.setItemPoste(Integer.parseInt(_item));
        this.myPoste.setEquipoNombre(_nombre);
        this.myPoste.setEquipoCapacidad(_capacidad);
        this.myPoste.setEquipoUnidades(_unidades);
        return this.registrarEquipo();
    }


    public boolean crearLineas(String _item, String _faseA, String _faseB, String _faseC, String _faseAP, String _faseN, String _conductor){
        this.myPoste.setItemPoste(Integer.parseInt(_item));
        this.myPoste.setLineaCalibreA(_faseA);
        this.myPoste.setLineaCalibreB(_faseB);
        this.myPoste.setLineaCalibreC(_faseC);
        this.myPoste.setLineaCalibreAP(_faseAP);
        this.myPoste.setLineaCalibreN(_faseN);
        this.myPoste.setLineaConductor(_conductor);
        return this.registrarLineas();
    }


    public boolean crearLuminaria(int _item, String _codigo, String _capacidad, String _tipo, String _estado, String _propietario, String _tierra){
        this.myPoste.setItemPoste(_item);
        this.myLuminaria.setId(this.FcnSQL.CountRegistrosWhere("postes_luminarias", "nodo='"+this.myPoste.getNodoPoste()+"' AND item="+this.myPoste.getItemPoste())+1);
        this.myLuminaria.setCodigoLuminaria(_codigo);
        this.myLuminaria.setCapacidadLuminaria(_capacidad);
        this.myLuminaria.setTipoLuminaria(_tipo);
        this.myLuminaria.setEstadoLuminaria(_estado);
        this.myLuminaria.setPropietarioLuminaria(_propietario);
        this.myLuminaria.setTierraLuminaria(_tierra);
        this.myPoste.addLuminaria(this.myLuminaria);
        return this.registrarLuminaria();
    }


    public boolean eliminarLuminaria(int _item, int _id){
        return this.FcnSQL.DeleteRegistro("postes_luminarias","nodo='"+this.myPoste.getNodoPoste()+"' AND item="+_item+" AND id="+_id);
    }

    /*******Inicio de los meotodos privados *******/

    private boolean registrarPoste(){
        this._tempRegistro.clear();
        this._tempRegistro.put("nodo", this.myPoste.getNodoPoste());
        this._tempRegistro.put("item", this.FcnSQL.CountRegistrosWhere("nodo_postes","nodo='"+this.nodo+"'")+1);
        this._tempRegistro.put("latitud", this.myPoste.getLatitudPoste());
        this._tempRegistro.put("longitud", this.myPoste.getLongitudPoste());
        this._tempRegistro.put("tipo", this.myPoste.getTipoPoste());
        this._tempRegistro.put("compartido", this.myPoste.getCompartidoPoste());
        this._tempRegistro.put("estado", this.myPoste.getEstadoPoste());
        this._tempRegistro.put("material", this.myPoste.getMaterialPoste());
        this._tempRegistro.put("altura", this.myPoste.getAlturaPoste());
        this._tempRegistro.put("estructura", this.myPoste.getEstructuraPoste());
        this._tempRegistro.put("observacion", this.myPoste.getObservacionPoste());
        return this.FcnSQL.InsertRegistro("nodo_postes", this._tempRegistro);
    }


    private boolean actualizarPoste(String _item){
        this._tempRegistro.clear();
        this._tempRegistro.put("latitud", this.myPoste.getLatitudPoste());
        this._tempRegistro.put("longitud", this.myPoste.getLongitudPoste());
        this._tempRegistro.put("tipo", this.myPoste.getTipoPoste());
        this._tempRegistro.put("compartido", this.myPoste.getCompartidoPoste());
        this._tempRegistro.put("estado", this.myPoste.getEstadoPoste());
        this._tempRegistro.put("material", this.myPoste.getMaterialPoste());
        this._tempRegistro.put("altura", this.myPoste.getAlturaPoste());
        this._tempRegistro.put("estructura", this.myPoste.getEstructuraPoste());
        this._tempRegistro.put("observacion", this.myPoste.getObservacionPoste());
        return this.FcnSQL.UpdateRegistro("nodo_postes", this._tempRegistro,"nodo='"+this.nodo+"' AND item="+_item);
    }


    private boolean registrarEquipo(){
        this._tempRegistro.clear();
        this._tempRegistro.put("nodo", this.nodo);
        this._tempRegistro.put("item", this.myPoste.getItemPoste());
        this._tempRegistro.put("nombre", this.myPoste.getEquipoNombre());
        this._tempRegistro.put("capacidad", this.myPoste.getEquipoCapacidad());
        this._tempRegistro.put("unidades", this.myPoste.getEquipoUnidades());
        return this.FcnSQL.InsertOrUpdateRegistro("postes_equipos", this._tempRegistro, "nodo='"+this.nodo+"' AND item="+this.myPoste.getItemPoste());
    }


    private boolean registrarLineas(){
        this._tempRegistro.clear();
        this._tempRegistro.put("nodo", this.nodo);
        this._tempRegistro.put("item", this.myPoste.getItemPoste());
        this._tempRegistro.put("faseA", this.myPoste.getLineaCalibreA());
        this._tempRegistro.put("faseB", this.myPoste.getLineaCalibreB());
        this._tempRegistro.put("faseC", this.myPoste.getLineaCalibreC());
        this._tempRegistro.put("faseAP", this.myPoste.getLineaCalibreAP());
        this._tempRegistro.put("faseN", this.myPoste.getLineaCalibreN());
        this._tempRegistro.put("conductor", this.myPoste.getLineaConductor());
        return this.FcnSQL.InsertOrUpdateRegistro("postes_lineas", this._tempRegistro, "nodo='"+this.nodo+"' AND item="+this.myPoste.getItemPoste());
    }


    private boolean registrarLuminaria(){
        this._tempRegistro.clear();
        this._tempRegistro.put("nodo", this.myPoste.getNodoPoste());
        this._tempRegistro.put("item", this.myPoste.getItemPoste());
        this._tempRegistro.put("id", this.myLuminaria.getId());
        this._tempRegistro.put("codigo", this.myLuminaria.getCodigoLuminaria());
        this._tempRegistro.put("capacidad", this.myLuminaria.getCapacidadLuminaria());
        this._tempRegistro.put("tipo", this.myLuminaria.getTipoLuminaria());
        this._tempRegistro.put("estado", this.myLuminaria.getEstadoLuminaria());
        this._tempRegistro.put("propietario", this.myLuminaria.getPropietarioLuminaria());
        this._tempRegistro.put("tierra", this.myLuminaria.getTierraLuminaria());
        return this.FcnSQL.InsertRegistro("postes_luminarias", this._tempRegistro);
    }
}
