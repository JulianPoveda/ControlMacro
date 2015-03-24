package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import controlmacro.R;

/**
 * Created by sypelcdesarrollo on 19/03/15.
 */
public class AdaptadorRedesPoste extends BaseAdapter {
    protected Context context;
    protected ArrayList<DetalleRedesPoste> infPostes;
    LayoutInflater inflater;


    private  AdaptadorRedesPoste(BuilderAdaptadorRedesPoste fila){
        this.context    = fila.ctx;
        this.infPostes  = fila.infPostes;
        inflater        = LayoutInflater.from(this.context);
    }

    public static class BuilderAdaptadorRedesPoste{
        public Context ctx;
        public ArrayList<DetalleRedesPoste> infPostes;


        public BuilderAdaptadorRedesPoste(Context  _ctx, ArrayList<DetalleRedesPoste> _infPostes){
            this.ctx        = _ctx;
            this.infPostes  = _infPostes;
        }


        public AdaptadorRedesPoste build(){
            return new AdaptadorRedesPoste(this);
        }
    }

    @Override
    public int getCount()
    {
        return infPostes.size();
    }

    @Override
    public Object getItem(int position)
    {
        return infPostes.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder  = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_redes_poste,null);
            holder.itemPoste        = (TextView) convertView.findViewById(R.id.RedesPosteLblItem);
            holder.gpsLatPoste      = (TextView) convertView.findViewById(R.id.RedesPosteLblLat);
            holder.gpsLongPoste     = (TextView) convertView.findViewById(R.id.RedesPosteLblLong);
            holder.compartido       = (TextView) convertView.findViewById(R.id.RedesPosteLblCompartido);
            holder.altPoste         = (TextView) convertView.findViewById(R.id.RedesPosteLblAltura);
            holder.estPoste         = (TextView) convertView.findViewById(R.id.RedesPosteLblEstructura);
            holder.tipoPoste        = (TextView) convertView.findViewById(R.id.RedesPosteLblTipo);
            holder.estadoPoste      = (TextView) convertView.findViewById(R.id.RedesPosteLblEstado);
            holder.materialPoste    = (TextView) convertView.findViewById(R.id.RedesPosteLblMaterial);
            holder.obsPoste         = (TextView) convertView.findViewById(R.id.RedesPosteLblObservacion);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.itemPoste.setText(this.infPostes.get(position).getItemPoste());
        holder.gpsLatPoste.setText(this.infPostes.get(position).getGpsLat());
        holder.gpsLongPoste.setText(this.infPostes.get(position).getGpsLong());
        holder.compartido.setText(this.infPostes.get(position).getCompartido());
        holder.altPoste.setText(this.infPostes.get(position).getAlturaPoste());
        holder.estPoste.setText(this.infPostes.get(position).getEstructuraPoste());
        holder.tipoPoste.setText(this.infPostes.get(position).getTipoPoste());
        holder.estadoPoste.setText(this.infPostes.get(position).getEstadoPoste());
        holder.materialPoste.setText(this.infPostes.get(position).getMaterialPoste());
        holder.obsPoste.setText(this.infPostes.get(position).getObservacionesPoste());
        return convertView;
    }

    public class ViewHolder{
        TextView    itemPoste;
        TextView    gpsLongPoste;
        TextView    gpsLatPoste;
        TextView    tipoPoste;
        TextView    compartido;
        TextView    estadoPoste;
        TextView    materialPoste;
        TextView    altPoste;
        TextView    estPoste;
        TextView    obsPoste;
    }
}
