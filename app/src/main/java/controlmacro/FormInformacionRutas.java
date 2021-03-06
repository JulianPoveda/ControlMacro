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
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.AdaptadorFourItems;
import Adapter.DetalleFourItems;
import async_task.UploadLecturas;
import clases.ClassRedesPoste;
import clases.ClassSession;
import dialogos.DialogTopologico;
import sistema.SQLite;

/**
 * Created by SypelcDesarrollo on 04/02/2015.
 */
public class FormInformacionRutas extends Activity{
    private static int ACT_INFO_TOPOLOGICO         = 1;
    private String FolderAplicacion;
    private String nodo_seleccionado;

    private Intent          new_form;
    private ListView        listadoRutas;
    private SQLite          sqlConsulta;
    private ClassSession    FcnSession;
    private ClassRedesPoste nodo;//para terminar

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
        this.nodo           = new ClassRedesPoste(this, this.nodo_seleccionado);//para terminar

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

            case R.id.NodoMenuInfoTopologico:
                this.new_form = new Intent(this, DialogTopologico.class);
                this.new_form.putExtra("Nodo",this.nodo_seleccionado);
                startActivityForResult(this.new_form, ACT_INFO_TOPOLOGICO);
                return true;

            case R.id.NodoMenuDescargar:
                if(this.sqlConsulta.CountRegistrosWhere("maestro_clientes","nodo='"+this.nodo_seleccionado+"' AND estado='P'")==0){
                    new UploadLecturas(this).execute(this.nodo_seleccionado);
                }
                else{
                    Toast.makeText(this, "No Es posible Descargar Nodo sin Terminar.", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(resultCode == RESULT_OK && requestCode == ACT_INFO_TOPOLOGICO){

            }
        }
        catch (Exception e){
            e.toString();
        }
    }
}
