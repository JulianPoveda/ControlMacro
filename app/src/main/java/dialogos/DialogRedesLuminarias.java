package dialogos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import Adapter.AdaptadorSixItems;
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

    private AdaptadorSixItems           listadoLuminariasAdapter;
    private ArrayList<DetalleSixItems>  arrayListadoLuminarias;

    private ArrayList<ContentValues> _tempTabla = new ArrayList<ContentValues>();
    private ContentValues _tempRegistro 		= new ContentValues();

    private String  nodo;
    private int     item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_luminarias);

        Bundle bundle = getIntent().getExtras();
        this.nodo = bundle.getString("Nodo");
        this.item = bundle.getInt("Item");

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
    }

    private void mostrarLuminariasRegistradas(){
        this._tempTabla = this.FcnRedesPoste.getListaLuminarias(this.item);
        for(int i=0;i<this._tempTabla.size();i++){
            this._tempRegistro = this._tempTabla.get(i);
            this.arrayListadoLuminarias.add(new DetalleSixItems( this._tempRegistro.getAsString("codigo"),
                    this._tempRegistro.getAsString("capacidad"),
                    this._tempRegistro.getAsString("tipo"),
                    this._tempRegistro.getAsString("estado"),
                    this._tempRegistro.getAsString("propietario"),
                    this._tempRegistro.getAsString("tierra")));
        }
        this.listadoLuminariasAdapter = new AdaptadorSixItems(this, this.arrayListadoLuminarias);
        this.listaLuminarias.setAdapter(this.listadoLuminariasAdapter);
        this.listadoLuminariasAdapter.notifyDataSetChanged();
        //registerForContextMenu(this.listaLuminarias);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_redes_luminarias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtoFinalizarRedesLuminarias:
                /*ContentValues tempRegistroLuminarias = new ContentValues();
                tempRegistroLuminarias.clear();
                tempRegistroLuminarias.put("codigoLuminaria",this.codigoLuminaria.getText().toString());
                tempRegistroLuminarias.put("potenciaLuminaria", this.potenciaLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("tipoLuminaria",this.tipoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("estadoLuminaria",this.estadoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("apLuminaria",this.apLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("tpLuminaria",this.ptLuminaria.getSelectedItem().toString());
                registroLuminarias.add(tempRegistroLuminarias);
                finish(true);*/
                break;

            case R.id.BtoAgregarLuminarias:
                this.FcnRedesPoste.crearLuminaria(this.item,
                        this.codigoLuminaria.getText().toString(),
                        this.potenciaLuminaria.getSelectedItem().toString(),
                        this.tipoLuminaria.getSelectedItem().toString(),
                        this.estadoLuminaria.getSelectedItem().toString(),
                        this.apLuminaria.getSelectedItem().toString(),
                        this.ptLuminaria.getSelectedItem().toString());

                /*this.arrayLuminarias.add(this.codigoLuminaria.getText().toString());
                this.arrayLuminarias.add(this.potenciaLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.tipoLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.estadoLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.apLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.ptLuminaria.getSelectedItem().toString());*/

                /*this.adapterLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayLuminarias);
                this.adapterLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.listaLuminarias.setAdapter(adapterLuminarias);
                this.adapterLuminarias.notifyDataSetChanged();*/
                break;
        }
    }
}
