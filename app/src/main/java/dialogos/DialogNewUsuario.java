package dialogos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import controlmacro.R;


public class DialogNewUsuario extends Activity implements OnClickListener {
    private Button _btnAceptar, _btnCancelar;
    private EditText _txtMarca, _txtSerie, _txtCuenta, _txtNombre, _txtDireccion;


    public void finish(boolean _caso) {
        Intent data = new Intent();
        data.putExtra("response", _caso);
        data.putExtra("marca", this._txtMarca.getText().toString());
        data.putExtra("serie", this._txtSerie.getText().toString());
        data.putExtra("cuenta", this._txtCuenta.getText().toString());
        data.putExtra("nombre", this._txtNombre.getText().toString());
        data.putExtra("direccion", this._txtDireccion.getText().toString());
        setResult(RESULT_OK, data);
        super.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_usuario);

        Bundle bundle = getIntent().getExtras();

        this._txtMarca      = (EditText) findViewById(R.id.DialogNewUserTxtMedidor);
        this._txtSerie      = (EditText) findViewById(R.id.DialogNewUserTxtSerie);
        this._txtCuenta     = (EditText) findViewById(R.id.DialogNewUserTxtCuenta);
        this._txtNombre     = (EditText) findViewById(R.id.DialogNewUserTxtNombre);
        this._txtDireccion  = (EditText) findViewById(R.id.DialogNewUserTxtDireccion);

        this._btnAceptar    = (Button) findViewById(R.id.DialogNewUserBtnAceptar);
        this._btnCancelar   = (Button) findViewById(R.id.DialogNewUserBtnCancelar);


        this._btnAceptar.setOnClickListener(this);
        this._btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DialogNewUserBtnAceptar:
                finish(true);
                break;

            case R.id.DialogNewUserBtnCancelar:
                finish(false);
                break;
        }
    }
}