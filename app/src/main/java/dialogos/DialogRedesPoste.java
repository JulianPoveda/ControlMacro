package dialogos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import controlmacro.R;


public class DialogRedesPoste extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button      _btonAceptar;
    Button      _btonCancelar;
    EditText    gpsLat;
    EditText    gpsLong;
    EditText    compartidoPoste;
    EditText    observacionPoste;
    Spinner     tipoPoste;
    Spinner     estadoPoste;
    Spinner     materialPoste;
    Spinner     alturaPoste;
    Spinner     estructuraPoste;



    private ArrayList<String> arrayAlturaPoste;
    private ArrayList<String> arrayTiposPoste;
    private ArrayList<String> arrayEstadoPoste;
    private ArrayList<String> arrayMaterialPoste;
    private ArrayList<String> arrayEstructuraPoste;

    private ArrayAdapter<String> adapterAltura;
    private ArrayAdapter<String> adapterTipo;
    private ArrayAdapter<String> adapterEstado;
    private ArrayAdapter<String> adapterMaterial;
    private ArrayAdapter<String> adapterEstructura;

    ArrayList<ContentValues> registroPoste  = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_post);

        this._btonAceptar     = (Button)findViewById(R.id.PosteBtonAceptar);
        this._btonCancelar    = (Button)findViewById(R.id.PosteBtonCancelar);
        this.gpsLat           = (EditText)findViewById(R.id.EditLatRedesPoste);
        this.gpsLong          = (EditText)findViewById(R.id.EditLongRedesPoste);
        this.compartidoPoste  = (EditText) findViewById(R.id.EditCompartidoRedesPoste);
        this.observacionPoste = (EditText)findViewById(R.id.EditObsRedesPoste);
        this.tipoPoste        = (Spinner)findViewById(R.id.SpinnerTipoRedesPoste);
        this.estadoPoste      = (Spinner)findViewById(R.id.SpinnerEstadoRedesPoste);
        this.materialPoste    = (Spinner)findViewById(R.id.SpinnerMaterialRedesPoste);
        this.alturaPoste      = (Spinner)findViewById(R.id.SpinnerAlturaRedesPoste);
        this.estructuraPoste  = (Spinner)findViewById(R.id.SpinnerEstructuraRedesPoste);

        this.registroPoste.clear();
        Bundle extras = this.getIntent().getExtras();

        arrayAlturaPoste = new ArrayList<String>();
        arrayAlturaPoste.add("0");
        arrayAlturaPoste.add("6");
        arrayAlturaPoste.add("8");
        arrayAlturaPoste.add("10");
        arrayAlturaPoste.add("12");

        arrayTiposPoste = new ArrayList<String>();
        arrayTiposPoste.add("Caja");
        arrayTiposPoste.add("Poste");
        arrayTiposPoste.add("Cruce Aereo");

        arrayEstadoPoste = new ArrayList<String>();
        arrayEstadoPoste.add("Bueno");
        arrayEstadoPoste.add("Malo");

        arrayMaterialPoste = new ArrayList<String>();
        arrayMaterialPoste.add("Concreto");
        arrayMaterialPoste.add("Madera");
        arrayMaterialPoste.add("Metal");

        arrayEstructuraPoste = new ArrayList<String>();
        arrayEstructuraPoste.add("N/A");// las demas opciones la envia daniel
        arrayEstructuraPoste.add("LA 319");
        arrayEstructuraPoste.add("LA 320");
        arrayEstructuraPoste.add("LA 320-1");
        arrayEstructuraPoste.add("LA 321");
        arrayEstructuraPoste.add("LA 322");
        arrayEstructuraPoste.add("LA 323");
        arrayEstructuraPoste.add("LA 324");
        arrayEstructuraPoste.add("LA 325");
        arrayEstructuraPoste.add("LA 326");
        arrayEstructuraPoste.add("LA 327");
        arrayEstructuraPoste.add("LA 328");
        arrayEstructuraPoste.add("LA 329");
        arrayEstructuraPoste.add("LA 330");
        arrayEstructuraPoste.add("LA 334");
        arrayEstructuraPoste.add("LA 335");
        arrayEstructuraPoste.add("LA 336");
        arrayEstructuraPoste.add("LA 339");
        arrayEstructuraPoste.add("LA 340");
        arrayEstructuraPoste.add("LA 341");
        arrayEstructuraPoste.add("LA 342");
        arrayEstructuraPoste.add("LA 343");
        arrayEstructuraPoste.add("LA 344");
        arrayEstructuraPoste.add("LA 345");
        arrayEstructuraPoste.add("LA 346");
        arrayEstructuraPoste.add("LA 347");
        arrayEstructuraPoste.add("LA 348");
        arrayEstructuraPoste.add("LA 349");
        arrayEstructuraPoste.add("LA 350");
        arrayEstructuraPoste.add("LA 503");
        arrayEstructuraPoste.add("LA 504");
        arrayEstructuraPoste.add("LA 505");
        arrayEstructuraPoste.add("LA 506");

        this.adapterAltura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayAlturaPoste);
        this.adapterAltura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.alturaPoste.setAdapter(adapterAltura);
        this.adapterAltura.notifyDataSetChanged();

        this.adapterTipo  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayTiposPoste);
        this.adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.tipoPoste.setAdapter(adapterTipo);
        this.adapterTipo.notifyDataSetChanged();

        this.adapterEstado  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEstadoPoste);
        this.adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.estadoPoste.setAdapter(adapterEstado);
        this.adapterEstado.notifyDataSetChanged();

        this.adapterMaterial  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayMaterialPoste);
        this.adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.materialPoste.setAdapter(adapterMaterial);
        this.adapterMaterial.notifyDataSetChanged();

        this.adapterEstructura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEstructuraPoste);
        this.adapterEstructura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.estructuraPoste.setAdapter(adapterEstructura);
        this.adapterEstructura.notifyDataSetChanged();

        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            this.gpsLat.setText(bundle.getString("latitud"));
            this.gpsLong.setText(bundle.getString("longitud"));
            this.compartidoPoste.setText(bundle.getString("compartido"));
            this.observacionPoste.setText(bundle.getString("observacion"));

            this.tipoPoste.setSelection(this.adapterTipo.getPosition(bundle.getString("tipo")));
            this.estadoPoste.setSelection(this.adapterEstado.getPosition(bundle.getString("estado")));
            this.materialPoste.setSelection(this.adapterMaterial.getPosition(bundle.getString("material")));
            this.alturaPoste.setSelection(this.adapterAltura.getPosition(bundle.getString("altura")));
            this.estructuraPoste.setSelection(this.adapterEstructura.getPosition(bundle.getString("estructura")));
        }

        _btonAceptar.setOnClickListener(this);
        _btonCancelar.setOnClickListener(this);
        tipoPoste.setOnItemSelectedListener(this);
    }

    public void finish(boolean _caso) {
        Intent data = new Intent();
        if(_caso){
            data.putExtra("response", _caso);
            data.putParcelableArrayListExtra("datosPoste", registroPoste);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_redes_post, menu);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.SpinnerTipoRedesPoste:
                if(tipoPoste.getSelectedItem().toString().equals("Caja")){
                   alturaPoste.setEnabled(false);
                    //asignarle cero
                }else if(tipoPoste.getSelectedItem().toString().equals("Cruce Aereo")){
                        this.alturaPoste.setEnabled(false);
                        this.estadoPoste.setEnabled(false);
                        this.materialPoste.setEnabled(false);
                        this.estructuraPoste.setEnabled(false);
                      }else{
                            this.alturaPoste.setEnabled(true);
                            this.estadoPoste.setEnabled(true);
                            this.materialPoste.setEnabled(true);
                            this.estructuraPoste.setEnabled(true);
                        }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PosteBtonAceptar:
                ContentValues tempRegistroPoste = new ContentValues();
                tempRegistroPoste.clear();
                tempRegistroPoste.put("tipoPoste", this.tipoPoste.getSelectedItem().toString());
                tempRegistroPoste.put("estadoPoste", this.estadoPoste.getSelectedItem().toString());
                tempRegistroPoste.put("materialPoste", this.materialPoste.getSelectedItem().toString());
                tempRegistroPoste.put("alturaPoste", this.alturaPoste.getSelectedItem().toString());
                tempRegistroPoste.put("estructuraPoste", this.estructuraPoste.getSelectedItem().toString());
                tempRegistroPoste.put("gpsLat", this.gpsLat.getText().toString());
                tempRegistroPoste.put("gpsLong", this.gpsLong.getText().toString());
                tempRegistroPoste.put("compartidoPoste", this.compartidoPoste.getText().toString());
                tempRegistroPoste.put("observacionPoste", this.observacionPoste.getText().toString());
                registroPoste.add(tempRegistroPoste);
                finish(true);
                break;

            case R.id.PosteBtonCancelar:
                finish(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
