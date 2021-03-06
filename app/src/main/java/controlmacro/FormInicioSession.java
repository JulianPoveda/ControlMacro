package controlmacro;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Environment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;


import java.io.File;

import async_task.DownLoad;
import async_task.UploadLecturas;
import clases.ClassConfiguracion;
import clases.ClassSession;


public class FormInicioSession extends ActionBarActivity implements OnClickListener{
    public static String path_files_app     = Environment.getExternalStorageDirectory() + File.separator + "ControlMacro";
    public static String name_database      = "ControlMacroBD";
    public static String sub_path_pictures  = "Fotos";

    private Intent              new_form;

    private ClassSession        FcnSession;
    private ClassConfiguracion  FcnCfg;

    private Button      _btnLoggin;
    private EditText    _txtCodigo;
    private TextView    _lblNombre, _lblVersionSoft, _lblVersionBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);

        this.FcnSession     = ClassSession.getInstance(this);
        this.FcnCfg         = ClassConfiguracion.getInstance(this);

        this._btnLoggin     = (Button) findViewById(R.id.LoginBtnIngresar);
        this._txtCodigo     = (EditText) findViewById(R.id.LoginEditTextCodigo);
        this._lblNombre     = (TextView) findViewById(R.id.LoginTxtNombre);
        this._lblVersionBD  = (TextView) findViewById(R.id.LoginTxtVersionBD);
        this._lblVersionSoft= (TextView) findViewById(R.id.LoginTxtVersionSoft);

        this._lblVersionBD.setText("Version BD "+this.FcnCfg.getVersion_bd());
        this._lblVersionSoft.setText("Version Software "+this.FcnCfg.getVersion_bd());

        invalidateOptionsMenu();
        this._btnLoggin.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio_session, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.InicioCargarParametros).setEnabled(this.FcnSession.isInicio_sesion());
        menu.findItem(R.id.InicioCargarNodos).setEnabled(this.FcnSession.isInicio_sesion());
        menu.findItem(R.id.InicioVerNodos).setEnabled(this.FcnSession.isInicio_sesion());
        menu.findItem(R.id.InicioCrearBackup).setEnabled(this.FcnSession.isInicio_sesion());
        menu.findItem(R.id.InicioConfiguracion).setEnabled(this.FcnSession.isInicio_sesion());

        this._txtCodigo.setEnabled(!this.FcnSession.isInicio_sesion());
        this._btnLoggin.setEnabled(!this.FcnSession.isInicio_sesion());
        this._lblNombre.setText(this.FcnSession.getNombre());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.InicioCargarParametros:
                //new DownLoad(this).execute(this.FcnSession.getCodigo()+"");
                new DownLoad(this).execute(this.FcnSession.getCodigo()+"","Parametros");
                break;

            case R.id.InicioCargarNodos:
                new DownLoad(this).execute(this.FcnSession.getCodigo()+"","Trabajo");
                break;

            case R.id.InicioVerNodos:
                this.new_form = new Intent(this, FormInformacionRutas.class);
                //this.new_form.putExtra("FolderAplicacion",Environment.getExternalStorageDirectory() + File.separator + "TomaLecturas");
                startActivity(this.new_form);
                break;

            case R.id.InicioConfiguracion:
                this.new_form = new Intent(this, FormConfiguracion.class);
                startActivity(this.new_form);
                break;

            case R.id.InicioCrearBackup:
                break;

            case R.id.InicioMenuSalir:
                this.FcnSession.IniciarSession(-1);
                invalidateOptionsMenu();
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.LoginBtnIngresar:
                if(!this._txtCodigo.getText().toString().isEmpty()){
                    this.FcnSession.IniciarSession(Integer.parseInt(this._txtCodigo.getText().toString()));
                }
                invalidateOptionsMenu();
                break;
        }
    }
}
