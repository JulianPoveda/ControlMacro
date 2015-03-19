package controlmacro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import clases.ClassTomaLectura;
import clases.ClassSession;
import dialogos.DialogConfirm;
import dialogos.DialogDeleteUsuario;
import dialogos.DialogNewUsuario;
import dialogos.DialogSingleInput;
import dialogos.DialogoInformativo;


public class FormTomarLectura extends ActionBarActivity implements OnClickListener{
    static int 				    INICIAR_CAMARA			= 1;
    static int                  FROM_BUSCAR             = 2;
    static int                  FINAL_RUTA              = 3;
    static int                  CONFIRMAR_LECTURA       = 4;
    static int                  CONFIRMAR_ELIMINACION   = 5;
    static int                  NUEVO_USUARIO           = 6;

    private Intent 			    IniciarCamara;
    private Intent              new_form;
    private Bundle              argumentos;

    private ClassSession        FcnSession;
    private ClassTomaLectura    FcnLectura;
    private DialogoInformativo  dialogo;

    private TextView    _lblCuenta, _lblNombre, _lblDireccion, _lblNodo, _lblMedidor;
    private EditText    _txtNumeroPoste, _txtLectura, _txtObservacion;
    private Button      _btnGuardar, _btnSiguiente, _btnAnterior;

