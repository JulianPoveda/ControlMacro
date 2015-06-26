package clases;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import controlmacro.FormInicioSession;
import sistema.SQLite;

/**
 * Created by sypelcdesarrollo on 30/03/15.
 */
public class ClassDataSpinner {

    private static Context context;
    private SQLite  FcnSQL;
    private static ClassDataSpinner ourInstance;
    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();

    public static ClassDataSpinner getInstance(Context _ctx){
        if(ourInstance==null){
            ourInstance = new ClassDataSpinner(_ctx);
        }
        else{
            context = _ctx;
        }
        return ourInstance;
    }

    private ClassDataSpinner(Context _ctx){
        this.context = _ctx;
        this.FcnSQL         = new SQLite(this.context, FormInicioSession.path_files_app);
    }

    public ArrayList<String> getDataSpinnerTipologia(String _spinner){
        _tempTabla.clear();
        ContentValues _tempRegistro = new ContentValues();
        ArrayList<String> tipologia = new ArrayList<String>();

        _tempTabla = FcnSQL.SelectData("valores_spinner","tipologia","nombre_spinner='"+_spinner+"'");
        for(int i=0;i<_tempTabla.size();i++){
            _tempRegistro = _tempTabla.get(i);
            tipologia.add(_tempRegistro.getAsString("tipologia"));
        }
        return tipologia;
    }

    public ArrayList<String> getDataSpinnerSubTipologia(String _spinner, String _tipologia){
        _tempTabla.clear();
        ContentValues _tempRegistro = new ContentValues();
        ArrayList<String> subTipologia = new ArrayList<String>();

        _tempTabla = FcnSQL.SelectData("valores_spinner","subtipologia","nombre_spinner='"+_spinner+"' AND tipologia='"+_tipologia+"'");
        for(int i=0;i<_tempTabla.size();i++){
            _tempRegistro = _tempTabla.get(i);
            subTipologia.add(_tempRegistro.getAsString("subtipologia"));
        }
        return subTipologia;
    }

    public ArrayList<String> getDataSpinnerPostes(String _nodo){
        _tempTabla.clear();
        ContentValues _temRegistro = new ContentValues();
        ArrayList<String> listadoPostes = new ArrayList<String>();

        _tempTabla = FcnSQL.SelectData("nodo_postes","item","nodo='"+_nodo+"' AND tipo<>'LuminariaPedestal'");
        for(int i=0;i<_tempTabla.size();i++){
            _temRegistro = _tempTabla.get(i);
            listadoPostes.add(_temRegistro.getAsString("item"));
        }
        return listadoPostes;
    }


}
