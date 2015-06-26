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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import clases.ClassDataSpinner;
import controlmacro.R;


public class DialogRedesPoste extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button      _btonAceptar;
    Button      _btonCancelar;
    Button      _btonMas;
    Button      _btoMenos;
    EditText    gpsLatGrado;
    EditText    gpsLatMinutos;
    EditText    gpsLatSegundos;
    EditText    gpsLongGrado;
    EditText    gpsLongMinutos;
    EditText    gpsLongSegundos;
    EditText    compartidoPoste;
    EditText    observacionPoste;
    EditText    cntEstGuardar;
    EditText    newNodo;
    Spinner     tipoPoste;
    Spinner     estadoPoste;
    Spinner     materialPoste;
    Spinner     alturaPoste;
    Spinner     estructuraPoste;
    Spinner     cntEstructura;
    RadioButton nuevoNodo;

    String      estructuras;

    private ClassDataSpinner FcnDataS;

    private ArrayList<String> arrayAlturaPoste;
    private ArrayList<String> arrayTiposPoste;
    private ArrayList<String> arrayEstadoPoste;
    private ArrayList<String> arrayMaterialPoste;
    private ArrayList<String> arrayEstructuraPoste;
    private ArrayList<String> arrayCantEstructura;

    private ArrayAdapter<String> adapterAltura;
    private ArrayAdapter<String> adapterTipo;
    private ArrayAdapter<String> adapterEstado;
    private ArrayAdapter<String> adapterMaterial;
    private ArrayAdapter<String> adapterEstructura;
    private ArrayAdapter<String> adapterCantidad;

    ArrayList<ContentValues> registroPoste  = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_post);

        this._btonAceptar     = (Button)findViewById(R.id.PosteBtonAceptar);
        this._btonCancelar    = (Button)findViewById(R.id.PosteBtonCancelar);
        this._btonMas         = (Button)findViewById(R.id.PosteBtonMas);
        this._btoMenos        = (Button)findViewById(R.id.PosteBtoMenos);
        this.gpsLatGrado      = (EditText)findViewById(R.id.EditLatGrado);
        this.gpsLatMinutos    = (EditText)findViewById(R.id.EditLatMinutos);
        this.gpsLatSegundos   = (EditText)findViewById(R.id.EditLatSegundos);
        this.gpsLongGrado     = (EditText)findViewById(R.id.EditLongGrado);
        this.gpsLongMinutos   = (EditText)findViewById(R.id.EditLongMinuto);
        this.gpsLongSegundos  = (EditText)findViewById(R.id.EditLongSegundos);
        this.compartidoPoste  = (EditText) findViewById(R.id.EditCompartidoRedesPoste);
        this.observacionPoste = (EditText)findViewById(R.id.EditObsRedesPoste);
        this.newNodo          = (EditText)findViewById(R.id.EditNuevoNodo);
        this.tipoPoste        = (Spinner)findViewById(R.id.SpinnerTipoRedesPoste);
        this.estadoPoste      = (Spinner)findViewById(R.id.SpinnerEstadoRedesPoste);
        this.materialPoste    = (Spinner)findViewById(R.id.SpinnerMaterialRedesPoste);
        this.alturaPoste      = (Spinner)findViewById(R.id.SpinnerAlturaRedesPoste);
        this.estructuraPoste  = (Spinner)findViewById(R.id.SpinnerEstructuraRedesPoste);
        this.cntEstructura    = (Spinner)findViewById(R.id.SpinnerCantEsPoste);
        this.cntEstGuardar    = (EditText)findViewById(R.id.EditEstructuraPoste);
        this.nuevoNodo        = (RadioButton)findViewById(R.id.BotonNodonuevo);
        this.cntEstGuardar.setEnabled(false);
        this.newNodo.setEnabled(false);
        this.estructuras ="";

        this.registroPoste.clear();
        Bundle extras = this.getIntent().getExtras();

        this.FcnDataS = ClassDataSpinner.getInstance(this);

        this.arrayAlturaPoste = new ArrayList<String>();
        this.arrayAlturaPoste.clear();

        this.arrayTiposPoste = new ArrayList<String>();
        this.arrayTiposPoste.clear();
        this.arrayTiposPoste = this.FcnDataS.getDataSpinnerTipologia("SpinnerTipoRedesPoste");

        this.arrayEstadoPoste = new ArrayList<String>();
        this.arrayEstadoPoste.clear();
        this.arrayEstadoPoste = this.FcnDataS.getDataSpinnerTipologia("SpinnerEstadoRedesPoste");

        this.arrayMaterialPoste = new ArrayList<String>();
        this.arrayMaterialPoste.clear();
        this.arrayMaterialPoste = this.FcnDataS.getDataSpinnerTipologia("SpinnerMaterialRedesPoste");

        this.arrayCantEstructura = new ArrayList<String>();
        this.arrayCantEstructura.clear();
        this.arrayCantEstructura = this.FcnDataS.getDataSpinnerTipologia("SpinnerCantEsPoste");

        this.arrayEstructuraPoste = new ArrayList<String>();
        this.arrayEstructuraPoste.clear();

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

        this.adapterCantidad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayCantEstructura);
        this.adapterCantidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.cntEstructura.setAdapter(adapterCantidad);
        this.adapterCantidad.notifyDataSetChanged();

        //this.arrayAlturaPoste = this.FcnDataS.getDataSpinnerSubTipologia("SpinnerAlturaRedesPoste",tipoPoste.getSelectedItem().toString());
        this.adapterAltura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.FcnDataS.getDataSpinnerTipologia("SpinnerAlturaRedesPoste"));
        this.adapterAltura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.alturaPoste.setAdapter(adapterAltura);
        this.adapterAltura.notifyDataSetChanged();

        //this.arrayEstructuraPoste = this.FcnDataS.getDataSpinnerSubTipologia("SpinnerEstructuraRedesPoste",tipoPoste.getSelectedItem().toString());
        this.adapterEstructura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.FcnDataS.getDataSpinnerTipologia("SpinnerEstructuraRedesPoste"));
        this.adapterEstructura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.estructuraPoste.setAdapter(adapterEstructura);
        this.adapterEstructura.notifyDataSetChanged();

        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            String delimitadores= "[ °\'\"\\[\\]]+";
            String[] latitudSeparadas = bundle.getString("latitud").split(delimitadores);
            String[] longitudSeparadas = bundle.getString("longitud").split(delimitadores);
            this.gpsLatGrado.setText(latitudSeparadas[0]);
            this.gpsLatMinutos.setText(latitudSeparadas[1]);
            this.gpsLatSegundos.setText(latitudSeparadas[2]);
            this.gpsLongGrado.setText(longitudSeparadas[0]);
            this.gpsLongMinutos.setText(longitudSeparadas[1]);
            this.gpsLongSegundos.setText(longitudSeparadas[2]);
            this.compartidoPoste.setText(bundle.getString("compartido"));
            this.observacionPoste.setText(bundle.getString("observacion"));
            this.cntEstGuardar.setText(bundle.getString("estructura"));
            this.newNodo.setText(bundle.getString("new_nodo"));

            this.tipoPoste.setSelection(this.adapterTipo.getPosition(bundle.getString("tipo")));
            this.estadoPoste.setSelection(this.adapterEstado.getPosition(bundle.getString("estado")));
            this.materialPoste.setSelection(this.adapterMaterial.getPosition(bundle.getString("material")));
            this.alturaPoste.setSelection(this.adapterAltura.getPosition(bundle.getString("altura")));
            this.estructuraPoste.setSelection(this.adapterEstructura.getPosition(bundle.getString("estructura")));
        }

        _btonAceptar.setOnClickListener(this);
        _btonCancelar.setOnClickListener(this);
        _btoMenos.setOnClickListener(this);
        _btonMas.setOnClickListener(this);
        tipoPoste.setOnItemSelectedListener(this);
        alturaPoste.setOnItemSelectedListener(this);
        estructuraPoste.setOnItemSelectedListener(this);
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
                this.arrayAlturaPoste = this.FcnDataS.getDataSpinnerSubTipologia("SpinnerAlturaRedesPoste",tipoPoste.getSelectedItem().toString());
                this.adapterAltura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayAlturaPoste);
                this.adapterAltura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.alturaPoste.setAdapter(adapterAltura);
                this.adapterAltura.notifyDataSetChanged();


                if(tipoPoste.getSelectedItem().toString().equals("Cruce Aereo")||tipoPoste.getSelectedItem().toString().equals("LuminariaPedestal")){
                    this._btoMenos.setEnabled(false);
                    this._btonMas.setEnabled(false);
                    this.cntEstructura.setEnabled(false);
                    this.estructuraPoste.setEnabled(false);
                    this.alturaPoste.setEnabled(false);
                    this.materialPoste.setEnabled(false);
                    this.estadoPoste.setEnabled(false);
                    this.compartidoPoste.setEnabled(false);
                    this.newNodo.setEnabled(false);
                    this.nuevoNodo.setEnabled(false);
                    this.cntEstGuardar.setText("0");

                    /*Cambiar el valor del estado para cruce aereo*/
                    this.arrayEstadoPoste.clear();
                    this.arrayEstadoPoste.add("0");
                    this.adapterEstado  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEstadoPoste);
                    this.adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.estadoPoste.setAdapter(adapterEstado);
                    this.adapterEstado.notifyDataSetChanged();

                    this.arrayMaterialPoste.clear();
                    this.arrayMaterialPoste.add("0");
                    this.adapterMaterial  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayMaterialPoste);
                    this.adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.materialPoste.setAdapter(adapterMaterial);
                    this.adapterMaterial.notifyDataSetChanged();


                }else{
                    this._btoMenos.setEnabled(true);
                    this._btonMas.setEnabled(true);
                    this.cntEstructura.setEnabled(true);
                    this.estructuraPoste.setEnabled(true);
                    this.alturaPoste.setEnabled(true);
                    this.materialPoste.setEnabled(true);
                    this.estadoPoste.setEnabled(true);
                    this.compartidoPoste.setEnabled(true);
                    this.newNodo.setEnabled(true);
                    this.nuevoNodo.setEnabled(true);

                    this.arrayEstructuraPoste = this.FcnDataS.getDataSpinnerSubTipologia("SpinnerEstructuraRedesPoste",tipoPoste.getSelectedItem().toString());
                    this.adapterEstructura  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEstructuraPoste);
                    this.adapterEstructura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.estructuraPoste.setAdapter(adapterEstructura);
                    this.adapterEstructura.notifyDataSetChanged();

                    this.arrayEstadoPoste.clear();
                    this.arrayEstadoPoste = this.FcnDataS.getDataSpinnerTipologia("SpinnerEstadoRedesPoste");
                    this.adapterEstado  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEstadoPoste);
                    this.adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.estadoPoste.setAdapter(adapterEstado);
                    this.adapterEstado.notifyDataSetChanged();

                    this.arrayMaterialPoste.clear();
                    this.arrayMaterialPoste = this.FcnDataS.getDataSpinnerTipologia("SpinnerMaterialRedesPoste");
                    this.adapterMaterial  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayMaterialPoste);
                    this.adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.materialPoste.setAdapter(adapterMaterial);
                    this.adapterMaterial.notifyDataSetChanged();

                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PosteBtonAceptar:
                if(!this.gpsLatGrado.getText().toString().isEmpty()&&!this.gpsLongGrado.getText().toString().isEmpty()&&!this.gpsLatMinutos.getText().toString().isEmpty()&&!this.gpsLatSegundos.getText().toString().isEmpty()&&!this.gpsLongMinutos.getText().toString().isEmpty()&&!this.gpsLongSegundos.getText().toString().isEmpty()){
                    if(!this.cntEstGuardar.getText().toString().isEmpty()){
                        ContentValues tempRegistroPoste = new ContentValues();
                        tempRegistroPoste.clear();
                        tempRegistroPoste.put("tipoPoste", this.tipoPoste.getSelectedItem().toString());
                        tempRegistroPoste.put("estadoPoste", this.estadoPoste.getSelectedItem().toString());
                        tempRegistroPoste.put("materialPoste", this.materialPoste.getSelectedItem().toString());
                        tempRegistroPoste.put("alturaPoste", this.alturaPoste.getSelectedItem().toString());
                        tempRegistroPoste.put("estructuraPoste", this.cntEstGuardar.getText().toString());
                        tempRegistroPoste.put("gpsLat", this.gpsLatGrado.getText().toString()+"°"+this.gpsLatMinutos.getText().toString()+"'"+this.gpsLatSegundos.getText().toString()+"\"");
                        tempRegistroPoste.put("gpsLong", this.gpsLongGrado.getText().toString()+"°"+this.gpsLongMinutos.getText().toString()+"'"+this.gpsLongSegundos.getText().toString()+"\"");
                        tempRegistroPoste.put("compartidoPoste", this.compartidoPoste.getText().toString());
                        tempRegistroPoste.put("observacionPoste", this.observacionPoste.getText().toString());
                        tempRegistroPoste.put("new_nodo",this.newNodo.getText().toString());
                        registroPoste.add(tempRegistroPoste);
                        finish(true);
                    }else{
                        Toast.makeText(this, "Debe Registrar Estructura", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Debe Registrar Pocision GPS", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.PosteBtonCancelar:
                finish(false);
                break;

            case R.id.PosteBtonMas:
                this.estructuraPoste.getSelectedItem().toString();
                this.cntEstructura.getSelectedItem().toString();
                this.estructuras = this.estructuras + this.estructuraPoste.getSelectedItem().toString() +"("+ this.cntEstructura.getSelectedItem().toString()+")"+"-";
                this.cntEstGuardar.setText(this.estructuras);
                break;

            case R.id.PosteBtoMenos:
                String cadena=this.estructuras.replace(this.estructuraPoste.getSelectedItem().toString() +"("+ this.cntEstructura.getSelectedItem().toString()+")"+"-","");
                this.cntEstGuardar.setText(cadena);
                this.estructuras = cadena;
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.BotonNodonuevo:
                if (checked)
                    this.newNodo.setEnabled(true);
                    break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
