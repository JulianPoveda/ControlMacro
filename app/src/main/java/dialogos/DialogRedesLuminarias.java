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

import controlmacro.R;

public class DialogRedesLuminarias extends Activity implements View.OnClickListener{

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText codigoLuminaria;
    private Spinner potenciaLuminaria;
    private Spinner  tipoLuminaria;
    private Spinner  estadoLuminaria;
    private Spinner  apLuminaria;
    private Spinner  ptLuminaria;
    private ListView listaLuminarias;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_luminarias);

        this.btnAceptar        = (Button)findViewById(R.id.BtoAcepRedesLuminarias);
        this.btnCancelar       = (Button)findViewById(R.id.BtoCancelarRedesLuminarias);
        this.codigoLuminaria   = (EditText)findViewById(R.id.EditCodigoLuminaria);
        this.potenciaLuminaria = (Spinner)findViewById(R.id.SpinnerPotenciaLuminaria);
        this.tipoLuminaria     = (Spinner)findViewById(R.id.SpinnerTipoLuminaria);
        this.estadoLuminaria   = (Spinner)findViewById(R.id.SpinnerEstadoLuminaria);
        this.apLuminaria       = (Spinner)findViewById(R.id.SpinnerApLuminaria);
        this.ptLuminaria       = (Spinner)findViewById(R.id.SpinnerPTLuminaria);
        this.listaLuminarias   = (ListView)findViewById(R.id.LuminariaListView);

        this.arraytipoLuminarias   = new ArrayList<String>();
        this.arrayestadoLuminarias = new ArrayList<String>();
        this.arrayapLuminarias     = new ArrayList<String>();
        this.arrayptLuminarias     = new ArrayList<String>();
        this.arrayPotencias        = new ArrayList<String>();
        this.arrayLuminarias       = new ArrayList<String>();

        this.registroLuminarias.clear();
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
        this.adapterTpLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arraytipoLuminarias);
        this.adapterTpLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterApLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayapLuminarias);
        this.adapterApLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterPtLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayptLuminarias);
        this.adapterPtLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterPotenciaLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayPotencias);
        this.adapterPotenciaLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayLuminarias);

        this.potenciaLuminaria.setAdapter(this.adapterPotenciaLuminarias);
        this.tipoLuminaria.setAdapter(this.adapterTpLuminarias);
        this.estadoLuminaria.setAdapter(this.adapterEsLuminarias);
        this.apLuminaria.setAdapter(this.adapterApLuminarias);
        this.ptLuminaria.setAdapter(this.adapterPtLuminarias);
        this.listaLuminarias.setAdapter(adapterLuminarias);
        this.adapterLuminarias.notifyDataSetChanged();

        this.btnCancelar.setOnClickListener(this);
        this.btnAceptar.setOnClickListener(this);

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
            case R.id.BtoAcepRedesLuminarias:
                ContentValues tempRegistroLuminarias = new ContentValues();
                tempRegistroLuminarias.clear();
                tempRegistroLuminarias.put("codigoLuminaria",this.codigoLuminaria.getText().toString());
                tempRegistroLuminarias.put("potenciaLuminaria", this.potenciaLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("tipoLuminaria",this.tipoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("estadoLuminaria",this.estadoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("apLuminaria",this.apLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("tpLuminaria",this.ptLuminaria.getSelectedItem().toString());
                registroLuminarias.add(tempRegistroLuminarias);
                finish(true);
                break;

            case R.id.BtoAgregarLuminarias:
                this.arrayLuminarias.add(this.codigoLuminaria.getText().toString());
                this.arrayLuminarias.add(this.potenciaLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.tipoLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.estadoLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.apLuminaria.getSelectedItem().toString());
                this.arrayLuminarias.add(this.ptLuminaria.getSelectedItem().toString());

                this.listaLuminarias.setAdapter(adapterLuminarias);
                this.adapterLuminarias.notifyDataSetChanged();

            case R.id.BtoCancelarRedesLuminarias:
                finish(false);
                break;
        }
    }
}
