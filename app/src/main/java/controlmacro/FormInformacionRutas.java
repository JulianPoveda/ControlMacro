package controlmacro;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.AdaptadorFourItems;
import Adapter.DetalleFourItems;
import async_task.UploadLecturas;
import clases.ClassSession;
import sistema.SQLite;

/**
 * Created by SypelcDesarrollo on 04/02/2015.
 */
public class FormInformacionRutas extends Activity{
    private String FolderAplicacion;
    private String nodo_seleccionado;

    private Intent          new_form;
    private ListView        listadoRutas;
    private SQLite          sqlConsulta;
    private ClassSession    FcnSession;

    private AdaptadorFourItems listadoRutasAdapter;
    private ArrayList<DetalleFourItems> arrayListadoRutas;

    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();
    private ContentValues _tempRegistro 		= new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_rutas);

        Bundle bundle = getIntent().getExtras();

        this.listadoRutas   = (ListView)findViewById(R.id.InfoListRutas);

        this.FcnSession     = ClassSession.getInstance(this);
        this.sqlConsulta    = new SQLite(this, FormInicioSession.path_files_app);

        this.arrayListadoRutas  = new ArrayList<>();
        this.arrayListadoRutas.clear();

        this._tempTabla = sqlConsulta.SelectData("maestro_nodos","nodo","id_tecnico="+this.FcnSession.getCodigo());
        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            Integer totalR = sqlConsulta.CountRegistrosWhere("maestro_clientes","nodo='"+this._tempRegistro.getAsString("nodo")+"'");
            Integer totalP = sqlConsulta.CountRegistrosWhere("maestro_clientes","nodo='"+this._tempRegistro.getAsString("nodo")+"' AND estado='P'");
            Integer totalL = totalR - totalP;
            arrayListadoRutas.add(new DetalleFourItems( this._tempRegistro.getAsString("nodo"),
                                                        String.valueOf(totalP),
                                                        String.valueOf(totalL),
                                                        String.valueOf(totalR)));
        }
        this.listadoRutasAdapter = new AdaptadorFourItems(FormInformacionRutas.this, arrayListadoRutas);
        this.listadoRutas.setAdapter(listadoRutasAdapter);
        this.listadoRutasAdapter.notifyDataSetChanged();
        registerForContextMenu(this.listadoRutas);
    }

    /**Funciones para el control del menu contextual del listview que muestra las ordenes de trabajo**/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        this.nodo_seleccionado = arrayListadoRutas.get(info.position).getItem1();
        switch(v.getId()){
            case R.id.InfoListRutas:
                menu.setHeaderTitle("Nodo " + this.nodo_seleccionado);
                super.onCreateContextMenu(menu, v, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_lista_rutas, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.NodosMenuFormatoClientes:
                this.new_form = new Intent(this, FormTomarLectura.class);
                this.new_form.putExtra("Nodo",this.nodo_seleccionado);
                startActivity(this.new_form);
                return true;

            case R.id.NodoMenuFormatorRedes:
                this.new_form = new Intent(this, FormRedesPoste.class);
                this.new_form.putExtra("Nodo",this.nodo_seleccionado);
                startActivity(this.new_form);
                return true;

            case R.id.NodoMenuTerminar:
                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }
}
