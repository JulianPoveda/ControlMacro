package dialogos;

import android.app.Activity;
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


public class DialogRedesEquipos extends Activity implements View.OnClickListener {

    private Button   btoAceptar;
    private Button   btoCancelar;
    private EditText nombreEquipo;
    private EditText capacidadEquipo;
    private Spinner  tiposEquipos;

    private ArrayList<String>    arrayEquipos;
    private ArrayAdapter<String> adapterEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redes_equipos);

        this.btoAceptar      = (Button)findViewById(R.id.BtoAcepRedesEquipos);
        this.btoCancelar     = (Button)findViewById(R.id.BtoCancelarRedesEquipos);
        this.nombreEquipo    = (EditText)findViewById(R.id.EditNombreRedesEquipos);
        this.capacidadEquipo = (EditText)findViewById(R.id.EditCondRedesEquipos);
        this.tiposEquipos    = (Spinner)findViewById(R.id.SpinnerRedesEquipos);

        this.arrayEquipos    = new ArrayList<String>();

        this.arrayEquipos.clear();
        this.arrayEquipos.add("Amplificadores");//Se debe actualizar con los parametros de la BD
        this.arrayEquipos.add("Condensadores");
        this.arrayEquipos.add("Switches");
        this.arrayEquipos.add("Otro");

        this.adapterEquipos =   new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.arrayEquipos);
        this.tiposEquipos.setAdapter(this.adapterEquipos);

        btoAceptar.setOnClickListener(this);
        btoCancelar.setOnClickListener(this);
    }

    public void finish(boolean _caso) {
        Intent data = new Intent();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtoAcepRedesEquipos:
                finish(true);
                break;

            case R.id.BtoCancelarRedesEquipos:
                finish(false);
                break;
        }
    }
}
