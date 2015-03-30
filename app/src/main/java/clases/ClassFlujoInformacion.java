package clases;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;

import controlmacro.FormInicioSession;
import sistema.SQLite;

/**
 * Created by JULIANEDUARDO on 03/02/2015.
 */
public class ClassFlujoInformacion {
    private Context         context;
    private String          _campos[];
    private ContentValues   _tempRegistro;

    private static SQLite FcnSQL;

    private String currentDateandTime;
    ProgressDialog _pDialog;


    public ClassFlujoInformacion(Context _ctx){
        this.context        = _ctx;
        this._tempRegistro  = new ContentValues();
        this.FcnSQL         = new SQLite(this.context, FormInicioSession.path_files_app);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        currentDateandTime = sdf.format(date);

    }


    public void EliminarParametros(){
        /**Eliminar los parametros previamente cargados**/
        this.FcnSQL.DeleteRegistro("param_usuarios","perfil<>0");
        this.FcnSQL.DeleteRegistro("param_municipios","id_municipio IS NOT NULL");
        this.FcnSQL.DeleteRegistro("param_anomalias","id_anomalia IS NOT NULL");
        this.FcnSQL.DeleteRegistro("param_critica","descripcion IS NOT NULL");
    }



    public void CargarParametros(String _informacion, String _delimitador){
        /**Se inicia el registro de los nuevos parametros**/
        this._campos = _informacion.split(_delimitador);
        this._tempRegistro.clear();
        if(this._campos[0].equals("Inspector")){
            this._tempRegistro.put("id_inspector",this._campos[1]);
            this._tempRegistro.put("nombre",this._campos[2]);
            this._tempRegistro.put("perfil",1);
            this.FcnSQL.InsertRegistro("param_usuarios",this._tempRegistro);
        }else if(this._campos[0].equals("Spinners")){
            this._tempRegistro.put("id",this._campos[1]);
            this._tempRegistro.put("activity",this._campos[2]);
            this._tempRegistro.put("nombre_spinner",this._campos[3]);
            this._tempRegistro.put("tipologia",this._campos[4]);
            this._tempRegistro.put("subtipologia",this._campos[5]);
            this.FcnSQL.InsertRegistro("valores_spinner",this._tempRegistro);
        }/*else if(this._campos[0].equals("Anomalia")){
            this._tempRegistro.put("id_anomalia",this._campos[1]);
            this._tempRegistro.put("descripcion",this._campos[2]);
            this._tempRegistro.put("aplica_residencial",this._campos[3]);
            this._tempRegistro.put("aplica_no_residencial",this._campos[4]);
            this._tempRegistro.put("lectura",this._campos[5]);
            this._tempRegistro.put("mensaje",this._campos[6]);
            this._tempRegistro.put("foto",this._campos[7]);
            this.FcnSQL.InsertRegistro("param_anomalias",this._tempRegistro);
        }else if(this._campos[0].equals("Critica")){
            this._tempRegistro.put("minimo",this._campos[1]);
            this._tempRegistro.put("maximo",this._campos[2]);
            this._tempRegistro.put("descripcion",this._campos[3]);
            this.FcnSQL.InsertRegistro("param_critica",this._tempRegistro);
        }else if(this._campos[0].equals("Uso")){
            this._tempRegistro.put("id_uso",this._campos[1]);
            this._tempRegistro.put("descripcion",this._campos[2]);
            this.FcnSQL.InsertRegistro("param_tipos_uso",this._tempRegistro);
        }*/
    }

    public void CargarTrabajo(String _informacion, String _delimitador, int secuencia){
        /**Se inicia el registro de la ruta programada**/
        this._campos = _informacion.split(_delimitador);
        this._tempRegistro.clear();
        if(this._campos[0].equals("MaestroNodos")){
            this.FcnSQL.DeleteRegistro("maestro_clientes","nodo='"+this._campos[2]+"' AND fecha_asignacion='"+this._campos[3]+"'");
            this._tempRegistro.put("id_serial", this._campos[1]);
            this._tempRegistro.put("nodo", this._campos[2]);
            this._tempRegistro.put("id_tecnico", this._campos[3]);
            this._tempRegistro.put("fecha_asignacion", this._campos[4]);
            this.FcnSQL.InsertRegistro("maestro_nodos",this._tempRegistro);
        }else if(this._campos[0].equals("MaestroClientes")){
            this._tempRegistro.put("id",this._campos[1]);
            this._tempRegistro.put("fecha_programacion",this._campos[2]);
            this._tempRegistro.put("nodo",this._campos[3]);
            this._tempRegistro.put("cuenta",this._campos[4]);
            this._tempRegistro.put("medidor",this._campos[5]);
            this._tempRegistro.put("serie",this._campos[6]);
            this._tempRegistro.put("nombre",this._campos[7]);
            this._tempRegistro.put("direccion",this._campos[8]);
            this._tempRegistro.put("estado",this._campos[9]);
            this.FcnSQL.InsertRegistro("maestro_clientes",this._tempRegistro);
        }
    }

    public void FinalizarUpload(String _informacion, String _delimitador){
        this._campos = _informacion.split(_delimitador);
        this._tempRegistro.clear();
        if(this._campos[0].equals("LECTURAS")){
            this.FcnSQL.DeleteRegistro("toma_lectura","fecha_programacion='"+this._campos[1]+"' AND nodo='"+this._campos[2]+"' AND cuenta='"+this._campos[3]+"'AND serie='"+this._campos[4]+"'");
            this._tempRegistro.put("estado", "E");
            this.FcnSQL.UpdateRegistro("maestro_clientes",this._tempRegistro,"fecha_programacion='"+this._campos[1]+"' AND nodo='"+this._campos[2]+"' AND cuenta='"+this._campos[3]+"'AND serie='"+this._campos[4]+"'");
        }else if(this._campos[0].equals("EQUIPOS")){
            this.FcnSQL.DeleteRegistro("postes_equipos","id="+this._campos[1]+" AND nodo='"+this._campos[2]+"' AND item="+this._campos[3]+" ");
        }else if(this._campos[0].equals("LINEAS")){
            this.FcnSQL.DeleteRegistro("postes_lineas","nodo='"+this._campos[1]+"' AND item="+this._campos[2]+" ");
        }else if(this._campos[0].equals("POSTE")){
            this.FcnSQL.DeleteRegistro("nodo_postes","nodo='"+this._campos[1]+"' AND item="+this._campos[2]+"");
        }else if(this._campos[0].equals("LUMINARIAS")){
            this.FcnSQL.DeleteRegistro("postes_luminarias","id='"+this._campos[1]+"' AND nodo='"+this._campos[2]+"' AND item="+this._campos[3]+" ");
        }

    }
}
