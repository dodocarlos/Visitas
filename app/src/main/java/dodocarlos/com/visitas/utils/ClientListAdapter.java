package dodocarlos.com.visitas.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dodocarlos.com.visitas.R;

/**
 * Created by DodoCarlos on 09/03/2017.
 */

public class ClientListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Client> clientList;

    public ClientListAdapter(Context context, ArrayList<Client> clientList){
        this.context = context;
        this.clientList = clientList;
    }

    public void setClientList(ArrayList<Client> clients){
        this.clientList.clear();
        this.clientList = clients;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Client client = clientList.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_clients_adapter, null);

        //Nome
        TextView textNome = (TextView) view.findViewById(R.id.textNome);
        textNome.setText(client.getNome());

        //Fantasia
        TextView textFantasia = (TextView) view.findViewById(R.id.textFantasia);
        textFantasia.setText("(" + client.getFantasia() + ")");

        //Endereco
        TextView textEndereco = (TextView) view.findViewById(R.id.textEndereco);
        textEndereco.setText(client.getEndereco() + ", " + client.getNumero());

        //Visitado a
        TextView textVisitadoTempo = (TextView) view.findViewById(R.id.textVisitadoTempo);

        //Status
        ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

        DateTime data = client.getDataVis();
        if(data != null){
            Days days = Days.daysBetween(data, DateTime.now());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            textVisitadoTempo.setText("Visitado a: " + days.getDays() + " dias (" + sdf.format(data.toDate()) + ")");

            //Define status
            if(days.getDays() <= 20){
                imgView.setImageResource(R.mipmap.circulo_verde);
            }else if(days.getDays() > 20 && days.getDays() < 30){
                imgView.setImageResource(R.mipmap.circulo_laranja);
            }else{
                imgView.setImageResource(R.mipmap.circulo_vermelho);
            }
        }else{
            textVisitadoTempo.setText("Visitado a: 00/00/0000");
            imgView.setImageResource(R.mipmap.circulo_vermelho);
        }

        return view;
    }
}
