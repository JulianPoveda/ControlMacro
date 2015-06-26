package dialogos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import clases.ClassDataSpinner;
import clases.ClassRedesPoste;
import controlmacro.R;


public class DialogTopologico extends Activity implements View.OnClickListener {
    private ClassRedesPoste FcnRedesPoste;
    private ClassDataSpinner FcnDataS;

    Spinner  posteInicial;
    Spinner  posteFinal;
    Spinner  conexionPoste;
    CheckBox trafo;
    Button   aceptar;
    Button   finalizar;

    private ArrayList<String> conexionesPostes;
    private ArrayList<String> postesInicial;
    private ArrayAdapter<String> adapterConexiones;
    private ArrayAdapter<String> adapterInicial;
    private ArrayAdapter<String> adapterFinal;
    private String  nodo;
    private boolean checTrafo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info_topologico);

        Bundle bundle = getIntent().getExtras();
        this.nodo = bundle.getString("Nodo");

        this.posteInicial   = (Spinner)findViewById(R.id.SpinnerPosteInicial);
        this.posteFinal     = (Spinner)findViewById(R.id.SpinnerPosteFinal);
        this.conexionPoste  = (Spinner)findViewById(R.id.SpinnerPosteConexion);
        this.trafo          = (CheckBox)findViewById(R.id.CheckboxTrafo);
        this.aceptar        = (Button)findViewById(R.id.BtoAgregarConexionTopologico);
        this.finalizar      = (Button)findViewById(R.id.BtoFinalizarTopologico);

        this.FcnDataS = ClassDataSpinner.getInstance(this);
        this.FcnRedesPoste  = new ClassRedesPoste(this, this.nodo);

        this.conexionesPostes = new ArrayList<String>();
        this.conexionesPostes.clear();
        this.conexionesPostes = this.FcnDataS.getDataSpinnerTipologia("SpinnerConexionPostes");

        this.postesInicial = new ArrayList<String>();
        this.postesInicial.clear();
        this.postesInicial = this.FcnDataS.getDataSpinnerPostes(this.nodo);

        this.adapterInicial = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.postesInicial);
        this.adapterInicial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.posteInicial.setAdapter(adapterInicial);
        this.adapterInicial.notifyDataSetChanged();

        this.adapterFinal = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.postesInicial);
        this.adapterFinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.posteFinal.setAdapter(adapterFinal);
        this.adapterFinal.notifyDataSetChanged();

        this.adapterConexiones = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,this.conexionesPostes);
        this.adapterConexiones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.conexionPoste.setAdapter(adapterConexiones);
        this.adapterConexiones.notifyDataSetChanged();

        this.aceptar.setOnClickListener(this);
        this.finalizar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_info_topologico, menu);
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
        boolean isChecked = this.trafo.isChecked();

        if (isChecked) {
            this.checTrafo = true;
        }
        else {
            this.checTrafo = false;
        }
        switch (v.getId()) {
            case R.id.BtoAgregarConexionTopologico:
             if(this.FcnRedesPoste.registrarTopologico(this.nodo,this.posteInicial.getSelectedItem().toString(),
                                                        this.posteFinal.getSelectedItem().toString(),
                                                        this.conexionPoste.getSelectedItem().toString(),
                                                        this.checTrafo)){
                 Toast.makeText(this, "Registro Ingresado Correctamente", Toast.LENGTH_SHORT).show();
            }
            else{
                 Toast.makeText(this, "No se pudo registrar la relacion", Toast.LENGTH_SHORT).show();
            }

            break;

            case R.id.BtoFinalizarTopologico:
                finish();
            break;
        }
    }
}
