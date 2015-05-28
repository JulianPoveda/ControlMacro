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
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.AdaptadorRedesPoste;
import Adapter.DetalleRedesPoste;
import clases.ClassRedesPoste;
import dialogos.DialogConfirm;
import dialogos.DialogRedesEquipos;
import dialogos.DialogRedesLineas;
import dialogos.DialogRedesLuminarias;
import dialogos.DialogRedesPoste;

import Object.Poste;


public class FormRedesPoste extends ActionBarActivity {
    private static int ACT_REGISTRO_EQUIPOS         = 1;
    private static int ACT_REGISTRO_LINEAS          = 2;
    private static int ACT_REGISTRO_LUMINARIAS      = 3;
    private static int ACT_EDITAR_REDES_POSTE       = 4;
    private static int ACT_ELIMINAR_REDES_POSTE     = 5;
    private static int ACT_NEW_REDES_POSTE          = 6;
    private static int ACT_ACTUALIZAR_REDES_POSTE   = 7;

    private ListView        _lstListadoPostes;
    private Intent          new_form;

    private ClassRedesPoste FcnRedesPoste;
    private String  nodo;
    private String  itemPoste;
    private String  tipoPoste;
    private int     itemSeleccionado;

    //private ArrayList<ContentValues>datosRegistroEquipos = new ArrayList<ContentValues>();
    private ArrayList<ContentValues>_tempTabla      = new ArrayList<ContentValues>();
    private ContentValues           _tempRegistro   = new ContentValues();


