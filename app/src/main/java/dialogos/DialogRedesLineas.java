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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import controlmacro.R;

public class DialogRedesLineas extends Activity implements View.OnClickListener {

    private Button btonAceptar;
    private Button btonCancelar;
    private Spinner faseA;
    private Spinner faseB;
    private Spinner faseC;
    private Spinner faseAP;
    private Spinner faseN;
    private Spinner conductor;

    private ArrayList<String> arrayFases;
    private ArrayList<String> arrayConductor;
    private ArrayAdapter<String> adapterFases;
    private ArrayAdapter<String> adapterConductor;

    private ArrayList<ContentValues> registrosLineas = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_lineas);

        this.btonAceptar  = (Button)findViewById(R.id.BtoAcepRedesLineas);
        this.btonCancelar = (Button)findViewById(R.id.BtoCancelarRedesLineas);
        this.faseA        = (Spinner)findViewById(R.id.SpinnerFARedesLineas);
        this.faseB        = (Spinner)findViewById(R.id.SpinnerFBRedesLineas);
        this.faseC        = (Spinner)findViewById(R.id.SpinnerFCRedesLineas);
        this.faseAP       = (Spinner)findViewById(R.id.SpinnerFAPRedesLineas);
        this.faseN        = (Spinner)findViewById(R.id.SpinnerFNRedesLineas);
        this.conductor    = (Spinner)findViewById(R.id.SpinnerCondRedesLineas);

        this.arrayFases     =   new ArrayList<String>();
        this.arrayConductor =   new ArrayList<String>();

        this.arrayFases.clear();
        this.arrayFases.add("8");
        this.arrayFases.add("6");
        this.arrayFases.add("4");
        this.arrayFases.add("2");
        this.arrayFases.add("1/0");
        this.arrayFases.add("2/0");
        this.arrayFases.add("3/0");
        this.arrayFases.add("4/0");
        this.arrayFases.add("250MCM");
        this.arrayFases.add("350MCM");
        this.arrayFases.add("450MCM");
        this.arrayFases.add("550KCMIL");

        this.arrayConductor.clear();
        this.arrayConductor.add("A");
        this.arrayConductor.add("D");
        this.arrayConductor.add("T");

        this.registrosLineas.clear();
        Bundle extras = this.getIntent().getExtras();

        this.adapterFases   = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayFases);
        this.faseA.setAdapter(this.adapterFases);
        this.faseB.setAdapter(this.adapterFases);
        this.faseC.setAdapter(this.adapterFases);
        this.faseAP.setAdapter(this.adapterFases);
        this.faseN.setAdapter(this.adapterFases);

        this.adapterConductor = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.arrayConductor);
        this.conductor.setAdapter(this.adapterConductor);

        this.btonCancelar.setOnClickListener(this);
        this.btonCancelar.setOnClickListener(this);
    }

    public void finish(boolean _caso) {
        Intent data = new Intent();
        if(_caso){
            data.putExtra("response", _caso);
            data.putParcelableArrayListExtra("datosLineas", registrosLineas);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_redes_lineas, menu);
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
            case R.id.BtoAcepRedesLineas:
                ContentValues tempRegistroLineas = new ContentValues();
                tempRegistroLineas.clear();
                tempRegistroLineas.put("faseA",this.faseA.getSelectedItem().toString());
                tempRegistroLineas.put("faseB",this.faseB.getSelectedItem().toString());
                tempRegistroLineas.put("faseC",this.faseC.getSelectedItem().toString());
                tempRegistroLineas.put("faseAP",this.faseAP.getSelectedItem().toString());
                tempRegistroLineas.put("faseN",this.faseN.getSelectedItem().toString());
                tempRegistroLineas.put("conductor",this.conductor.getSelectedItem().toString());
                registrosLineas.add(tempRegistroLineas);
                finish(true);
                break;

            case R.id.BtoCancelarRedesEquipos:
                finish(false);
                break;
        }
    }
}
