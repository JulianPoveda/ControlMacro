package dialogos;

import android.app.Activity;
import android.content.ContentValues;
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


public class DialogRedesEquipos extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button   btoAceptar;
    private Button   btoCancelar;
    private EditText nombreEquipo;
    private EditText capacidadEquipo;
    private Spinner  unidadesCapacidad;
    private Spinner  tiposEquipos;

    private ArrayList<String>    arrayEquipos;
    private ArrayList<String>    arrayUnidad;
    private ArrayAdapter<String> adapterCapacidadEquipos;
    private ArrayAdapter<String> adapterEquipos;

    private ArrayList<ContentValues> registrosEquipos = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_equipos);

        this.btoAceptar        = (Button)findViewById(R.id.BtoAcepRedesEquipos);
        this.btoCancelar       = (Button)findViewById(R.id.BtoCancelarRedesEquipos);
        this.nombreEquipo      = (EditText)findViewById(R.id.EditNombreRedesEquipos);
        this.capacidadEquipo   = (EditText)findViewById(R.id.EditCondRedesEquipos);
        this.tiposEquipos      = (Spinner)findViewById(R.id.SpinnerRedesEquipos);
        this.unidadesCapacidad = (Spinner)findViewById(R.id.SpCapacidadEquipos);

        this.arrayEquipos     = new ArrayList<String>();
        this.arrayUnidad = new ArrayList<String>();

        this.arrayEquipos.clear();
        this.arrayEquipos.add("Amplificadores");//Se debe actualizar con los parametros de la BD
        this.arrayEquipos.add("Condensadores");
        this.arrayEquipos.add("Switches");
        this.arrayEquipos.add("Otro");

        this.registrosEquipos.clear();
        Bundle extras = this.getIntent().getExtras();

        this.adapterEquipos =   new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEquipos);
        this.adapterEquipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.tiposEquipos.setAdapter(this.adapterEquipos);

        this.adapterCapacidadEquipos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayUnidad);
        this.adapterCapacidadEquipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.unidadesCapacidad.setAdapter(adapterCapacidadEquipos);


        btoAceptar.setOnClickListener(this);
        btoCancelar.setOnClickListener(this);
        tiposEquipos.setOnItemSelectedListener(this);
        unidadesCapacidad.setOnItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_redes_equipos, menu);
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
            case R.id.SpinnerRedesEquipos:
                if(tiposEquipos.getSelectedItem().toString().equals("Otro")){
                    nombreEquipo.setEnabled(true);
                }else{
                    nombreEquipo.setEnabled(false);
                }
                break;
            case R.id.SpCapacidadEquipos:
                if(unidadesCapacidad.getSelectedItem().toString().equals("Condensadores")){
                    arrayEquipos.clear();
                    arrayEquipos.add("VAR");
                }else{
                    arrayEquipos.clear();
                    arrayEquipos.add("KW");
                    arrayEquipos.add("W");
                }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtoAcepRedesEquipos:
                ContentValues tempRegistroEquipos = new ContentValues();
                tempRegistroEquipos.clear();
                tempRegistroEquipos.put("tipoEquipo", this.tiposEquipos.getSelectedItem().toString());
                tempRegistroEquipos.put("capacidaEquipo", this.capacidadEquipo.getText().toString());
                tempRegistroEquipos.put("nombreEquipo", this.nombreEquipo.getText().toString());
                registrosEquipos.add(tempRegistroEquipos);
                finish(true);
                break;

            case R.id.BtoCancelarRedesEquipos:
                finish(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}