    private AdaptadorRedesPoste             listadoPostesAdapter;
    private ArrayList<DetalleRedesPoste>    arrayListadoPoste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_poste);

        Bundle bundle = getIntent().getExtras();
        this.nodo = bundle.getString("Nodo");

        this.FcnRedesPoste  = new ClassRedesPoste(this,this.nodo);

        this._lstListadoPostes  = (ListView) findViewById(R.id.RedesLstPostes);

        this.arrayListadoPoste = new ArrayList<DetalleRedesPoste>();
        this.mostrarInformacionPostes();
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
                this.new_form.putExtra("Nodo",this.nodo);
                this.new_form.putExtra("Item",this.itemPoste);
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
                this.new_form.putExtra("Nodo",this.nodo);
                startActivityForResult(this.new_form, ACT_REGISTRO_LUMINARIAS);
                return true;

            case R.id.RedesMenuContextEditar:
                this.new_form = new Intent(this, DialogConfirm.class);
                this.new_form.putExtra("titulo","CONFIRMACION");
                this.new_form.putExtra("lbl1", "Desea editar el item " + this.itemPoste + "?");
                startActivityForResult(this.new_form, ACT_EDITAR_REDES_POSTE);
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
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_REGISTRO_LINEAS){
                if(data.getExtras().getBoolean("response")){
                    this._tempTabla = data.getParcelableArrayListExtra("datosLineas");
                    if(this.FcnRedesPoste.crearLineas(this.itemPoste,
                            this._tempTabla.get(0).getAsString("faseA"),
                            this._tempTabla.get(0).getAsString("faseB"),
                            this._tempTabla.get(0).getAsString("faseC"),
                            this._tempTabla.get(0).getAsString("faseAP"),
                            this._tempTabla.get(0).getAsString("faseN"),
                            this._tempTabla.get(0).getAsString("conductor"))){
                        Toast.makeText(this, "Lineas registradas correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_EDITAR_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    this.new_form = new Intent(this, DialogRedesPoste.class);
                    this._tempRegistro = this.FcnRedesPoste.getDataPoste(Integer.parseInt(this.itemPoste));
                    this.new_form.putExtra("longitud", this._tempRegistro.getAsString("longitud"));
                    this.new_form.putExtra("latitud", this._tempRegistro.getAsString("latitud"));
                    this.new_form.putExtra("tipo", this._tempRegistro.getAsString("tipo"));
                    this.new_form.putExtra("compartido", this._tempRegistro.getAsString("compartido"));
                    this.new_form.putExtra("estado", this._tempRegistro.getAsString("estado"));
                    this.new_form.putExtra("material", this._tempRegistro.getAsString("material"));
                    this.new_form.putExtra("altura", this._tempRegistro.getAsString("altura"));
                    this.new_form.putExtra("estructura", this._tempRegistro.getAsString("estructura"));
                    this.new_form.putExtra("observacion", this._tempRegistro.getAsString("observacion"));
                    startActivityForResult(this.new_form, ACT_ACTUALIZAR_REDES_POSTE);
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_ELIMINAR_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    if(this.FcnRedesPoste.eliminarPoste(/*this.itemSeleccionado*/Integer.parseInt(this.itemPoste))){
                        this.mostrarInformacionPostes();
                    }
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_NEW_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    this._tempTabla = data.getParcelableArrayListExtra("datosPoste");
                    if(this.FcnRedesPoste.crearPoste(this._tempTabla.get(0).getAsString("gpsLat"),
                            this._tempTabla.get(0).getAsString("gpsLong"),
                            this._tempTabla.get(0).getAsString("tipoPoste"),
                            this._tempTabla.get(0).getAsString("compartidoPoste"),
                            this._tempTabla.get(0).getAsString("estadoPoste"),
                            this._tempTabla.get(0).getAsString("materialPoste"),
                            this._tempTabla.get(0).getAsInteger("alturaPoste"),
                            this._tempTabla.get(0).getAsString("estructuraPoste"),
                            this._tempTabla.get(0).getAsString("observacionPoste"))){
                        this.mostrarInformacionPostes();
                    }
                }
            }else if(resultCode == RESULT_OK && requestCode == ACT_ACTUALIZAR_REDES_POSTE){
                if(data.getExtras().getBoolean("response")){
                    this._tempTabla = data.getParcelableArrayListExtra("datosPoste");
                    if(this.FcnRedesPoste.editarPoste(this.itemPoste,
                            this._tempTabla.get(0).getAsString("gpsLat"),
                            this._tempTabla.get(0).getAsString("gpsLong"),
                            this._tempTabla.get(0).getAsString("tipoPoste"),
                            this._tempTabla.get(0).getAsString("compartidoPoste"),
                            this._tempTabla.get(0).getAsString("estadoPoste"),
                            this._tempTabla.get(0).getAsString("materialPoste"),
                            this._tempTabla.get(0).getAsInteger("alturaPoste"),
                            this._tempTabla.get(0).getAsString("estructuraPoste"),
                            this._tempTabla.get(0).getAsString("observacionPoste"))){
                        this.mostrarInformacionPostes();
                    }
                }
            }
        }catch(Exception e){
            e.toString();
        }
    }


    private void mostrarInformacionPostes(){
        this.arrayListadoPoste.clear();
        this._tempTabla = this.FcnRedesPoste.listaPostes();
        for(int i=0; i<this._tempTabla.size(); i++){
            this._tempRegistro  = this._tempTabla.get(i);
            this.arrayListadoPoste.add(new DetalleRedesPoste(this._tempRegistro.get("item")+"",
                    this._tempRegistro.getAsString("longitud"),
                    this._tempRegistro.getAsString("latitud"),
                    this._tempRegistro.getAsString("tipo"),
                    this._tempRegistro.getAsString("compartido"),
                    this._tempRegistro.getAsString("estado"),
                    this._tempRegistro.getAsString("material"),
                    this._tempRegistro.getAsString("altura"),
                    this._tempRegistro.getAsString("estructura"),
                    this._tempRegistro.getAsString("observacion")));
        }
        this.listadoPostesAdapter = new AdaptadorRedesPoste.BuilderAdaptadorRedesPoste(this, this.arrayListadoPoste).build();
        this._lstListadoPostes.setAdapter(this.listadoPostesAdapter);
        this.listadoPostesAdapter.notifyDataSetChanged();
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
                break;

            case R.id.RedesMenuGuardar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
