package controlmacro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import clases.ClassTomaLectura;
import clases.ClassFormatos;
import clases.ClassSession;
import dialogos.DialogoInformativo;
import dialogos.DialogoSingleInput;


public class FormTomarLectura extends ActionBarActivity implements OnClickListener{
    static int 				    INICIAR_CAMARA			= 1;
    static int                  FROM_BUSCAR             = 2;
    static int                  FINAL_RUTA              = 3;
    static int                  CONFIRMAR_INPUT         = 4;

    private Intent 			    IniciarCamara;
    private Intent              new_form;

    private ClassSession        FcnSession;
    private ClassTomaLectura    FcnLectura;
    //private ClassFormatos       FcnFormatos;

    private DialogoInformativo  dialogo;
    //private Bundle              argumentos;

    private TextView    _lblCuenta, _lblNombre, _lblDireccion, _lblNodo, _lblMedidor;
    private EditText    _txtNumeroPoste, _txtLectura, _txtObservacion;
    private Button      _btnGuardar, _btnSiguiente, _btnAnterior;

    private String                  nodo;
    public static String           inputDialog;


    private ArrayAdapter<String>    AdaptadorAnomalias;
    private ArrayAdapter<String>    AdaptadorUso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_lectura);

        this.IniciarCamara	= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Bundle bundle   = getIntent().getExtras();
        this.nodo       = bundle.getString("Nodo");

        this.FcnSession         = ClassSession.getInstance(this);
        this.FcnLectura         = new ClassTomaLectura(this, this.nodo);
        //this.FcnFormatos        = new ClassFormatos(this, false);
        this.dialogo            = new DialogoInformativo();
        //this.argumentos         = new Bundle();

        this._lblCuenta     = (TextView) findViewById(R.id.LecturaLblCuenta);
        this._lblNombre     = (TextView) findViewById(R.id.LecturaLblNombre);
        this._lblDireccion  = (TextView) findViewById(R.id.LecturaLblDireccion);
        this._lblNodo       = (TextView) findViewById(R.id.LecturaLblRuta);
        this._lblMedidor    = (TextView) findViewById(R.id.LecturaLblMedidor);

        //this._lblLectura1   = (TextView) findViewById(R.id.LecturaTxtLectura1);
        //this._lblLectura2   = (TextView) findViewById(R.id.LecturaTxtLectura2);
        //this._lblLectura3   = (TextView) findViewById(R.id.LecturaTxtLectura3);
        //this._lblCritica    = (TextView) findViewById(R.id.LecturaTxtCritica);

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

        /*this.AdaptadorAnomalias =   new CustomSpinnerAdapter.BuilderSpinnerAdapter(this,R.layout.custom_spinner,this.FcnAnomalias.getAnomalias(this.FcnLectura.getInfUsuario().getTipo_uso()))
                                                            .colorText("#FF5CBD79")
                                                            .colorBack("#6B5656")
                                                            .sizeText(18)
                                                            .build();*/

        /*this.AdaptadorUso = new CustomSpinnerAdapter.BuilderSpinnerAdapter(this,R.layout.custom_spinner,this.FcnTipoUso.getTipoUso())
                                                    .colorText("#FF5CBD79")
                                                    .colorBack("#6B5656")
                                                    .sizeText(18)
                                                    .paddingText(0,5,0,0)
                                                    .build();*/

        /*this._cmbAnomalia.setAdapter(this.AdaptadorAnomalias);
        this._cmbTipoUso.setAdapter(this.AdaptadorUso);

        this._cmbTipoUso.setOnItemSelectedListener(this);
        this._cmbAnomalia.setOnItemSelectedListener(this);*/

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

            case R.id.LecturaMenuRendimiento:
                //this.new_form = new Intent(this, DialogoSingleInput.class);
                //new DialogoSingleInput().DialogoSingleInput(this, "CONFIRMACION", "Lectura");

                //new ShowDialog().showLoginDialog(this, this.FcnLectura.getInfUsuario().getRuta());
                break;

            case R.id.LecturaMenuReImprimir:
                //this.FcnFormatos.FormatoPrueba();
                //this.FcnFormatos.ActaLectura();
                /*this.FcnFormatos.ActaLectura(   this.lectura1,
                                                this.FcnLectura.getCuenta(),
                                                "FALTA",
                                                this.FcnLectura.getNombre(),
                                                this.FcnLectura.getDireccion(),
                                                this.FcnLectura.getSerie_medidor()+"-"+this.FcnLectura.getMarca_medidor(),
                                                ClassUsuario.getInstance(this).getCodigo());*/
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
                    /*this._cmbAnomalia.setSelection(0);
                    this._cmbTipoUso.setSelection(0);*/
                    this.MostrarInformacionBasica();
                }
                break;

            case R.id.LecturaBtnSiguiente:
                if(this.FcnLectura.getDatosUsuario(true)){
                    /*this._cmbAnomalia.setSelection(0);
                    this._cmbTipoUso.setSelection(0);*/
                    this.MostrarInformacionBasica();
                }
                break;


            case R.id.LecturasBtnGuardar:
                this.FcnLectura.getInfUsuario().setNumeroPoste(Integer.parseInt(this._txtNumeroPoste.getText().toString()));
                this.FcnLectura.getInfUsuario().setLectura(Integer.parseInt(this._txtLectura.getText().toString()));
                this.FcnLectura.getInfUsuario().setObservacion(this._txtObservacion.getText().toString());

                new DialogoSingleInput().DialogoSingleInput(this, "CONFIRMACION", "Lectura");



                /*if(this.FcnLectura.getInfUsuario().isNeedMensaje() && this._txtMensaje.getText().toString().isEmpty()){
                    this.argumentos.clear();
                    this.argumentos.putString("Titulo","ERROR.");
                    this.argumentos.putString("Mensaje","No se ha ingresado un mensaje valido.");
                    this.dialogo.setArguments(argumentos);
                    this.dialogo.show(getFragmentManager(), "SaveDialog");
                }else{
                    this.FcnLectura.preCritica(this._txtLectura1.getText().toString(),this._txtLectura2.getText().toString(),this._txtLectura3.getText().toString());

                    this._lblCritica.setText(this.FcnLectura.getInfUsuario().getDescripcionCritica());

                    if((this.FcnLectura.getInfUsuario().isHaveCritica() || this.FcnLectura.getInfUsuario().isNeedFoto()) &&
                            this.FcnLectura.getInfUsuario().getCountFotos() == 0 &&
                            (this.FcnLectura.getInfUsuario().getIntentos() == 1 || this.FcnLectura.getInfUsuario().getIntentos() == 2)){
                        this.getFoto();
                    }else if((this.FcnLectura.getInfUsuario().isNeedLectura() && !this._txtLectura1.getText().toString().isEmpty()) ||
                                !this.FcnLectura.getInfUsuario().isNeedLectura()){
                        if(this.FcnLectura.guardarLectura(this._txtLectura1.getText().toString(),
                                this._txtLectura2.getText().toString(),
                                this._txtLectura3.getText().toString(),
                                this._txtMensaje.getText().toString())) {
                            this._txtLectura1.setText("");
                            this._txtLectura2.setText("");
                            this._txtLectura3.setText("");

                            if (this.FcnLectura.getInfUsuario().isLeido()) {
                                this._btnGuardar.setEnabled(false);
                                this.FcnFormatos.ActaLectura(this.FcnLectura.getInfUsuario().getTipo_energia1(),
                                        this.FcnLectura.getInfUsuario().getLectura1(),
                                        this.FcnLectura.getInfUsuario().getStrAnomalia(),
                                        this.FcnLectura.getInfUsuario().getCuenta(),
                                        this.FcnLectura.getInfUsuario().getMunicipio(),
                                        this.FcnLectura.getInfUsuario().getNombre(),
                                        this.FcnLectura.getInfUsuario().getDireccion(),
                                        this.FcnLectura.getInfUsuario().getSerie_medidor() + "-" + this.FcnLectura.getInfUsuario().getMarca_medidor(),
                                        ClassSession.getInstance(this).getCodigo());

                                new UploadLecturas(this).execute(this.FcnLectura.getInfUsuario().getRuta());

                                if (this.FcnLectura.getDatosUsuario(true)) {
                                    this._cmbAnomalia.setSelection(0);
                                    this._cmbTipoUso.setSelection(0);
                                    this.MostrarInformacionBasica();
                                } else {
                                    this.argumentos.clear();
                                    this.argumentos.putString("Titulo", "FIN DE RUTA.");
                                    this.argumentos.putString("Mensaje", "Se ha llegado al final de la ruta.");
                                    this.dialogo.setArguments(argumentos);
                                    this.dialogo.show(getFragmentManager(), "SaveDialog");
                                }
                            } else if (this.FcnLectura.getInfUsuario().isHaveCritica()) {
                                this.argumentos.clear();
                                this.argumentos.putString("Titulo", "ERROR.");
                                this.argumentos.putString("Mensaje", "Se ha generado critica, ingrese la lectura nuevamente.");
                                this.dialogo.setArguments(argumentos);
                                this.dialogo.show(getFragmentManager(), "SaveDialog");
                            }
                        }else {
                            this.argumentos.clear();
                            this.argumentos.putString("Titulo", "ERROR.");
                            this.argumentos.putString("Mensaje", "No ha sido posible registrar la lectura.");
                            this.dialogo.setArguments(argumentos);
                            this.dialogo.show(getFragmentManager(), "SaveDialog");
                        }
                    }else {
                        this.argumentos.clear();
                        this.argumentos.putString("Titulo","ERROR.");
                        this.argumentos.putString("Mensaje","No ha sido posible registrar la lectura.");
                        this.dialogo.setArguments(argumentos);
                        this.dialogo.show(getFragmentManager(), "SaveDialog");
                    }
                }*/
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(resultCode == RESULT_OK && requestCode == FROM_BUSCAR){
                if(data.getExtras().getBoolean("response")){
                    //this.FcnLectura.getInfUsuario().setFlagSearch(true);
                    //this.FcnLectura.getInfUsuario().setBackupRuta(this.FcnLectura.getInfUsuario().getRuta());
                    //this.FcnLectura.getInfUsuario().setBackupConsecutivo(this.FcnLectura.getInfUsuario().getId_consecutivo());

                    this.FcnLectura.getSearchDatosUsuario(data.getExtras().getString("cuenta"),data.getExtras().getString("medidor"));
                    //this._cmbAnomalia.setSelection(0);
                    //this._cmbTipoUso.setSelection(0);
                    this.MostrarInformacionBasica();
                }
            }else if(resultCode == RESULT_OK && requestCode == INICIAR_CAMARA){
                this.FcnLectura.getNumeroFotos();
            }else if(resultCode == RESULT_OK && requestCode == FINAL_RUTA){
                this.finish();
            }else if(resultCode == RESULT_OK && requestCode == CONFIRMAR_INPUT){
                if(data.getExtras().getBoolean("response")){
                    String resultado = data.getExtras().getString("txt1");
                }
            }
        }catch(Exception e){

        }
    }


    private void getFoto(){
        /*File imagesFolder   = new File(FormInicioSession.path_files_app, FormInicioSession.sub_path_pictures);
        File image          = new File( imagesFolder,
                                        this.FcnLectura.getInfUsuario().getCuenta()+"_"+this.FcnLectura.getInfUsuario().getCountFotos()+".jpeg");

        Uri uriSavedImage = Uri.fromFile(image);
        this.IniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(IniciarCamara, INICIAR_CAMARA);*/
    }
}
