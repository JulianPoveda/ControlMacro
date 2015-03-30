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

import Adapter.AdaptadorFourItems;
import Adapter.AdaptadorSevenItems;
import Adapter.DetalleFourItems;
import Adapter.DetalleSevenItems;
import clases.ClassDataSpinner;
import clases.ClassRedesPoste;
import controlmacro.R;


public class DialogRedesEquipos extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ClassRedesPoste FcnRedesPoste;
    private ClassDataSpinner FcnDataS;

    private Button      btoAgregar;
    private Button      btoFinalizar;
    private EditText    nombreEquipo;
    private EditText    capacidadEquipo;
    private Spinner     unidadesCapacidad;
    private Spinner     tiposEquipos;
    private ListView    listaEquipos;

    private ArrayList<String>    arrayEquipos;
    private ArrayList<String>    arrayUnidad;
    private ArrayAdapter<String> adapterCapacidadEquipos;
    private ArrayAdapter<String> adapterEquipos;

    private ArrayList<ContentValues> registroEquipos = new ArrayList<ContentValues>();
    private AdaptadorFourItems          listadoEquiposAdapter;
    private ArrayList<DetalleFourItems> arrayListadoEquipos;

    private ArrayList<ContentValues> registrosEquipos   = new ArrayList<ContentValues>();
    private ArrayList<ContentValues> _tempTabla         = new ArrayList<ContentValues>();
    private ContentValues _tempRegistro 		        = new ContentValues();

    private String  nodo;
    private int     item;
    private int     id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_equipos);

        Bundle bundle = getIntent().getExtras();
        this.nodo = bundle.getString("Nodo");
        this.item = Integer.parseInt(bundle.getString("Item"));
        this.FcnRedesPoste  = new ClassRedesPoste(this, this.nodo);

        this.arrayListadoEquipos = new ArrayList<>();
        this.arrayListadoEquipos.clear();

        this.btoAgregar         = (Button)findViewById(R.id.RedesEquiposBtnAgregar);
        this.btoFinalizar       = (Button)findViewById(R.id.RedesEquiposBtnFinalizar);
        this.nombreEquipo       = (EditText)findViewById(R.id.EditNombreRedesEquipos);
        this.capacidadEquipo    = (EditText)findViewById(R.id.EditCondRedesEquipos);
        this.tiposEquipos       = (Spinner)findViewById(R.id.SpinnerRedesEquipos);
        this.unidadesCapacidad  = (Spinner)findViewById(R.id.SpCapacidadEquipos);
        this.listaEquipos       = (ListView)findViewById(R.id.RedesEquiposLstEquipos);

        this.FcnDataS   = ClassDataSpinner.getInstance(this);

        this.arrayEquipos     = new ArrayList<String>();
        this.arrayEquipos.clear();
        this.arrayEquipos = this.FcnDataS.getDataSpinnerTipologia("SpinnerRedesEquipos");

        this.arrayUnidad = new ArrayList<String>();
        this.arrayUnidad.clear();

        this.registrosEquipos.clear();
        Bundle extras = this.getIntent().getExtras();

        this.adapterEquipos =   new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEquipos);
        this.adapterEquipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.tiposEquipos.setAdapter(this.adapterEquipos);

        this.adapterCapacidadEquipos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayUnidad);
        this.adapterCapacidadEquipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.unidadesCapacidad.setAdapter(adapterCapacidadEquipos);
        this.adapterCapacidadEquipos.notifyDataSetChanged();

        btoAgregar.setOnClickListener(this);
        btoFinalizar.setOnClickListener(this);
        tiposEquipos.setOnItemSelectedListener(this);
        unidadesCapacidad.setOnItemSelectedListener(this);
        registerForContextMenu(this.listaEquipos);

        this.mostrarEquiposRegistrados();

    }


    /**Funciones para el control del menu contextual del listview que muestra las ordenes de trabajo**/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        this.id = Integer.parseInt(this.arrayListadoEquipos.get(info.position).getItem1());

        switch(v.getId()){
            case R.id.RedesEquiposLstEquipos:
                menu.setHeaderTitle("Nodo "+this.nodo+", item "+this.item+", id "+this.id);
                super.onCreateContextMenu(menu, v, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_equipos, menu);
                break;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.EquiposMenuContextEliminar:
                if(this.FcnRedesPoste.eliminarEquipo(this.item, this.id)){
                    this.mostrarEquiposRegistrados();
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    public void finish(boolean _caso) {
        Intent data = new Intent();
        if(_caso){
            data.putExtra("response", _caso);
            data.putParcelableArrayListExtra("datosEquipos", registrosEquipos);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.SpinnerRedesEquipos:
                this.arrayUnidad = this.FcnDataS.getDataSpinnerSubTipologia("SpinnerRedesEquipos",this.tiposEquipos.getSelectedItem().toString());
                this.adapterCapacidadEquipos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayUnidad);
                this.adapterCapacidadEquipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.unidadesCapacidad.setAdapter(adapterCapacidadEquipos);
                this.adapterCapacidadEquipos.notifyDataSetChanged();

                if(this.tiposEquipos.getSelectedItem().toString().equals("Otro")){
                    nombreEquipo.setEnabled(true);
                }
                else{
                    nombreEquipo.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RedesEquiposBtnAgregar:
                this._tempRegistro.clear();
                if(this.tiposEquipos.getSelectedItem().toString().equals("Otro")){
                    this._tempRegistro.put("tipoEquipo", this.tiposEquipos.getSelectedItem().toString() + ":" + this.nombreEquipo.getText().toString());
                }else{
                    this._tempRegistro.put("tipoEquipo", this.tiposEquipos.getSelectedItem().toString());
                }
                this._tempRegistro.put("capacidadEquipo", this.capacidadEquipo.getText().toString());
                this._tempRegistro.put("unidadesEquipo", this.unidadesCapacidad.getSelectedItem().toString());

                if(this.FcnRedesPoste.crearEquipo(this.nodo,
                        this.item,
                        this._tempRegistro.getAsString("tipoEquipo"),
                        Integer.parseInt(this._tempRegistro.getAsString("capacidadEquipo")),
                        this._tempRegistro.getAsString("unidadesEquipo"))){
                    this.mostrarEquiposRegistrados();
                }



                //registrosEquipos.add(tempRegistroEquipos);
                //finish(true);
                break;

            case R.id.RedesEquiposBtnFinalizar:
                finish(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    private void mostrarEquiposRegistrados(){
        this.arrayListadoEquipos.clear();
        this._tempTabla = this.FcnRedesPoste.getListaEquipos(this.item);
        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            this.arrayListadoEquipos.add(new DetalleFourItems( this._tempRegistro.getAsString("id"),
                    this._tempRegistro.getAsString("nombre"),
                    this._tempRegistro.getAsString("capacidad"),
                    this._tempRegistro.getAsString("unidades")));
        }
        this.listadoEquiposAdapter = new AdaptadorFourItems(this, this.arrayListadoEquipos);
        this.listaEquipos.setAdapter(this.listadoEquiposAdapter);
        this.listadoEquiposAdapter.notifyDataSetChanged();
    }
}

