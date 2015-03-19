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


public class DialogConfirm extends Activity implements OnClickListener{
    private Button _aceptar, _cancelar;
    private TextView _lbl1, _lblTitulo;


    public void finish(boolean _caso) {
        Intent data = new Intent();
        data.putExtra("response", _caso);
        setResult(RESULT_OK, data);
        super.finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);

        Bundle bundle 	= getIntent().getExtras();

        _aceptar = (Button) findViewById(R.id.DialogConfirmBtnAceptar);
        _cancelar= (Button) findViewById(R.id.DialogConfirmBtnCancelar);

        _lblTitulo = (TextView) findViewById(R.id.DialogConfirmTitulo);
        _lbl1 = (TextView) findViewById(R.id.DialogConfirmLbl1);

        _lbl1.setText(bundle.getString("lbl1"));
        _lblTitulo.setText(bundle.getString("titulo"));

        _aceptar.setOnClickListener(this);
        _cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.DialogConfirmBtnAceptar:
                finish(true);
                break;

            case R.id.DialogConfirmBtnCancelar:
                finish(false);
                break;
        }
    }
}