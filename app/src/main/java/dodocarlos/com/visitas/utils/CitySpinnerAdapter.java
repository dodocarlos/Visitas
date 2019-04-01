package dodocarlos.com.visitas.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import dodocarlos.com.visitas.R;

/**
 * Created by DodoCarlos on 09/03/2017.
 */

public class CitySpinnerAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> cidades;

    public CitySpinnerAdapter(Context context, ArrayList<String> cidades){
        this.context = context;
        this.cidades = cidades;
    }

    @Override
    public int getCount() {
        return cidades.size();
    }

    @Override
    public Object getItem(int position) {
        return cidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cidade = String.valueOf(getItem(position));

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_adapter, null);

        //TextView
        TextView textView = (TextView) view.findViewById(R.id.textViewCidadeSpinner);
        textView.setText(cidade.replaceFirst(String.valueOf(cidade.charAt(0)), String.valueOf(cidade.charAt(0)).toUpperCase()));

        return view;
    }
}
