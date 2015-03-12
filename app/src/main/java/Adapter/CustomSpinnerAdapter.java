package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import controlmacro.R;

/**
 * Created by JULIANEDUARDO on 12/03/2015.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String>{
    private final Context           ctx;
    private final int               idResource;
    private final ArrayList<String> arrayInformacion;

    private final String            colorText;
    private final String            colorBack;


    private CustomSpinnerAdapter(BuilderSpinnerAdapter spinner){
        super(spinner.ctx, spinner.idResource, spinner.arrayInformacion);
        this.ctx                = spinner.ctx;
        this.idResource         = spinner.idResource;
        this.arrayInformacion   = spinner.arrayInformacion;
        this.colorText          = spinner.colorText;
        this.colorBack          = spinner.colorBack;
    }

    public Context getCtx() {
        return ctx;
    }

    public String getColorText() {
        return colorText;
    }

    public String getColorBack() {
        return colorBack;
    }


    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int _position, View _convertView, ViewGroup _parent){
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View        mySpinner = inflater.inflate(R.layout.custom_spinner, _parent, false);
        TextView txtSpinner= (TextView) mySpinner.findViewById(R.id.custom_text_spinner);
        txtSpinner.setText(this.arrayInformacion.get(_position));
        txtSpinner.setTextColor(Color.parseColor(this.colorText));
        txtSpinner.setBackgroundColor(Color.parseColor(this.colorBack));
        return mySpinner;
    }



    public static class BuilderSpinnerAdapter{
        public final Context            ctx;
        public int                      idResource;
        public final ArrayList<String>  arrayInformacion;
        public String                   colorText = "#1C1717";
        public String                   colorBack = "#FFFFFF";


        public BuilderSpinnerAdapter(Context _ctx, int _idResource, ArrayList<String> _arrayInformacion){
            this.ctx                = _ctx;
            this.idResource         = _idResource;
            this.arrayInformacion   = _arrayInformacion;
        }


        public BuilderSpinnerAdapter colorText(String _colorText){
            this.colorText  = _colorText;
            return this;
        }


        public BuilderSpinnerAdapter colorBack(String _colorBack){
            this.colorBack  = _colorBack;
            return this;
        }


        public CustomSpinnerAdapter build(){
            return new CustomSpinnerAdapter(this);
        }

    }

}