    private String                  nodo;
    private String                  tempNewNodo, tempPoste, tempLectura, tempObservacion;
    private boolean                 flagEliminacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_lectura);

        this.IniciarCamara	= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Bundle bundle   = getIntent().getExtras();
        this.nodo       = bundle.getString("Nodo");

        this.FcnSession         = ClassSession.getInstance(this);
        this.FcnLectura         = new ClassTomaLectura(this, this.nodo);
        this.dialogo            = new DialogoInformativo();
        this.argumentos         = new Bundle();

        this._lblCuenta     = (TextView) findViewById(R.id.LecturaLblCuenta);
        this._lblNombre     = (TextView) findViewById(R.id.LecturaLblNombre);
        this._lblDireccion  = (TextView) findViewById(R.id.LecturaLblDireccion);
        this._lblNodo       = (TextView) findViewById(R.id.LecturaLblRuta);
        this._lblMedidor    = (TextView) findViewById(R.id.LecturaLblMedidor);

        this._txtNumeroPoste= (EditText) findViewById(R.id.LecturaTxtPoste);
        this._txtLectura    = (EditText) findViewById(R.id.LecturaTxtLectura1);
        this._txtObservacion= (EditText) findViewById(R.id.LecturaTxtObservacion);

        this._btnGuardar    = (Button) findViewById(R.id.LecturasBtnGuardar);
        this._btnSiguiente  = (Button) findViewById(R.id.LecturaBtnSiguiente);
        this._btnAnterior   = (Button) findViewById(R.id.LecturaBtnAnterior);

        if(this.FcnLectura.getDatosUsuario(true)){
            this.MostrarInformacionBasica();
            this._btnGuardar.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        }

        this._btnGuardar.setOnClickListener(this);
        this._btnAnterior.setOnClickListener(this);
        this._btnSiguiente.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tomar_lectura, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.LecturaMenuBuscar:
                this.new_form = new Intent(this, FormBuscar.class);
                startActivityForResult(this.new_form, FROM_BUSCAR);
                break;

            case R.id.LecturaMenuFoto:
                this.getFoto();
                break;

            case R.id.LecturaMenuEliminar:
                this.new_form = new Intent(this, DialogDeleteUsuario.class);
                startActivityForResult(this.new_form, CONFIRMAR_ELIMINACION);
                break;

            case R.id.LecturaMenuNuevo:
                this.new_form = new Intent(this, DialogNewUsuario.class);
                startActivityForResult(this.new_form, NUEVO_USUARIO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void MostrarInformacionBasica(){
        this._lblNodo.setText(this.FcnLectura.getInfUsuario().getNodo());
        this._lblCuenta.setText(this.FcnLectura.getInfUsuario().getCuenta() + "");
        this._lblMedidor.setText(this.FcnLectura.getInfUsuario().getMarca_medidor()+" "+this.FcnLectura.getInfUsuario().getSerie_medidor());
        this._lblNombre.setText(this.FcnLectura.getInfUsuario().getNombre());
        this._lblDireccion.setText(this.FcnLectura.getInfUsuario().getDireccion());

        this._txtNumeroPoste.setText("");
        this._txtLectura.setText("");
        this._txtObservacion.setText("");
        this._btnGuardar.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtLectura.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
        this._txtObservacion.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.LecturaBtnAnterior:
                if(this.FcnLectura.getDatosUsuario(false)) {
                    this.MostrarInformacionBasica();
                }
                break;

            case R.id.LecturaBtnSiguiente:
                if(this.FcnLectura.getDatosUsuario(true)){
                    this.MostrarInformacionBasica();
                }
                break;

            case R.id.LecturasBtnGuardar:
                validarInformacion("",this._txtNumeroPoste.getText().toString(),
                        this._txtLectura.getText().toString(),this._txtObservacion.getText().toString());
                break;

            default:
                break;
        }
    }


    private void guardarLecturaUsuario(String _newNodo, String _numPoste, String _lectura, String _observacion){
        this.FcnLectura.getInfUsuario().setNew_nodo(_newNodo);
        this.FcnLectura.getInfUsuario().setNumeroPoste(Integer.parseInt(_numPoste));
        this.FcnLectura.getInfUsuario().setObservacion(_observacion);
        if(_lectura.isEmpty()){
            this.FcnLectura.getInfUsuario().setLectura(-1);
        }else{
            this.FcnLectura.getInfUsuario().setLectura(Integer.parseInt(_lectura));
        }

        if(this.FcnLectura.registrarInformacion()){
            if(this.flagEliminacion){
                this.flagEliminacion = false;
                this.FcnLectura.eliminarUsuario();
            }
            if(this.FcnLectura.getDatosUsuario(true)){
                this.MostrarInformacionBasica();
                this._btnGuardar.setEnabled(!this.FcnLectura.getInfUsuario().isLeido());
            }
        }else{
            this.argumentos.clear();
            this.argumentos.putString("Titulo", "ERROR.");
            this.argumentos.putString("Mensaje", "No ha sido posible registrar la informacion.");
            this.dialogo.setArguments(argumentos);
            this.dialogo.show(getFragmentManager(), "SaveDialog");
        }
    }


    private void validarInformacion(String _newNodo, String _numPoste, String _lectura, String _observacion){
        this.tempNewNodo    = _newNodo;
        this.tempPoste      = _numPoste;
        this.tempLectura    = _lectura;
        this.tempObservacion= _observacion;


        if(!_numPoste.isEmpty()) {
            if (_lectura.isEmpty()) {
                if (_observacion.isEmpty()) {
                    this.argumentos.clear();
                    this.argumentos.putString("Titulo", "ERROR.");
                    this.argumentos.putString("Mensaje", "No ha ingresado la observacion.");
                    this.dialogo.setArguments(argumentos);
                    this.dialogo.show(getFragmentManager(), "SaveDialog");
                } else {
                    this.guardarLecturaUsuario(_newNodo, _numPoste, _lectura, _observacion);
                }
            }else {
                if (this.FcnLectura.getInfUsuario().getFotos() == 0) {
                    this.getFoto();
                }else {
                    this.new_form = new Intent(this, DialogSingleInput.class);
                    this.new_form.putExtra("titulo", "CONFIRMACION");
                    this.new_form.putExtra("lbl1", "Lectura");
                    this.new_form.putExtra("txt1", "");
                    startActivityForResult(this.new_form, CONFIRMAR_LECTURA);
                }
            }
        }else{
            this.argumentos.clear();
            this.argumentos.putString("Titulo", "ERROR.");
            this.argumentos.putString("Mensaje", "No ha ingresado el numero de poste o caja.");
            this.dialogo.setArguments(argumentos);
            this.dialogo.show(getFragmentManager(), "SaveDialog");
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(resultCode == RESULT_OK && requestCode == FROM_BUSCAR){
                if(data.getExtras().getBoolean("response")){
                    this.FcnLectura.getSearchDatosUsuario(data.getExtras().getString("cuenta"),data.getExtras().getString("medidor"));
                    this.MostrarInformacionBasica();
                }
            }else if(resultCode == RESULT_OK && requestCode == INICIAR_CAMARA){
                this.FcnLectura.getCantidadFotos();
            }else if(resultCode == RESULT_OK && requestCode == FINAL_RUTA){
                this.finish();
            }else if(resultCode == RESULT_OK && requestCode == CONFIRMAR_LECTURA){
                if(data.getExtras().getBoolean("response")){
                    if(this.tempLectura.equals(data.getExtras().getString("txt1"))){
                        this.guardarLecturaUsuario(this.tempNewNodo, this.tempPoste, this.tempLectura, this.tempObservacion);
                    }else{
                        this.argumentos.clear();
                        this.argumentos.putString("Titulo", "ERROR.");
                        this.argumentos.putString("Mensaje", "Error en le confirmacion de lectura.");
                        this.dialogo.setArguments(argumentos);
                        this.dialogo.show(getFragmentManager(), "SaveDialog");
                    }
                }
            }else if(resultCode == RESULT_OK && requestCode == CONFIRMAR_ELIMINACION){
                if(data.getExtras().getBoolean("response")){
                    if(data.getExtras().getString("new_nodo").equals(_lblNodo.getText().toString())){
                        this.argumentos.clear();
                        this.argumentos.putString("Titulo", "ERROR.");
                        this.argumentos.putString("Mensaje", "Error, el nuevo nodo es igual al nodo sobre el que se esta trabajando.");
                        this.dialogo.setArguments(argumentos);
                        this.dialogo.show(getFragmentManager(), "SaveDialog");
                    }else{
                        this.flagEliminacion = true;
                        this.validarInformacion(data.getExtras().getString("new_nodo"),
                                                data.getExtras().getString("poste"),
                                                data.getExtras().getString("lectura"),
                                                data.getExtras().getString("observacion"));
                    }
                }else{
                    this.flagEliminacion = false;
                }
            }else if(resultCode == RESULT_OK && requestCode == NUEVO_USUARIO){
                if(data.getExtras().getBoolean("response")) {
                    if(data.getExtras().getString("marca").isEmpty() || data.getExtras().getString("serie").isEmpty()){
                        this.argumentos.clear();
                        this.argumentos.putString("Titulo", "ERROR.");
                        this.argumentos.putString("Mensaje", "Error, no ha ingresado los datos minimos para la creacion del usuario.");
                        this.dialogo.setArguments(argumentos);
                        this.dialogo.show(getFragmentManager(), "SaveDialog");
                    }else{
                        if(this.FcnLectura.crearUsuario(data.getExtras().getString("marca"),
                                data.getExtras().getString("serie"),
                                data.getExtras().getString("cuenta"),
                                data.getExtras().getString("nombre"),
                                data.getExtras().getString("direccion"))){
                            this.argumentos.clear();
                            this.argumentos.putString("Titulo", "INFORMACION.");
                            this.argumentos.putString("Mensaje", "Usuario creado correctamente.");
                            this.dialogo.setArguments(argumentos);
                            this.dialogo.show(getFragmentManager(), "SaveDialog");
                        }else{
                            this.argumentos.clear();
                            this.argumentos.putString("Titulo", "ERROR.");
                            this.argumentos.putString("Mensaje", "No fue posible crear al usuario.");
                            this.dialogo.setArguments(argumentos);
                            this.dialogo.show(getFragmentManager(), "SaveDialog");
                        }
                    }
                }
            }
        }catch(Exception e){

        }
    }


    private void getFoto(){
        File imagesFolder   = new File(FormInicioSession.path_files_app, FormInicioSession.sub_path_pictures);
        File image          = new File( imagesFolder,
                                        this.FcnLectura.getInfUsuario().getCuenta()+"_"+this.FcnLectura.getInfUsuario().getFotos()+".jpeg");

        Uri uriSavedImage = Uri.fromFile(image);
        this.IniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(IniciarCamara, INICIAR_CAMARA);
    }
}
