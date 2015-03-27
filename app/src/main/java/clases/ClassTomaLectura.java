package clases;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import controlmacro.FormInicioSession;
import sistema.Archivos;
import sistema.DateTime;
import sistema.SQLite;

import Object.UsuarioLeido;

/**
 * Created by JULIANEDUARDO on 20/02/2015.
 */
public class ClassTomaLectura {
    private ContentValues               _tempRegistro;
    private ArrayList<ContentValues>    _tempTabla;
    private Context                     Ctx;
    private SQLite                      FcnSQL;
    private UsuarioLeido                ObjUsuario;
    private Archivos                    FcnArchivos;
    private DateTime                    FcnTime;


    public ClassTomaLectura(Context _ctx, String _nodo){
        this.Ctx            = _ctx;
        this.ObjUsuario     = UsuarioLeido.getInstance();
        this.FcnSQL         = new SQLite(this.Ctx, FormInicioSession.path_files_app);
        this.FcnArchivos    = new Archivos(this.Ctx, FormInicioSession.path_files_app,10);
        this.FcnTime        = DateTime.getInstance();
        this.ObjUsuario.setNodo(_nodo);

        if(!this.FcnArchivos.ExistFolderOrFile(FormInicioSession.sub_path_pictures,true)){
            this.FcnArchivos.MakeDirectory(FormInicioSession.sub_path_pictures,true);
        }
    }

    public ClassTomaLectura(Context _ctx){
        this.Ctx    = _ctx;
        this.FcnSQL = new SQLite(this.Ctx, FormInicioSession.path_files_app);
    }

    public boolean getDatosUsuario(boolean _next){
        boolean _retorno  = false;
        if(this.ObjUsuario.getId() == -1){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, fecha_programacion, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND estado='P' ORDER BY id ASC");
        }else if(_next){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, fecha_programacion, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND id>"+this.ObjUsuario.getId()+" AND estado='P' ORDER BY id ASC");
        }else{
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, fecha_programacion, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND id<"+this.ObjUsuario.getId()+" AND estado='P' ORDER BY id DESC");
        }

        if(this._tempRegistro.size()>0){
            _retorno = true;
            this.setInfUsuario();
        }
        return _retorno;
    }

