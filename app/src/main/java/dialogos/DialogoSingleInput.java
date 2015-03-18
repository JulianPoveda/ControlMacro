package dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import clases.ClassTomaLectura;
import controlmacro.FormInicioSession;
import controlmacro.FormTomarLectura;
import controlmacro.R;
import sistema.SQLite;

/**
 * Created by JULIANEDUARDO on 18/03/2015.
 */
public class DialogoSingleInput{
    private ClassTomaLectura    FcnLectura;
    private TextView            _lblInput1;
    private EditText            _txtInput1;



    public void DialogoSingleInput(Context _ctx, String _titulo, String _lbl1) {
        this.FcnLectura = new ClassTomaLectura(_ctx);
        LayoutInflater linf = LayoutInflater.from(_ctx);
        View inflator = linf.inflate(R.layout.dialog_single_input, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(_ctx);

        alert.setTitle(_titulo);
        alert.setView(inflator);

        this._lblInput1     = (TextView) inflator.findViewById(R.id.DialogSingleLbl1);
        this._txtInput1     = (EditText) inflator.findViewById(R.id.DialogSingleTxt1);

        this._lblInput1.setText(_lbl1);

        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton){
                FcnLectura.ConfirmacionLectura(_txtInput1.getText().toString());
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }
}
