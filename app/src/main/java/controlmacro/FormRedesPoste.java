package controlmacro;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.AdaptadorRedesPoste;
import Adapter.DetalleFourItems;
import Adapter.DetalleRedesPoste;


public class FormRedesPoste extends ActionBarActivity {
    private ListView        _lstListadoPostes;

    private AdaptadorRedesPoste listadoPostesAdapter;
    private ArrayList<DetalleRedesPoste>    arrayListadoPoste;

    private ArrayList<String> alturasPoste;
    private ArrayList<String> tiposPoste;
    private ArrayList<String> estadoPoste;
    private ArrayList<String> materialPoste;
    private ArrayList<String> estructuraPoste;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes);

        this._lstListadoPostes  = (ListView) findViewById(R.id.RedesLstPostes);

        alturasPoste = new ArrayList<String>();
        alturasPoste.add("0");
        alturasPoste.add("6");
        alturasPoste.add("8");
        alturasPoste.add("10");
        alturasPoste.add("12");

        tiposPoste = new ArrayList<String>();
        tiposPoste.add("Caja");
        tiposPoste.add("Poste");

        estadoPoste = new ArrayList<String>();
        estadoPoste.add("Bueno");
        estadoPoste.add("Malo");

        materialPoste = new ArrayList<String>();
        materialPoste.add("Concreto");
        materialPoste.add("Madera");
        materialPoste.add("Metal");

        estructuraPoste = new ArrayList<String>();
        estructuraPoste.add("Concreto");
        estructuraPoste.add("Madera");
        estructuraPoste.add("Metal");


        this.arrayListadoPoste = new ArrayList<DetalleRedesPoste>();
        this.arrayListadoPoste.clear();

        this.arrayListadoPoste.add(new DetalleRedesPoste("1","0.1","0.2","Poste","","Bueno","Madera","12","","Sin Observaciones"));
        this.arrayListadoPoste.add(new DetalleRedesPoste("2","1.1","2.4","Caja","","Bueno","Metal","0","","Con Observaciones"));

        this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste)
                .setAltura(this.alturasPoste).setTipo(this.tiposPoste).setEstado(this.estadoPoste).setMaterial(this.materialPoste).setEstructura(this.estructuraPoste).build();

        this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
        this.listadoPostesAdapter.notifyDataSetChanged();


        /*arrayListadoRutas.add(new DetalleFourItems( this._tempRegistro.getAsString("nodo"),
                String.valueOf(totalP),
                String.valueOf(totalL),
                String.valueOf(totalR)));
                */




        //this.nuevoPoste = new AdaptadorFourItems(FormInformacionRutas.this, arrayListadoRutas);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_redes_poste, menu);
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
}
