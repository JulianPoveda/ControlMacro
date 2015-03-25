package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import controlmacro.R;

/**
 * Created by JULIANEDUARDO on 25/03/2015.
 */

public class AdaptadorSixItems extends BaseAdapter {
    protected Context context;
    protected ArrayList<DetalleSixItems> items;
    protected ArrayList<DetalleSixItems> itemsBackup;
    LayoutInflater inflater;


    public AdaptadorSixItems(Context _context, ArrayList<DetalleSixItems> _items){
        this.context        = _context;
        this.itemsBackup    = new ArrayList<DetalleSixItems>();
        this.items          = new ArrayList<DetalleSixItems>();

        this.itemsBackup.addAll(_items);
        this.items.addAll(_items);
        inflater            = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }


    public DetalleSixItems getDataOnFilter(int _position){
        return this.items.get(_position);
    }

    public void Filtrar(int _campo, String _txtFiltro){
        this.items.clear();
        if(_txtFiltro.length() == 0 ){
            this.items.addAll(this.itemsBackup);
        }else{
            String filterString = _txtFiltro.toLowerCase();
            String filterCampo  = "";
            for(int i = 0; i<this.itemsBackup.size(); i++){
                switch(_campo){
                    case 0:
                        filterCampo = this.itemsBackup.get(i).getItem1().toLowerCase();
                        break;

                    case 1:
                        filterCampo = this.itemsBackup.get(i).getItem2().toLowerCase();
                        break;

                    case 2:
                        filterCampo = this.itemsBackup.get(i).getItem3().toLowerCase();
                        break;

                    case 3:
                        filterCampo = this.itemsBackup.get(i).getItem4().toLowerCase();
                        break;

                    case 4:
                        filterCampo = this.itemsBackup.get(i).getItem5().toLowerCase();
                        break;

                    case 5:
                        filterCampo = this.itemsBackup.get(i).getItem6().toLowerCase();
                        break;

                    default:
                        break;
                }
                if(filterCampo.contains(filterString)){
                    this.items.add(this.itemsBackup.get(i));
                }
            }
        }
        //notifyDataSetChanged();
        notifyDataSetChanged();
    }


    /*********************************Finalizacion del Filter********************************/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View v = convertView;
        final ViewHolder holder;
        if(convertView == null){
            holder  = new ViewHolder();
            convertView = inflater.inflate(R.layout.six_items_horizontal,null);
            holder.item1 = (TextView) convertView.findViewById(R.id.sixItem1);
            holder.item2 = (TextView) convertView.findViewById(R.id.sixItem2);
            holder.item3 = (TextView) convertView.findViewById(R.id.sixItem3);
            holder.item4 = (TextView) convertView.findViewById(R.id.sixItem4);
            holder.item5 = (TextView) convertView.findViewById(R.id.sixItem5);
            holder.item6 = (TextView) convertView.findViewById(R.id.sixItem6);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item1.setText(items.get(position).getItem1());
        holder.item2.setText(items.get(position).getItem2());
        holder.item3.setText(items.get(position).getItem3());
        holder.item4.setText(items.get(position).getItem4());
        holder.item5.setText(items.get(position).getItem5());
        holder.item6.setText(items.get(position).getItem6());
        return convertView;
    }

    public class ViewHolder{
        TextView    item1;
        TextView    item2;
        TextView    item3;
        TextView    item4;
        TextView    item5;
        TextView    item6;
    }
}