package clases;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import controlmacro.FormInicioSession;
import sistema.Archivos;
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


    public ClassTomaLectura(Context _ctx, String _nodo){
        this.Ctx            = _ctx;
        this.ObjUsuario     = new UsuarioLeido();
        this.FcnSQL         = new SQLite(this.Ctx, FormInicioSession.path_files_app);
        this.FcnArchivos    = new Archivos(this.Ctx, FormInicioSession.path_files_app,10);
        this.ObjUsuario.setNodo(_nodo);

        if(!this.FcnArchivos.ExistFolderOrFile(FormInicioSession.sub_path_pictures,true)){
            this.FcnArchivos.MakeDirectory(FormInicioSession.sub_path_pictures,true);
        }

        /*this.ObjUsuario.setFlagSearch(false);
        this.ObjUsuario.setBackupRuta(null);
        this.ObjUsuario.setBackupConsecutivo(-1);*/
    }

    public ClassTomaLectura(Context _ctx){
        this.Ctx    = _ctx;
        this.FcnSQL = new SQLite(this.Ctx, FormInicioSession.path_files_app);

    }


    public boolean getDatosUsuario(boolean _next){
        boolean _retorno  = false;
        if(this.ObjUsuario.getId() == -1){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND estado='P' ORDER BY id ASC");
        }/*else if(this.ObjUsuario.isFlagSearch()){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "ruta, id_serial,id_secuencia, cuenta,marca_medidor,serie_medidor,nombre,direccion,tipo_uso,factor_multiplicacion,id_serial_1,lectura_anterior_1,tipo_energia_1,promedio_1,id_serial_2,lectura_anterior_2,tipo_energia_2,promedio_2,id_serial_3,lectura_anterior_3,tipo_energia_3,promedio_3,estado,id_municipio,anomalia_anterior_1",
                                                                "ruta='"+this.ObjUsuario.getBackupRuta()+"' AND id_secuencia="+this.ObjUsuario.getBackupConsecutivo()+" ORDER BY id_secuencia ASC");
            this.ObjUsuario.setFlagSearch(false);
            this.ObjUsuario.setBackupRuta(null);
            this.ObjUsuario.setBackupConsecutivo(-1);
        }*/else if(_next){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND id>"+this.ObjUsuario.getId()+" AND estado='P' ORDER BY id ASC");
        }else{
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("maestro_clientes",
                                                                "id, nodo, cuenta, medidor, serie, nombre, direccion, estado, vinculacion",
                                                                "nodo='"+this.ObjUsuario.getNodo()+"' AND id<"+this.ObjUsuario.getId()+" AND estado='P' ORDER BY id DESC");
        }

        if(this._tempRegistro.size()>0){
            _retorno = true;
            this.setInfUsuario();
        }
        return _retorno;
    }

    public void ConfirmacionLectura(String _lectura){
        String lectura = _lectura;

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
        this.ObjUsuario.setId(this._tempRegistro.getAsInteger("id"));
        this.ObjUsuario.setCuenta(this._tempRegistro.getAsInteger("cuenta"));
        this.ObjUsuario.setMarca_medidor(this._tempRegistro.getAsString("medidor"));
        this.ObjUsuario.setSerie_medidor(this._tempRegistro.getAsString("serie"));
        this.ObjUsuario.setNombre(this._tempRegistro.getAsString("nombre"));
        this.ObjUsuario.setDireccion(this._tempRegistro.getAsString("direccion"));
        this.ObjUsuario.setEstado(this._tempRegistro.getAsString("estado"));
        this.ObjUsuario.setVinculacion(this._tempRegistro.getAsString("vinculacion"));
        this.ObjUsuario.setLeido(!this._tempRegistro.getAsString("estado").equals("P"));

        /*this.getNumeroFotos();
        this.getLastLectura();
        this.getNumIntentos();*/
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

    public void getLastLectura(){
        /*if(this.FcnSQL.ExistRegistros(  "toma_lectura",
                                        "id_serial1="+this.ObjUsuario.getId_serial1()+" AND id_serial2="+this.ObjUsuario.getId_serial2()+" AND id_serial3="+this.ObjUsuario.getId_serial3())){
            this._tempRegistro = this.FcnSQL.SelectDataRegistro("toma_lectura",
                                                                "lectura1,lectura2,lectura3",
                                                                "id_serial1="+this.ObjUsuario.getId_serial1()+" AND id_serial2="+this.ObjUsuario.getId_serial2()+" AND id_serial3="+this.ObjUsuario.getId_serial3()+ " ORDER BY id DESC ");
            this.ObjUsuario.setLectura1(this._tempRegistro.getAsInteger("lectura1"));
            this.ObjUsuario.setLectura2(this._tempRegistro.getAsInteger("lectura2"));
            this.ObjUsuario.setLectura3(this._tempRegistro.getAsInteger("lectura3"));

        }else{
            this.ObjUsuario.setLectura1(0);
            this.ObjUsuario.setLectura2(0);
            this.ObjUsuario.setLectura3(0);
        }*/
    }


    public UsuarioLeido getInfUsuario() {
        return this.ObjUsuario;
    }


    public void getNumeroFotos(){
        /*this.ObjUsuario.setCountFotos(this.FcnArchivos.numArchivosInFolderBeginByName(  FormInicioSession.sub_path_pictures,
                                                                                        this.ObjUsuario.getCuenta()+"",
                                                                                        true));*/
    };


    public void getNumIntentos(){
        /*this.ObjUsuario.setIntentos(this.FcnSQL.CountRegistrosWhere("toma_lectura",
                                    "id_serial1="+this.ObjUsuario.getId_serial1()+" AND id_serial2="+this.ObjUsuario.getId_serial2()+" AND id_serial3="+this.ObjUsuario.getId_serial3()));

        if(this.ObjUsuario.getIntentos() == 0){
            this.ObjUsuario.setOldLectura1(0);
            this.ObjUsuario.setOldLectura2(0);
            this.ObjUsuario.setOldLectura3(0);
            this.ObjUsuario.setConfirmLectura(false);
        }else if(this.ObjUsuario.getIntentos() == 1){
            this.ObjUsuario.setOldLectura1(this.ObjUsuario.getLectura1());
            this.ObjUsuario.setOldLectura2(this.ObjUsuario.getLectura2());
            this.ObjUsuario.setOldLectura3(this.ObjUsuario.getLectura3());
            this.ObjUsuario.setConfirmLectura(false);
        }else if(this.ObjUsuario.getIntentos() == 2 || this.ObjUsuario.getIntentos() == 3){
            if(this.ObjUsuario.getOldLectura1() == this.ObjUsuario.getLectura1() &&
                    this.ObjUsuario.getOldLectura2() == this.ObjUsuario.getLectura2() &&
                    this.ObjUsuario.getOldLectura3() == this.ObjUsuario.getLectura3()){
                this.ObjUsuario.setConfirmLectura(true);
            }else{
                this.ObjUsuario.setConfirmLectura(false);
            }

            this.ObjUsuario.setOldLectura1(this.ObjUsuario.getLectura1());
            this.ObjUsuario.setOldLectura2(this.ObjUsuario.getLectura2());
            this.ObjUsuario.setOldLectura3(this.ObjUsuario.getLectura3());
        }*/
    }


    private void setEstado(String _estado){
        /*this._tempRegistro.clear();
        this._tempRegistro.put("estado",_estado);
        this.FcnSQL.UpdateRegistro( "maestro_clientes",
                                    this._tempRegistro,
                                    "id_serial="+this.ObjUsuario.getId_serial());*/
    }


    public void preCritica(String _lectura1, String _lectura2, String _lectura3){
        /*if(_lectura1.isEmpty() && this.ObjUsuario.isView_tipo_energia1() && this.ObjUsuario.isNeedLectura()){
            //falta la lectura 1
        }else if(_lectura2.isEmpty() && this.ObjUsuario.isView_tipo_energia2() && this.ObjUsuario.isNeedLectura()){

        }else if(_lectura3.isEmpty() && this.ObjUsuario.isView_tipo_energia3() && this.ObjUsuario.isNeedLectura()){

        }else{
            if(this.ObjUsuario.isView_tipo_energia1() && this.ObjUsuario.isNeedLectura()){

            }else{
                this.ObjUsuario.setLectura1(-1);
                this.ObjUsuario.setCritica1(1);
            }

            if(this.ObjUsuario.isView_tipo_energia2() && this.ObjUsuario.isNeedLectura()){

            }else{
                this.ObjUsuario.setLectura2(-1);
                this.ObjUsuario.setCritica2(1);
            }

            if(this.ObjUsuario.isView_tipo_energia3() && this.ObjUsuario.isNeedLectura()){

            }else{
                this.ObjUsuario.setLectura3(-1);
                this.ObjUsuario.setCritica3(1);
            }
        }*/
    }


    public boolean guardarLectura(String _lectura1, String _lectura2, String _lectura3, String _mensaje){
        boolean _retorno = false;

        /*if(_lectura1.isEmpty() && this.ObjUsuario.isView_tipo_energia1() && this.ObjUsuario.isNeedLectura()){
            //falta la lectura 1
        }else if(_lectura2.isEmpty() && this.ObjUsuario.isView_tipo_energia2() && this.ObjUsuario.isNeedLectura()){

        }else if(_lectura3.isEmpty() && this.ObjUsuario.isView_tipo_energia3() && this.ObjUsuario.isNeedLectura()){

        }else{
            this.ObjUsuario.setMensaje(_mensaje);

            if(this.ObjUsuario.isView_tipo_energia1() && this.ObjUsuario.isNeedLectura()){
                this.ObjUsuario.setLectura1(Integer.parseInt(_lectura1));
            }else{
                this.ObjUsuario.setLectura1(-1);
            }

            if(this.ObjUsuario.isView_tipo_energia2() && this.ObjUsuario.isNeedLectura()){
                this.ObjUsuario.setLectura2(Integer.parseInt(_lectura2));
            }else{
                this.ObjUsuario.setLectura2(-1);
            }

            if(this.ObjUsuario.isView_tipo_energia3() && this.ObjUsuario.isNeedLectura()){
                this.ObjUsuario.setLectura3(Integer.parseInt(_lectura3));
            }else{
                this.ObjUsuario.setLectura3(-1);
            }
        }
        this._tempRegistro.clear();
        this._tempRegistro.put("id_serial1",this.ObjUsuario.getId_serial1());
        this._tempRegistro.put("id_serial2",this.ObjUsuario.getId_serial2());
        this._tempRegistro.put("id_serial3",this.ObjUsuario.getId_serial3());
        this._tempRegistro.put("anomalia",  this.ObjUsuario.getAnomalia());
        this._tempRegistro.put("mensaje",   this.ObjUsuario.getMensaje());
        this._tempRegistro.put("lectura1",  this.ObjUsuario.getLectura1());
        this._tempRegistro.put("lectura2",  this.ObjUsuario.getLectura2());
        this._tempRegistro.put("lectura3",  this.ObjUsuario.getLectura3());
        this._tempRegistro.put("critica1",  this.ObjUsuario.getCritica1());
        this._tempRegistro.put("critica2",  this.ObjUsuario.getCritica2());
        this._tempRegistro.put("critica3",  this.ObjUsuario.getCritica3());
        this._tempRegistro.put("tipo_uso",  this.ObjUsuario.getNewTipoUso());

        _retorno =  this.FcnSQL.InsertRegistro("toma_lectura",this._tempRegistro);
        this.getNumIntentos();

        if(!this.ObjUsuario.isNeedLectura() || this.ObjUsuario.getIntentos() == 3 || !this.ObjUsuario.isHaveCritica() || this.ObjUsuario.isConfirmLectura()){
            this.ObjUsuario.setLeido(true);
            this.setEstado("T");
            //this.ObjUsuario.setHaveCritica(false);
        }*/
        return _retorno;
    }
}
