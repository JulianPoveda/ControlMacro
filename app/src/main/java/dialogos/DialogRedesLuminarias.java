package dialogos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import Adapter.AdaptadorSevenItems;
import Adapter.AdaptadorSixItems;
import Adapter.DetalleSevenItems;
import Adapter.DetalleSixItems;
import clases.ClassRedesPoste;
import controlmacro.FormInicioSession;
import controlmacro.R;
import sistema.SQLite;

public class DialogRedesLuminarias extends Activity implements View.OnClickListener{

    private ClassRedesPoste FcnRedesPoste;

    private Button      btnAgregar;
    private Button      btnFinalizar;
    private EditText    codigoLuminaria;
    private Spinner     potenciaLuminaria;
    private Spinner     tipoLuminaria;
    private Spinner     estadoLuminaria;
    private Spinner     apLuminaria;
    private Spinner     ptLuminaria;
    private ListView    listaLuminarias;

    private ArrayList<String> arraytipoLuminarias;
    private ArrayList<String> arrayestadoLuminarias;
    private ArrayList<String> arrayapLuminarias;
    private ArrayList<String> arrayptLuminarias;
    private ArrayList<String> arrayPotencias;
    private ArrayList<String> arrayLuminarias;
    private ArrayAdapter<String> adapterEsLuminarias;
    private ArrayAdapter<String> adapterTpLuminarias;
    private ArrayAdapter<String> adapterApLuminarias;
    private ArrayAdapter<String> adapterPtLuminarias;
    private ArrayAdapter<String> adapterPotenciaLuminarias;
    private ArrayAdapter<String> adapterLuminarias;

    private ArrayList<ContentValues> registroLuminarias = new ArrayList<ContentValues>();

    private AdaptadorSevenItems           listadoLuminariasAdapter;
    private ArrayList<DetalleSevenItems>  arrayListadoLuminarias;

    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();
    private ContentValues _tempRegistro 		= new ContentValues();