    public boolean getSearchDatosUsuario(String _cuenta, String _medidor){
        boolean _retorno    = false;
        this._tempRegistro  = this.FcnSQL.SelectDataRegistro(   "maestro_clientes",
                                                                "id, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "cuenta="+_cuenta+" AND serie ='"+_medidor+"' ORDER BY id ASC");

        if(this._tempRegistro.size()>0){
            _retorno = true;
            this.setInfUsuario();
        }
        return _retorno;
    }

    private void setInfUsuario(){
        this.ObjUsuario.setNodo(this._tempRegistro.getAsString("nodo"));
        this.ObjUsuario.setNew_nodo(this._tempRegistro.getAsString("nodo"));
        this.ObjUsuario.setFecha_programacion(this._tempRegistro.getAsString("fecha_programacion"));
        this.ObjUsuario.setId(this._tempRegistro.getAsInteger("id"));
        this.ObjUsuario.setCuenta(this._tempRegistro.getAsInteger("cuenta"));
        this.ObjUsuario.setMarca_medidor(this._tempRegistro.getAsString("medidor"));
        this.ObjUsuario.setSerie_medidor(this._tempRegistro.getAsString("serie"));
        this.ObjUsuario.setNombre(this._tempRegistro.getAsString("nombre"));
        this.ObjUsuario.setDireccion(this._tempRegistro.getAsString("direccion"));
        this.ObjUsuario.setEstado(this._tempRegistro.getAsString("estado"));
        this.ObjUsuario.setVinculacion(this._tempRegistro.getAsString("vinculacion"));
        this.ObjUsuario.setLeido(!this._tempRegistro.getAsString("estado").equals("P"));
        this.getCantidadFotos();
    }

    public ArrayList<ContentValues> ListaClientes(String _nodo, boolean _filtro){
        if(_filtro){
            this._tempTabla = this.FcnSQL.SelectData(   "maestro_clientes",
                                                        "cuenta,serie,nombre,direccion",
                                                        "nodo='"+_nodo+"'");
        }else{
            this._tempTabla = this.FcnSQL.SelectData(   "maestro_clientes",
                                                        "cuenta,serie,nombre,direccion",
                                                        "id IS NOT NULL");
        }
        return this._tempTabla;
    }

    public UsuarioLeido getInfUsuario() {
        return this.ObjUsuario;
    }

    public void getCantidadFotos(){
        this.ObjUsuario.setFotos(this.FcnArchivos.numArchivosInFolderBeginByName(   FormInicioSession.sub_path_pictures,
                                                                                    this.ObjUsuario.getCuenta()+"",
                                                                                    true));
    };

    private void setEstado(String _estado){
        this._tempRegistro.clear();
        this._tempRegistro.put("estado",_estado);
        this.FcnSQL.UpdateRegistro( "maestro_clientes",
                                    this._tempRegistro,
                                    "id="+this.ObjUsuario.getId()+" AND fecha_programacion='"+this.ObjUsuario.getFecha_programacion()+"' AND " +
                                            "nodo='"+this.ObjUsuario.getNodo()+"' AND cuenta="+this.ObjUsuario.getCuenta()+" AND " +
                                            "medidor='"+this.ObjUsuario.getMarca_medidor()+"' AND serie='"+this.ObjUsuario.getSerie_medidor()+"'");
    }

    private void changeVinculacion(String _vinculacion){
        this._tempRegistro.clear();
        this._tempRegistro.put("vinculacion",_vinculacion);
        this.FcnSQL.UpdateRegistro( "maestro_clientes",
                this._tempRegistro,
                "id="+this.ObjUsuario.getId()+" AND fecha_programacion='"+this.ObjUsuario.getFecha_programacion()+"' AND " +
                        "nodo='"+this.ObjUsuario.getNodo()+"' AND cuenta="+this.ObjUsuario.getCuenta()+" AND " +
                        "medidor='"+this.ObjUsuario.getMarca_medidor()+"' AND serie='"+this.ObjUsuario.getSerie_medidor()+"'");
    }

    public boolean registrarInformacion(){
        boolean _retorno = false;
        this._tempRegistro.clear();
        this._tempRegistro.put("id",this.ObjUsuario.getId());
        this._tempRegistro.put("fecha_programacion",this.ObjUsuario.getFecha_programacion());
        this._tempRegistro.put("nodo",              this.ObjUsuario.getNodo());
        this._tempRegistro.put("new_nodo",          this.ObjUsuario.getNew_nodo());
        this._tempRegistro.put("cuenta",            this.ObjUsuario.getCuenta());
        this._tempRegistro.put("medidor",           this.ObjUsuario.getMarca_medidor());
        this._tempRegistro.put("serie",             this.ObjUsuario.getSerie_medidor());
        this._tempRegistro.put("poste",             this.ObjUsuario.getNumeroPoste());
        this._tempRegistro.put("lectura",           this.ObjUsuario.getLectura());
        this._tempRegistro.put("observacion",       this.ObjUsuario.getObservacion());
        _retorno =  this.FcnSQL.InsertRegistro("toma_lectura",this._tempRegistro);

        if(_retorno){
            this.setEstado("T");
            this.ObjUsuario.setLeido(true);
        }
        return _retorno;
    }

    public boolean eliminarUsuario(){
        this.setEstado("T");
        this.ObjUsuario.setLeido(true);

        this.changeVinculacion("I");
        this.ObjUsuario.setVinculacion("I");

        return this.FcnSQL.ExistRegistros(  "maestro_clientes",
                                            "id="+this.ObjUsuario.getId()+" AND fecha_programacion='"+this.ObjUsuario.getFecha_programacion()+"' AND " +
                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND cuenta="+this.ObjUsuario.getCuenta()+" AND " +
                                                "medidor='"+this.ObjUsuario.getMarca_medidor()+"' AND serie='"+this.ObjUsuario.getSerie_medidor()+"' AND " +
                                                "estado='T' AND vinculacion='I'");
    }

    public boolean crearUsuario(String _marca, String _serie, String _cuenta, String _nombre, String _direccion){
        this._tempRegistro.clear();
        this._tempRegistro.put("id",this.FcnSQL.IntSelectShieldWhere("maestro_clientes","max(id)","nodo='"+this.ObjUsuario.getNodo()+"'")+1);
        this._tempRegistro.put("fecha_programacion",this.FcnTime.GetFecha());
        this._tempRegistro.put("nodo",              this.ObjUsuario.getNodo());

        if(_cuenta.isEmpty()){
            this._tempRegistro.put("cuenta",-1);
        }else{
            this._tempRegistro.put("cuenta",Integer.parseInt(_cuenta));
        }

        this._tempRegistro.put("medidor", _marca);
        this._tempRegistro.put("serie", _serie);
        this._tempRegistro.put("nombre", _nombre);
        this._tempRegistro.put("direccion", _direccion);
        this._tempRegistro.put("vinculacion","N");
        this._tempRegistro.put("estado", "P");
        return this.FcnSQL.InsertRegistro("maestro_clientes",this._tempRegistro);
    }
}
