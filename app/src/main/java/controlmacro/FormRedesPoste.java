package controlmacro;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.AdaptadorRedesPoste;
import Adapter.DetalleFourItems;
import Adapter.DetalleRedesPoste;
import dialogos.DialogRedesEquipos;
import dialogos.DialogRedesLineas;
import dialogos.DialogRedesLuminarias;


public class FormRedesPoste extends ActionBarActivity {
    private ListView        _lstListadoPostes;
    private Intent          new_form;

    private AdaptadorRedesPoste listadoPostesAdapter;
    private ArrayList<DetalleRedesPoste>    arrayListadoPoste;

    private ArrayList<String> alturasPoste;
    private ArrayList<String> tiposPoste;
    private ArrayList<String> estadoPoste;
    private ArrayList<String> materialPoste;
    private ArrayList<String> estructuraPoste;

    private String  itemPoste;
    private String  tipoPoste;
    private int     itemSeleccionado;

    private static int ACT_REGISTRO_EQUIPOS = 1;
    private static int ACT_REGISTRO_LINEAS = 2;
    private static int ACT_REGISTRO_LUMINARIAS = 3;

    private ArrayList<ContentValues> datosRegistroEquipos = new ArrayList<ContentValues>();

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

        registerForContextMenu(this._lstListadoPostes);


        /*arrayListadoRutas.add(new DetalleFourItems( this._tempRegistro.getAsString("nodo"),
                String.valueOf(totalP),
                String.valueOf(totalL),
                String.valueOf(totalR)));
                */




        //this.nuevoPoste = new AdaptadorFourItems(FormInformacionRutas.this, arrayListadoRutas);
    }

    /**Funciones para el control del menu contextual del listview que muestra las ordenes de trabajo**/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        this.itemPoste  = arrayListadoPoste.get(info.position).getItemPoste();
        this.tipoPoste  = arrayListadoPoste.get(info.position).getTipoPoste();
        this.itemSeleccionado = info.position;

        switch(v.getId()){
            case R.id.RedesLstPostes:
                menu.setHeaderTitle(this.tipoPoste+" "+this.itemPoste);
                super.onCreateContextMenu(menu, v, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_context_redes, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.RedesMenuContextEquipos:
                this.new_form = new Intent(this, DialogRedesEquipos.class);
                this.new_form.putExtra("Item",this.itemPoste);
                this.new_form.putExtra("Tipo",this.tipoPoste);
                startActivityForResult(this.new_form, ACT_REGISTRO_EQUIPOS);
                return true;

            case R.id.RedesMenuContextLineas:
                this.new_form = new Intent(this, DialogRedesLineas.class);
                this.new_form.putExtra("Item",this.itemPoste);
                this.new_form.putExtra("Tipo",this.tipoPoste);
                startActivityForResult(this.new_form, ACT_REGISTRO_LINEAS);
                return true;

            case R.id.RedesMenuContextLuminarias:
                this.new_form = new Intent(this, DialogRedesLuminarias.class);
                this.new_form.putExtra("Item",this.itemPoste);
                this.new_form.putExtra("Tipo",this.tipoPoste);
                startActivityForResult(this.new_form, ACT_REGISTRO_LUMINARIAS);
                return true;

            case R.id.RedesMenuContextEliminar:
                this.arrayListadoPoste.remove(this.itemSeleccionado);
                this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).setAltura(this.alturasPoste)
                        .setTipo(this.tiposPoste).setEstado(this.estadoPoste).setMaterial(this.materialPoste).setEstructura(this.estructuraPoste).build();
                this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                this.listadoPostesAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_EQUIPOS){
                if(data.getExtras().getBoolean("response")){
                    this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosEquipos");
                }
            }else{
                if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_LINEAS){
                    if(data.getExtras().getBoolean("response")){
                        this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosLineas");
                    }
                }else{
                    if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_LUMINARIAS){
                        if(data.getExtras().getBoolean("response")){
                            this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosLuminarias");
                        }
                    }
                }
            }
        }catch(Exception e){

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_redes_poste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.RedesMenuAgregar:
                this.arrayListadoPoste.add(new DetalleRedesPoste((this.arrayListadoPoste.size()+1)+"","","","","","","","","",""));
                this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).setAltura(this.alturasPoste)
                        .setTipo(this.tiposPoste).setEstado(this.estadoPoste).setMaterial(this.materialPoste).setEstructura(this.estructuraPoste).build();
                this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                this.listadoPostesAdapter.notifyDataSetChanged();

                break;

            case R.id.RedesMenuGuardar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
