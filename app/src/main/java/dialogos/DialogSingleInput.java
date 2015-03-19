package dialogos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import controlmacro.R;


public class DialogSingleInput extends Activity implements OnClickListener{
    String _parametros[];
    private Button _aceptar, _cancelar;
    private TextView _lbl1, _lblTitulo;
    private EditText _txt1;


    public void finish(boolean _caso) {
        Intent data = new Intent();
        data.putExtra("txt1", _txt1.getText().toString());
        data.putExtra("response", _caso);
        setResult(RESULT_OK, data);
        super.finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_single_input);

        Bundle bundle 	= getIntent().getExtras();

        _aceptar = (Button) findViewById(R.id.DialogSingleBtnAceptar);
        _cancelar= (Button) findViewById(R.id.DialogSingleBtnCancelar);

        _lblTitulo = (TextView) findViewById(R.id.DialogSingleTitulo);
        _lbl1 = (TextView) findViewById(R.id.DialogSingleLbl1);
        _txt1 = (EditText) findViewById(R.id.DialogSingleTxt1);

        _lbl1.setText(bundle.getString("lbl1"));
        _txt1.setText(bundle.getString("txt1"));
        _lblTitulo.setText(bundle.getString("titulo"));

        _aceptar.setOnClickListener(this);
        _cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.DialogSingleBtnAceptar:
                finish(true);
                break;

            case R.id.DialogSingleBtnCancelar:
                finish(false);
                break;
        }
    }
}