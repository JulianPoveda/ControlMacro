package async_task;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;

import clases.ClassConfiguracion;
import clases.ClassSession;
import controlmacro.FormInicioSession;
import sistema.Archivos;
import sistema.SQLite;

/**
 * Created by SypelcDesarrollo on 12/02/2015.
 */
public class UploadLecturas extends AsyncTask<String, Void, Integer> {
    private Intent              new_form;
    private ClassConfiguracion  FcnCfg;
    private Archivos            FcnArch;
    private SQLite              FcnSQL;
    private ClassSession        Usuario;

    private Context Context;

    private String URL;
    private String NAMESPACE;
    private String Respuesta   = "";

    private ContentValues				_tempRegistro 	    = new ContentValues();
    private ContentValues				_tempRegistro1 	    = new ContentValues();
    private ArrayList<ContentValues>	_tempTabla	    	= new ArrayList<ContentValues>();
    private ArrayList<ContentValues>	_tempTabla1		    = new ArrayList<ContentValues>();
    private String                      InformacionCarga;
    private String                      InformacionCargaPoste;

    private static final String METHOD_NAME	= "UploadTrabajo";
    private static final String SOAP_ACTION	= "UploadTrabajo";

    SoapPrimitive response = null;
    ProgressDialog _pDialog;


    public UploadLecturas(Context context){
        this.Context    = context;
        this.FcnCfg     = ClassConfiguracion.getInstance(this.Context);
        this.FcnSQL     = new SQLite(this.Context, FormInicioSession.path_files_app);
        this.FcnArch	= new Archivos(this.Context, FormInicioSession.path_files_app, 10);
        this.Usuario    = ClassSession.getInstance(this.Context);
    }

    protected void onPreExecute() {
        this.URL        = this.FcnCfg.getIp_server() + ":" + this.FcnCfg.getPort() + "/" + this.FcnCfg.getModule_web_service() + "/" + this.FcnCfg.getWeb_service();
        this.NAMESPACE  = this.FcnCfg.getIp_server() + ":" + this.FcnCfg.getPort() + "/" + this.FcnCfg.getModule_web_service();
    }

