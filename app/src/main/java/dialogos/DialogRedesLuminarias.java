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
import android.widget.Spinner;

import java.util.ArrayList;

import controlmacro.R;

public class DialogRedesLuminarias extends Activity implements View.OnClickListener{

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText codigoLuminaria;
    private EditText potenciaUno;
    private EditText potenciaDos;
    private EditText potenciaTres;
    private EditText potenciaCuatro;
    private Spinner  tipoLuminaria;
    private Spinner  estadoLuminaria;
    private Spinner  apLuminaria;
    private Spinner  ptLuminaria;

    private ArrayList<String> arraytipoLuminarias;
    private ArrayList<String> arrayestadoLuminarias;
    private ArrayList<String> arrayapLuminarias;
    private ArrayList<String> arrayptLuminarias;
    private ArrayAdapter<String> adapterEsLuminarias;
    private ArrayAdapter<String> adapterTpLuminarias;
    private ArrayAdapter<String> adapterApLuminarias;
    private ArrayAdapter<String> adapterPtLuminarias;

    private ArrayList<ContentValues> registroLuminarias = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_luminarias);

        this.btnAceptar      = (Button)findViewById(R.id.BtoAcepRedesLuminarias);
        this.btnCancelar     = (Button)findViewById(R.id.BtoCancelarRedesLuminarias);
        this.codigoLuminaria = (EditText)findViewById(R.id.EditCodigoLuminaria);
        this.potenciaUno     = (EditText)findViewById(R.id.Edit70Luminaria);
        this.potenciaDos     = (EditText)findViewById(R.id.Edit125Luminaria);
        this.potenciaTres    = (EditText)findViewById(R.id.Edit150Luminaria);
        this.potenciaCuatro  = (EditText)findViewById(R.id.Edit250Luminaria);
        this.tipoLuminaria   = (Spinner)findViewById(R.id.SpinnerTipoLuminaria);
        this.estadoLuminaria = (Spinner)findViewById(R.id.SpinnerEstadoLuminaria);
        this.apLuminaria     = (Spinner)findViewById(R.id.SpinnerApLuminaria);
        this.ptLuminaria     = (Spinner)findViewById(R.id.SpinnerPTLuminaria);

        this.arraytipoLuminarias   = new ArrayList<String>();
        this.arrayestadoLuminarias = new ArrayList<String>();
        this.arrayapLuminarias     = new ArrayList<String>();
        this.arrayptLuminarias     = new ArrayList<String>();

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

        this.adapterEsLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayestadoLuminarias);
        this.adapterEsLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterTpLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arraytipoLuminarias);
        this.adapterTpLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterApLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayapLuminarias);
        this.adapterApLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.adapterPtLuminarias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.arrayptLuminarias);
        this.adapterPtLuminarias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.tipoLuminaria.setAdapter(this.adapterTpLuminarias);
        this.estadoLuminaria.setAdapter(this.adapterEsLuminarias);
        this.apLuminaria.setAdapter(this.adapterApLuminarias);
        this.ptLuminaria.setAdapter(this.adapterPtLuminarias);

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
                tempRegistroLuminarias.put("potenciaUno",this.potenciaUno.getText().toString());
                tempRegistroLuminarias.put("potenciaDos",this.potenciaDos.getText().toString());
                tempRegistroLuminarias.put("potenciaTres",this.potenciaTres.getText().toString());
                tempRegistroLuminarias.put("potenciaCuatro",this.potenciaCuatro.getText().toString());
                tempRegistroLuminarias.put("tipoLuminaria",this.tipoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("estadoLuminaria",this.estadoLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("apLuminaria",this.apLuminaria.getSelectedItem().toString());
                tempRegistroLuminarias.put("tpLuminaria",this.ptLuminaria.getSelectedItem().toString());
                registroLuminarias.add(tempRegistroLuminarias);
                finish(true);
                break;

            case R.id.BtoCancelarRedesLuminarias:
                finish(false);
                break;
        }
    }
}
