package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
    protected ArrayList<DetalleRedesPoste> detallePoste;
    LayoutInflater inflater;

    private final String item;
    private final String gpsLat;
    private final String gpsLong;
    private final ArrayList<String> tipo;
    private final String compartido;
    private final ArrayList<String> estado;
    private final ArrayList<String> material;
    private final ArrayList<String> altura;
    private final ArrayList<String> estructura;
    private final String observaciones;

    private ArrayAdapter<String> adaptadorTipo;
    private ArrayAdapter<String> adaptadorEstado;
    private ArrayAdapter<String> adaptadorMaterial;
    private ArrayAdapter<String> adaptadorAltura;
    private ArrayAdapter<String> adaptadorEstructura;

    private  AdaptadorRedesPoste(BuilderAdaptadorRedesPoste fila){
        this.context        =   fila.ctx;
        this.item           =   fila.item;
        this.gpsLat         =   fila.gpsLat;
        this.gpsLong        =   fila.gpsLong;
        this.tipo           =   fila.tipo;
        this.compartido     =   fila.compartido;
        this.estado         =   fila.estado;
        this.material       =   fila.material;
        this.estructura     =   fila.estructura;
        this.observaciones  =   fila.observaciones;
        this.altura         =   fila.altura;

        this.adaptadorTipo          = new ArrayAdapter<String>(this.context,android.R.layout.simple_list_item_1,this.tipo);
        this.adaptadorEstado        = new ArrayAdapter<String>(this.context,android.R.layout.simple_list_item_1,this.estado);
        this.adaptadorMaterial      = new ArrayAdapter<String>(this.context,android.R.layout.simple_list_item_1,this.material);
        this.adaptadorAltura        = new ArrayAdapter<String>(this.context,android.R.layout.simple_list_item_1,this.altura);
        this.adaptadorEstructura    = new ArrayAdapter<String>(this.context,android.R.layout.simple_list_item_1,this.estructura);

        //inflater            = LayoutInflater.from(this.context);
    }

    public String getItem() {
        return item;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public String getGpsLong() {
        return gpsLong;
    }

    public ArrayList<String> getTipo() {
        return tipo;
    }

    public String getCompartido() {
        return compartido;
    }

    public ArrayList<String> getEstado() {
        return estado;
    }

    public ArrayList<String> getMaterial() {
        return material;
    }

    public ArrayList<String> getAltura() {
        return altura;
    }

    public ArrayList<String> getEstructura() {
        return estructura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public static class BuilderAdaptadorRedesPoste{
        public Context ctx;
        public String item="";
        public String gpsLat="";
        public String gpsLong="";
        public ArrayList<String> tipo = new ArrayList<String>();
        public String compartido="";
        public ArrayList<String> estado = new ArrayList<String>();
        public ArrayList<String> material = new ArrayList<String>();
        public ArrayList<String> altura = new ArrayList<String>();
        public ArrayList<String> estructura = new ArrayList<String>();
        public String observaciones = "";


        public BuilderAdaptadorRedesPoste(Context  _ctx){
            this.ctx = _ctx;
        }

        public AdaptadorRedesPoste build(){
            return new AdaptadorRedesPoste(this);
        }

        public BuilderAdaptadorRedesPoste setItem(String item) {
            this.item = item;
            return this;
        }

        public BuilderAdaptadorRedesPoste setGpsLat(String gpsLat) {
            this.gpsLat = gpsLat;
            return this;
        }

        public BuilderAdaptadorRedesPoste setGpsLong(String gpsLong) {
            this.gpsLong = gpsLong;
            return this;
        }

        public BuilderAdaptadorRedesPoste setTipo(ArrayList<String> tipo) {
            this.tipo = tipo;
            return this;
        }

        public BuilderAdaptadorRedesPoste setCompartido(String compartido) {
            this.compartido = compartido;
            return this;
        }

        public BuilderAdaptadorRedesPoste setEstado(ArrayList<String> estado) {
            this.estado = estado;
            return this;
        }

        public BuilderAdaptadorRedesPoste setMaterial(ArrayList<String> material) {
            this.material = material;
            return this;
        }

        public BuilderAdaptadorRedesPoste setAltura(ArrayList<String> altura) {
            this.altura = altura;
            return this;
        }

        public BuilderAdaptadorRedesPoste setEstructura(ArrayList<String> estructura) {
            this.estructura = estructura;
            return this;
        }

        public BuilderAdaptadorRedesPoste setObservaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }
    }

    @Override
    public int getCount()
    {
        return detallePoste.size();
    }

    @Override
    public Object getItem(int position)
    {
        return detallePoste.get(position);
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
        //View v = convertView;
        final ViewHolder holder;
        if(convertView == null){
            holder  = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_redes_poste,null);
            holder.itemPoste        = (EditText) convertView.findViewById(R.id.EditItemRedesPoste);
            holder.gpsLatPoste      = (EditText) convertView.findViewById(R.id.EditLatRedesPoste);
            holder.gpsLongPoste     = (EditText) convertView.findViewById(R.id.EditLongRedesPoste);
            holder.compartido       = (EditText) convertView.findViewById(R.id.EditCompartidoRedesPoste);
            holder.altPoste         = (Spinner) convertView.findViewById(R.id.SpinnerAlturaRedesPoste);
            holder.estPoste         = (Spinner) convertView.findViewById(R.id.SpinnerEstructuraRedesPoste);
            holder.obsPoste         = (EditText) convertView.findViewById(R.id.EditObsRedesPoste);
            holder.tipoPoste        = (Spinner) convertView.findViewById(R.id.SpinnerTipoRedesPoste);
            holder.estadoPoste      = (Spinner)convertView.findViewById(R.id.SpinnerEstadoRedesPoste);
            holder.materialPoste    = (Spinner)convertView.findViewById(R.id.SpinnerMaterialRedesPoste);

            holder.itemPoste.setText(this.item);
            holder.gpsLatPoste.setText(this.gpsLat);
            holder.gpsLongPoste.setText(this.gpsLong);
            holder.compartido.setText(this.compartido);
            holder.obsPoste.setText(this.observaciones);

            holder.altPoste.setAdapter(this.adaptadorAltura);
            holder.estPoste.setAdapter(this.adaptadorEstructura);
            holder.tipoPoste.setAdapter(this.adaptadorTipo);
            holder.estadoPoste.setAdapter(this.adaptadorEstado);
            holder.materialPoste.setAdapter(this.adaptadorMaterial);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{

        EditText itemPoste;
        EditText gpsLongPoste;
        EditText gpsLatPoste;
        Spinner tipoPoste;
        EditText compartido;
        Spinner estadoPoste;
        Spinner materialPoste;
        Spinner altPoste;
        Spinner estPoste;
        EditText obsPoste;
    }

}
