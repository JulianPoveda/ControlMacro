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
import Adapter.DetalleRedesPoste;
import dialogos.DialogConfirm;
import dialogos.DialogRedesEquipos;
import dialogos.DialogRedesLineas;
import dialogos.DialogRedesLuminarias;
import dialogos.DialogRedesPoste;


public class FormRedesPoste extends ActionBarActivity {
    private ListView        _lstListadoPostes;
    private Intent          new_form;

    private AdaptadorRedesPoste             listadoPostesAdapter;
    private ArrayList<DetalleRedesPoste>    arrayListadoPoste;

    private String  itemPoste;
    private String  tipoPoste;
    private int     itemSeleccionado;

    private static int ACT_REGISTRO_EQUIPOS     = 1;
    private static int ACT_REGISTRO_LINEAS      = 2;
    private static int ACT_REGISTRO_LUMINARIAS  = 3;
    private static int ACT_EDITAR_REDES_POSTE   = 4;
    private static int ACT_ELIMINAR_REDES_POSTE = 5;
    private static int ACT_NEW_REDES_POSTE      = 6;

    private ArrayList<ContentValues> datosRegistroEquipos = new ArrayList<ContentValues>();

    private ArrayList<ContentValues> datosPoste = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_poste);

        this._lstListadoPostes  = (ListView) findViewById(R.id.RedesLstPostes);

        this.arrayListadoPoste = new ArrayList<DetalleRedesPoste>();
        this.arrayListadoPoste.clear();

        this.arrayListadoPoste.add(new DetalleRedesPoste("1","0.1","0.2","Poste","","Bueno","Madera","12","","Sin Observaciones"));
        this.arrayListadoPoste.add(new DetalleRedesPoste("2","1.1","2.4","Caja","","Bueno","Metal","0","","Con Observaciones"));

        this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();

        this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
        this.listadoPostesAdapter.notifyDataSetChanged();

        registerForContextMenu(this._lstListadoPostes);
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

            case R.id.RedesMenuContextEditar:
                this.arrayListadoPoste.remove(this.itemSeleccionado);
                this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();
                this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                this.listadoPostesAdapter.notifyDataSetChanged();
                return true;

            case R.id.RedesMenuContextEliminar:
                this.new_form = new Intent(this, DialogConfirm.class);
                this.new_form.putExtra("titulo","CONFIRMACION");
                this.new_form.putExtra("lbl1","Se eliminara el item "+this.itemPoste+", desea continuar?");
                startActivityForResult(this.new_form, ACT_ELIMINAR_REDES_POSTE);
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
            }else if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_LINEAS){
                if(data.getExtras().getBoolean("response")){
                    this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosLineas");
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_LUMINARIAS){
                if(data.getExtras().getBoolean("response")){
                    this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosLuminarias");
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_EDITAR_REDES_POSTE){
                /*if(data.getExtras().getBoolean("response")){
                    this.datosRegistroEquipos = data.getParcelableArrayListExtra("datosLuminarias");
                }*/
            }else if(resultCode == RESULT_OK && requestCode == ACT_ELIMINAR_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    this.arrayListadoPoste.remove(this.itemSeleccionado);
                    this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();
                    this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                    this.listadoPostesAdapter.notifyDataSetChanged();
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_NEW_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    this.datosPoste = data.getParcelableArrayListExtra("datosPoste");
                    this.arrayListadoPoste.add(new DetalleRedesPoste((this.arrayListadoPoste.size()+1)+"",
                            this.datosPoste.get(0).getAsString("gpsLat"),
                            this.datosPoste.get(0).getAsString("gpsLong"),
                            this.datosPoste.get(0).getAsString("tipoPoste"),
                            this.datosPoste.get(0).getAsString("compartidoPoste"),
                            this.datosPoste.get(0).getAsString("estadoPoste"),
                            this.datosPoste.get(0).getAsString("materialPoste"),
                            this.datosPoste.get(0).getAsString("alturaPoste"),
                            this.datosPoste.get(0).getAsString("estructuraPoste"),
                            this.datosPoste.get(0).getAsString("observacionPoste")));
                    this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();
                    this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                    this.listadoPostesAdapter.notifyDataSetChanged();
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
                this.new_form = new Intent(this, DialogRedesPoste.class);
                startActivityForResult(this.new_form, ACT_NEW_REDES_POSTE);


                /*this.arrayListadoPoste.add(new DetalleRedesPoste((this.arrayListadoPoste.size()+1)+"","","","","","","","","",""));
                this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();
                this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
                this.listadoPostesAdapter.notifyDataSetChanged();*/
                break;

            case R.id.RedesMenuGuardar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
