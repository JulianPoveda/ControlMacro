package clases;

/**
 * Created by JULIANEDUARDO on 24/03/2015.
 */

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import Object.Poste;
import controlmacro.FormInicioSession;
import sistema.SQLite;


public class ClassRedesPoste {
    private Context ctx;
    private String  nodo;


    private ArrayList<ContentValues> _tempTabla;
    private ContentValues   _tempRegistro;
    private SQLite  FcnSQL;

    public ClassRedesPoste(Context _ctx, String _nodo){
        this.ctx    = _ctx;
        this.nodo   = _nodo;
        this.FcnSQL = new SQLite(this.ctx, FormInicioSession.path_files_app);
    }


    public ArrayList<Poste> listaPostes(){
        ArrayList<Poste> listaPostes = new ArrayList<Poste>();
        Poste localPoste = new Poste();

        //this._tempRegistro =



        return listaPostes;
    }
}