    @Override
    protected Integer doInBackground(String... params) {
        int _retorno    = 0;
        //this.InformacionCarga.clear();

        this.InformacionCarga = "";
        this._tempTabla	= this.FcnSQL.SelectData("maestro_nodos", "fecha_asignacion,nodo","estado='T'");
        for(int i=0; i<this._tempTabla.size();i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this._tempTabla1	= this.FcnSQL.SelectData(   "toma_lectura",
                                                            "fecha_programacion,nodo,new_nodo,cuenta,medidor,serie,poste,lectura,observacion,fecha_registro",
                                                            "nodo='"+this._tempRegistro.getAsString("nodo")+"'");

            for(int j=0; j<this._tempTabla1.size();j++){
                this._tempRegistro1 = this._tempTabla1.get(j);
                this.InformacionCarga += this._tempRegistro1.getAsString("fecha_programacion")+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("new_nodo") + "," + this._tempRegistro1.getAsString("cuenta") + "," +
                        "" + this._tempRegistro1.getAsString("medidor") + "," + this._tempRegistro1.getAsString("serie") + "," + this._tempRegistro1.getAsString("poste") + "," +
                        "" + this._tempRegistro1.getAsString("lectura") + "," + this._tempRegistro1.getAsString("observacion") + "," + this._tempRegistro1.getAsString("fecha_registro") + "\r\n";
            }
        }

        this.FcnArch.DoFile("Descarga",this.Usuario.getCodigo()+"_"+"lectura_nodo"+".txt",this.InformacionCarga);

        this._tempRegistro1.clear();
        this._tempTabla1.clear();
        this.InformacionCargaPoste="";

        for(int i=0; i<this._tempTabla.size();i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this._tempTabla1	= this.FcnSQL.SelectData(   "nodo_postes",
                    "nodo,item,longitud,latitud,tipo,compartido,estado,material,altura,estructura,observacion",
                    "nodo='"+this._tempRegistro.getAsString("nodo")+"'");

            for(int j=0; j<this._tempTabla1.size();j++){
                this._tempRegistro1 = this._tempTabla1.get(j);
                this.InformacionCargaPoste += "POSTE,"+this._tempRegistro.getAsString("fecha_asignacion")+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("item") + "," + this._tempRegistro1.getAsString("longitud") + "," +
                        "" + this._tempRegistro1.getAsString("latitud") + "," + this._tempRegistro1.getAsString("tipo") + "," + this._tempRegistro1.getAsString("compartido") + "," +
                        "" + this._tempRegistro1.getAsString("estado") + ","+this._tempRegistro1.getAsString("material")+","+this._tempRegistro1.getAsString("altura")+"," + this._tempRegistro1.getAsString("estructura") + "," + this._tempRegistro1.getAsString("observacion") + "\r\n";
            }
        }

        this._tempRegistro1.clear();
        this._tempTabla1.clear();

        for(int i=0; i<this._tempTabla.size();i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this._tempTabla1	= this.FcnSQL.SelectData(   "postes_equipos",
                    "nodo,item,nombre,capacidad,unidades",
                    "nodo='"+this._tempRegistro.getAsString("nodo")+"'");

            for(int j=0; j<this._tempTabla1.size();j++){
                this._tempRegistro1 = this._tempTabla1.get(j);
                this.InformacionCargaPoste +="EQUIPOS,"+ this._tempRegistro.getAsString("fecha_asignacion")+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("item") + "," + this._tempRegistro1.getAsString("nombre") + "," +
                        "" + this._tempRegistro1.getAsString("capacidad") + "," + this._tempRegistro1.getAsString("unidades")  + "\r\n";
            }
        }

        this._tempRegistro1.clear();
        this._tempTabla1.clear();

        for(int i=0; i<this._tempTabla.size();i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this._tempTabla1	= this.FcnSQL.SelectData(   "postes_lineas",
                    "nodo,item,faseA,faseB,faseC,faseAP,faseN,conductor",
                    "nodo='"+this._tempRegistro.getAsString("nodo")+"'");

            for(int j=0; j<this._tempTabla1.size();j++){
                this._tempRegistro1 = this._tempTabla1.get(j);
                this.InformacionCargaPoste += "LINEAS,"+this._tempRegistro.getAsString("fecha_asignacion")+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("item") + "," + this._tempRegistro1.getAsString("faseA") + "," +
                        "" + this._tempRegistro1.getAsString("faseB") + "," + this._tempRegistro1.getAsString("faseC") + "," + this._tempRegistro1.getAsString("faseAP") + "," +
                        "" + this._tempRegistro1.getAsString("faseN") + ","+this._tempRegistro1.getAsString("conductor") + "\r\n";
            }
        }

        this._tempRegistro1.clear();
        this._tempTabla1.clear();

        for(int i=0; i<this._tempTabla.size();i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this._tempTabla1	= this.FcnSQL.SelectData(   "postes_luminarias",
                    "id,nodo,item,codigo,capacidad,tipo,estado,propietario,tierra",
                    "nodo='"+this._tempRegistro.getAsString("nodo")+"'");

            for(int j=0; j<this._tempTabla1.size();j++){
                this._tempRegistro1 = this._tempTabla1.get(j);
                this.InformacionCargaPoste += "LUMINARIAS,"+this._tempRegistro.getAsString("fecha_asignacion")+","+this._tempRegistro1.getAsString("id") +"," +this._tempRegistro1.getAsString("nodo") +"," + this._tempRegistro1.getAsString("item") + ","
                        + this._tempRegistro1.getAsString("codigo") + "," + this._tempRegistro1.getAsString("capacidad") + "," + this._tempRegistro1.getAsString("tipo") + "," + this._tempRegistro1.getAsString("estado") + "," +
                       "" + this._tempRegistro1.getAsString("propietario") + ","+this._tempRegistro1.getAsString("tierra") + "\r\n";
            }
        }

        this.FcnArch.DoFile("Descarga",this.Usuario.getCodigo()+"_"+"formato_redes"+".txt",this.InformacionCargaPoste);

        try{
            SoapObject so=new SoapObject(NAMESPACE, this.METHOD_NAME);
            so.addProperty("usuario", this.Usuario.getCodigo());
            so.addProperty("informacionlectura",this.FcnArch.FileToArrayBytes("Descarga", this.Usuario.getCodigo()+"_"+"lectura_nodo"+".txt",true));
            so.addProperty("informacionredes",this.FcnArch.FileToArrayBytes("Descarga", this.Usuario.getCodigo()+"_"+"formato_redes"+".txt",true));

            SoapSerializationEnvelope sse=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(sse);
            sse.dotNet=true;
            sse.setOutputSoapObject(so);
            HttpTransportSE htse=new HttpTransportSE(URL);
            htse.call(SOAP_ACTION, sse);
            response=(SoapPrimitive) sse.getResponse();

            if(response==null) {
                this.Respuesta = "-1";
            }else if(response.toString().isEmpty()){
                this.Respuesta = "-2";
            }else {
                try {
                    String informacion[] = new String(response.toString()).trim().split("\\|");
                    if(informacion.length>0){
                        this._tempRegistro.clear();
                        for(int i=0;i<informacion.length;i++){
                            this.FcnSQL.DeleteRegistro("toma_lectura","id="+informacion[i]+"");
                        }
                    }
                    _retorno = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    _retorno = -3;
                }
            }
        } catch (Exception e) {
            this.Respuesta = e.toString();
            _retorno = -4;
        }
        return _retorno;
    }

    @Override
    protected void onPostExecute(Integer rta) {

    }
}




