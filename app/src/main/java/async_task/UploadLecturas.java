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
import android.widget.Toast;

import clases.ClassConfiguracion;
import clases.ClassFlujoInformacion;
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
    private ClassFlujoInformacion FcnInformacion;

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
        this.FcnInformacion             = new ClassFlujoInformacion(this.Context);
    }

    protected void onPreExecute() {
        this.URL        = this.FcnCfg.getIp_server() + ":" + this.FcnCfg.getPort() + "/" + this.FcnCfg.getModule_web_service() + "/" + this.FcnCfg.getWeb_service();
        this.NAMESPACE  = this.FcnCfg.getIp_server() + ":" + this.FcnCfg.getPort() + "/" + this.FcnCfg.getModule_web_service();

        Toast.makeText(this.Context, "Iniciando conexion con el servidor, por favor espere...", Toast.LENGTH_SHORT).show();
        _pDialog = new ProgressDialog(this.Context);
        _pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _pDialog.setMessage("Ejecutando operaciones...");
        _pDialog.setCancelable(false);
        _pDialog.setProgress(0);
        _pDialog.setMax(100);
        _pDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        int _retorno    = 0;
        //this.InformacionCarga.clear();

        String nodo = params[0];

                this.InformacionCarga = "";
                this._tempTabla	= this.FcnSQL.SelectData(   "toma_lectura",
                            "fecha_programacion,nodo,new_nodo,cuenta,medidor,serie,poste,lectura,observacion,fecha_registro",
                            "nodo='"+nodo+"'");

                for(int j=0; j<this._tempTabla.size();j++){
                        this._tempRegistro = this._tempTabla.get(j);
                        String vinculacion = this.FcnSQL.StrSelectShieldWhere("maestro_clientes","vinculacion","nodo='"+nodo+"' AND fecha_programacion ='"+this._tempRegistro.getAsString("fecha_programacion")+"' AND cuenta='"+this._tempRegistro.getAsString("cuenta")+"' AND serie='"+this._tempRegistro.getAsString("serie")+"'");
                        this.InformacionCarga += this._tempRegistro.getAsString("fecha_programacion")+","+this._tempRegistro.getAsString("nodo") + "," + this._tempRegistro.getAsString("new_nodo") + "," + this._tempRegistro.getAsString("cuenta") + "," +
                                "" + this._tempRegistro.getAsString("medidor") + "," + this._tempRegistro.getAsString("serie") + "," + this._tempRegistro.getAsString("poste") + "," +
                                "" + this._tempRegistro.getAsString("lectura") + "," + this._tempRegistro.getAsString("observacion") + "," + this._tempRegistro.getAsString("fecha_registro") +","+vinculacion+"\r\n";
                }

                this.FcnArch.DoFile("Descarga",this.Usuario.getCodigo()+"_"+"lectura_nodo"+".txt",this.InformacionCarga);

                this._tempRegistro1.clear();
                this._tempTabla1.clear();
                this.InformacionCargaPoste="";
                String fecha_asignacion = this.FcnSQL.StrSelectShieldWhere("maestro_nodos","fecha_asignacion","nodo='"+nodo+"'");

                this._tempTabla1	= this.FcnSQL.SelectData(   "nodo_postes",
                                                            "nodo,item,longitud,latitud,tipo,compartido,estado,material,altura,estructura,observacion",
                                                            "nodo='"+nodo+"'");
                for(int j=0; j<this._tempTabla1.size();j++){
                    this._tempRegistro1 = this._tempTabla1.get(j);
                    this.InformacionCargaPoste += "POSTE,"+fecha_asignacion+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("item") + "," + this._tempRegistro1.getAsString("longitud").replace("°","-").replace("'","--").replace("\"","---") + "," +
                                "" + this._tempRegistro1.getAsString("latitud").replace("°","-").replace("'","--").replace("\"","---")+ "," + this._tempRegistro1.getAsString("tipo") + "," + this._tempRegistro1.getAsString("compartido") + "," +
                                "" + this._tempRegistro1.getAsString("estado") + ","+this._tempRegistro1.getAsString("material")+","+this._tempRegistro1.getAsString("altura")+"," + this._tempRegistro1.getAsString("estructura") + "," + this._tempRegistro1.getAsString("observacion") + "\r\n";
                }

                this._tempRegistro1.clear();
                this._tempTabla1.clear();

                this._tempTabla1	= this.FcnSQL.SelectData(   "postes_equipos",
                                                            "id,nodo,item,nombre,capacidad,unidades",
                                                            "nodo='"+nodo+"'");
                for(int j=0; j<this._tempTabla1.size();j++){
                    this._tempRegistro1 = this._tempTabla1.get(j);
                    this.InformacionCargaPoste +="EQUIPOS,"+ fecha_asignacion+","+this._tempRegistro1.getAsString("id")+","+this._tempRegistro1.getAsString("nodo")+"," +this._tempRegistro1.getAsString("item")+","+this._tempRegistro1.getAsString("nombre")+","+
                                ""+this._tempRegistro1.getAsString("capacidad")+","+ this._tempRegistro1.getAsString("unidades")+"\r\n";
                }

                this._tempRegistro1.clear();
                this._tempTabla1.clear();

                this._tempTabla1	= this.FcnSQL.SelectData(   "postes_lineas",
                                                            "nodo,item,faseA,faseB,faseC,faseAP,faseN,conductor",
                                                            "nodo='"+nodo+"'");
                for(int j=0; j<this._tempTabla1.size();j++){
                    this._tempRegistro1 = this._tempTabla1.get(j);
                    this.InformacionCargaPoste += "LINEAS,"+fecha_asignacion+","+this._tempRegistro1.getAsString("nodo") + "," + this._tempRegistro1.getAsString("item") + "," + this._tempRegistro1.getAsString("faseA") + "," +
                                "" + this._tempRegistro1.getAsString("faseB") + "," + this._tempRegistro1.getAsString("faseC") + "," + this._tempRegistro1.getAsString("faseAP") + "," +
                                "" + this._tempRegistro1.getAsString("faseN") + ","+this._tempRegistro1.getAsString("conductor") + "\r\n";
                }

                this._tempRegistro1.clear();
                this._tempTabla1.clear();

                this._tempTabla1	= this.FcnSQL.SelectData(   "postes_luminarias",
                                                            "id,nodo,item,codigo,capacidad,tipo,estado,propietario,tierra",
                                                            "nodo='"+nodo+"'");
                for(int j=0; j<this._tempTabla1.size();j++){
                        this._tempRegistro1 = this._tempTabla1.get(j);
                        this.InformacionCargaPoste += "LUMINARIAS,"+fecha_asignacion+","+this._tempRegistro1.getAsString("id") +"," +this._tempRegistro1.getAsString("nodo") +"," + this._tempRegistro1.getAsString("item") + ","
                                + this._tempRegistro1.getAsString("codigo") + "," + this._tempRegistro1.getAsString("capacidad") + "," + this._tempRegistro1.getAsString("tipo") + "," + this._tempRegistro1.getAsString("estado") + "," +
                                "" + this._tempRegistro1.getAsString("propietario") + ","+this._tempRegistro1.getAsString("tierra") + "\r\n";
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
                    String informacion[] = new String(response.toString()).trim().split("\\,");
                    for(int i=0;i<informacion.length;i++){
                        this.FcnInformacion.FinalizarUpload(informacion[i],"\\|");
                        this.onProgressUpdate(i*100/informacion.length);
                    }
                    this.FcnInformacion.FinalizarNodo(nodo);
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
        if(rta==1){
            Toast.makeText(this.Context,"Carga Finalizada.", Toast.LENGTH_LONG).show();
        }else if(rta==-1){
            Toast.makeText(this.Context,"Intento fallido, el servidor no ha respondido.", Toast.LENGTH_SHORT).show();
        }else if(rta==-2){
            Toast.makeText(this.Context,"No hay nodos pendientes para cargar.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.Context,"Error desconocido.", Toast.LENGTH_SHORT).show();
        }
        _pDialog.dismiss();
    }

    protected void onProgressUpdate(Integer... values) {
        int progreso = values[0].intValue();
        _pDialog.setProgress(progreso);
    }
}