    private String  nodo;
    private int     item;
    private int     id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_luminarias);

        Bundle bundle = getIntent().getExtras();
        this.nodo = bundle.getString("Nodo");
        this.item = Integer.parseInt(bundle.getString("Item"));

        this.FcnRedesPoste  = new ClassRedesPoste(this, this.nodo);

        this.arrayListadoLuminarias  = new ArrayList<>();
        this.arrayListadoLuminarias.clear();

        this.btnAgregar         = (Button) findViewById(R.id.BtoAgregarLuminarias);
        this.btnFinalizar       = (Button)findViewById(R.id.BtoFinalizarRedesLuminarias);
        this.codigoLuminaria    = (EditText)findViewById(R.id.EditCodigoLuminaria);
        this.potenciaLuminaria  = (Spinner)findViewById(R.id.SpinnerPotenciaLuminaria);
        this.tipoLuminaria      = (Spinner)findViewById(R.id.SpinnerTipoLuminaria);
        this.estadoLuminaria    = (Spinner)findViewById(R.id.SpinnerEstadoLuminaria);
        this.apLuminaria        = (Spinner)findViewById(R.id.SpinnerApLuminaria);
        this.ptLuminaria        = (Spinner)findViewById(R.id.SpinnerPTLuminaria);
        this.listaLuminarias    = (ListView)findViewById(R.id.LuminariaListView);

        this.arraytipoLuminarias   = new ArrayList<String>();
        this.arrayestadoLuminarias = new ArrayList<String>();
        this.arrayapLuminarias     = new ArrayList<String>();
        this.arrayptLuminarias     = new ArrayList<String>();
        this.arrayPotencias        = new ArrayList<String>();
        this.arrayLuminarias       = new ArrayList<String>();

        this.registroLuminarias.clear();
        this.arrayLuminarias.clear();
        Bundle extras = this.getIntent().getExtras();

        //Se deben ingresar de la base de datos.
        this.arraytipoLuminarias.clear();
        this.arraytipoLuminarias.add("Na");
        this.arraytipoLuminarias.add("Hg");
        this.arraytipoLuminarias.add("MH");
        this.arraytipoLuminarias.add("I");

        this.arrayestadoLuminarias.clear();
        this.arrayestadoLuminarias.add("B");
        this.arrayestadoLuminarias.add("M");

        this.arrayapLuminarias.clear();
        this.arrayapLuminarias.add("Privado");
        this.arrayapLuminarias.add("Publico");

        this.arrayptLuminarias.clear();
        this.arrayptLuminarias.add("S");
        this.arrayptLuminarias.add("N");

        this.arrayPotencias.clear();
        this.arrayPotencias.add("70W");
        this.arrayPotencias.add("125W");
        this.arrayPotencias.add("150W");
        this.arrayPotencias.add("250W");


        this.adapterEsLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayestadoLuminarias);
        this.adapterEsLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.estadoLuminaria.setAdapter(this.adapterEsLuminarias);

        this.adapterTpLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arraytipoLuminarias);
        this.adapterTpLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.tipoLuminaria.setAdapter(this.adapterTpLuminarias);

        this.adapterApLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayapLuminarias);
        this.adapterApLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.apLuminaria.setAdapter(this.adapterApLuminarias);

        this.adapterPtLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayptLuminarias);
        this.adapterPtLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ptLuminaria.setAdapter(this.adapterPtLuminarias);

        this.adapterPotenciaLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayPotencias);
        this.adapterPotenciaLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.potenciaLuminaria.setAdapter(this.adapterPotenciaLuminarias);

        this.adapterLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayLuminarias);
        this.adapterLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.listaLuminarias.setAdapter(adapterLuminarias);
        this.adapterLuminarias.notifyDataSetChanged();

        this.btnAgregar.setOnClickListener(this);
        this.btnFinalizar.setOnClickListener(this);
        this.mostrarLuminariasRegistradas();

        registerForContextMenu(this.listaLuminarias);
    }


    /**Funciones para el control del menu contextual del listview que muestra las ordenes de trabajo**/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        this.id = Integer.parseInt(this.arrayListadoLuminarias.get(info.position).getItem1());

        switch(v.getId()){
            case R.id.LuminariaListView:
                menu.setHeaderTitle("Nodo "+this.nodo+", item "+this.item+", id "+this.id);
                super.onCreateContextMenu(menu, v, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_luminarias, menu);
                break;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.LuminariasMenuContextEliminar:
                if(this.FcnRedesPoste.eliminarLuminaria(this.item, this.id)){
                    this.mostrarLuminariasRegistradas();
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }



    private void mostrarLuminariasRegistradas(){
        this.arrayListadoLuminarias.clear();
        this._tempTabla = this.FcnRedesPoste.getListaLuminarias(this.item);
        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            this.arrayListadoLuminarias.add(new DetalleSevenItems( this._tempRegistro.getAsString("id"),
                    this._tempRegistro.getAsString("codigo"),
                    this._tempRegistro.getAsString("capacidad"),
                    this._tempRegistro.getAsString("tipo"),
                    this._tempRegistro.getAsString("estado"),
                    this._tempRegistro.getAsString("propietario"),
                    this._tempRegistro.getAsString("tierra")));
        }
        this.listadoLuminariasAdapter = new AdaptadorSevenItems(this, this.arrayListadoLuminarias);
        this.listaLuminarias.setAdapter(this.listadoLuminariasAdapter);
        this.listadoLuminariasAdapter.notifyDataSetChanged();
    }


    public void finish(boolean _caso) {
        Intent data = new Intent();
        if(_caso){
            data.putExtra("response", _caso);
            data.putParcelableArrayListExtra("datosLuminarias", registroLuminarias);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtoFinalizarRedesLuminarias:
                finish(true);
                break;

            case R.id.BtoAgregarLuminarias:
                if(this.FcnRedesPoste.crearLuminaria(this.item,
                        this.codigoLuminaria.getText().toString(),
                        this.potenciaLuminaria.getSelectedItem().toString(),
                        this.tipoLuminaria.getSelectedItem().toString(),
                        this.estadoLuminaria.getSelectedItem().toString(),
                        this.apLuminaria.getSelectedItem().toString(),
                        this.ptLuminaria.getSelectedItem().toString())){
                    this.mostrarLuminariasRegistradas();
                    this.codigoLuminaria.setText("");
                }
                break;
        }
    }
}
