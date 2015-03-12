package clases;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import controlmacro.FormInicioSession;
import sistema.SQLite;

/**
 * Created by JULIANEDUARDO on 09/03/2015.
 */
public class ClassTipoUso {
    private static Context context;
    private SQLite FcnSQL;
    private static ClassTipoUso         ourInstance;
    private ArrayList<ContentValues>    _tempTabla;
    private ContentValues               _tempRegistro;
    private static  ArrayList<String>   _lstTipoUso;

    //private static ClassCritica ourInstance = new ClassCritica();

    public static ClassTipoUso getInstance(Context _ctx) {
        if(ourInstance == null){
            ourInstance = new ClassTipoUso(_ctx);
        }else{
            context = _ctx;
        }
        return ourInstance;
    }

    private ClassTipoUso(Context _ctx) {
        this.context    = _ctx;
        this._tempRegistro  = new ContentValues();
        this._tempTabla     = new ArrayList<ContentValues>();
        this.FcnSQL         = new SQLite(this.context, FormInicioSession.path_files_app);
        this._lstTipoUso    = new ArrayList<String>();
    }


    public static ClassTipoUso getInstance(){
        return ourInstance;
    }


    public ArrayList<String> getTipoUso() {
        this._lstTipoUso.clear();
        this._tempTabla = this.FcnSQL.SelectData(   "param_tipos_uso","id_uso, descripcion","id_uso IS NOT NULL");

        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            this._lstTipoUso.add(this._tempRegistro.getAsString("id_uso")+"-"+this._tempRegistro.getAsString("descripcion"));
        }
        return this._lstTipoUso;
    }


    public int getCodeTipoUso(String _tipoUso){
        String newTipoUso[] = _tipoUso.split("\\-");
        return Integer.parseInt(newTipoUso[0]);
    }
}
