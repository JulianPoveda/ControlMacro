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

import controlmacro.R;


public class DialogDeleteUsuario extends Activity implements OnClickListener {
    private Button _aceptar, _cancelar;
    private EditText _txtNewNodo, _txtPoste, _txtLectura, _txtObservacion;


    public void finish(boolean _caso) {
        Intent data = new Intent();
        data.putExtra("response", _caso);
        data.putExtra("new_nodo", _txtNewNodo.getText().toString().toUpperCase());
        data.putExtra("poste", _txtPoste.getText().toString());
        data.putExtra("lectura", _txtLectura.getText().toString());
        data.putExtra("observacion", _txtObservacion.getText().toString());
        setResult(RESULT_OK, data);
        super.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_usuario);

        Bundle bundle = getIntent().getExtras();

        _txtNewNodo     = (EditText) findViewById(R.id.DialogDeleteUserTxtNewNodo);
        _txtPoste       = (EditText) findViewById(R.id.DialogDeleteUserTxtPoste);
        _txtLectura     = (EditText) findViewById(R.id.DialogDeleteUserTxtLectura);
        _txtObservacion = (EditText) findViewById(R.id.DialogDeleteUserTxtObservacion);

        _aceptar = (Button) findViewById(R.id.DialogDeleteUserBtnAceptar);
        _cancelar = (Button) findViewById(R.id.DialogDeleteUserBtnCancelar);


        _aceptar.setOnClickListener(this);
        _cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DialogDeleteUserBtnAceptar:
                finish(true);
                break;

            case R.id.DialogDeleteUserBtnCancelar:
                finish(false);
                break;
        }
    }
}