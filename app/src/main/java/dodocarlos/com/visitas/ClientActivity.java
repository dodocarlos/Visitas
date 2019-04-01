package dodocarlos.com.visitas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;

import dodocarlos.com.visitas.utils.Client;
import dodocarlos.com.visitas.utils.Vars;

public class ClientActivity extends AppCompatActivity {

    private Client client;

    public Client getClient(){
        return this.client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for(Client c : Vars.clients){
            if(c.getCodigo().equals(getIntent().getExtras().getString("clientCode"))){
                this.client = c;
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        updateData();

        //Botao visitado
        Button visited = (Button) findViewById(R.id.ButtonVisited);
        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getThis());
                alertBuilder.setMessage("Deseja confirmar a visita?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.db.setData(getClient(), DateTime.now().getMillis());
                        getClient().setData();

                        //Visitado a
                        TextView textVisitadoTempo = (TextView) findViewById(R.id.textVisitadoa);

                        //Status
                        ImageView imgView = (ImageView) findViewById(R.id.imageViewVisitadoInfo);

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
                        dialog.cancel();
                    }
                });

                alertBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        });

        //Botao remover
        Button remove = (Button) findViewById(R.id.buttonDelete);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getThis());
                alertBuilder.setMessage("Deseja remover este cliente?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.db.removeClient(client);
                        Vars.clients.remove(client);
                        finish();
                        dialog.cancel();
                    }
                });

                alertBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        });

        //Botao editar
        Button edit = (Button) findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ClientEditActivity.class);
                intent.putExtra("clientCode", client.getCodigo());
                startActivity(intent);
            }
        });

    }

    public void updateData(){
        for(Client c : Vars.clients){
            if(c.getCodigo().equals(getIntent().getExtras().getString("clientCode"))){
                this.client = c;
            }
        }

        //Codigo
        TextView codigo = (TextView) findViewById(R.id.clientCode);
        codigo.setText("Código: " + client.getCodigo());

        //Nome
        TextView nome = (TextView) findViewById(R.id.clientName);
        nome.setText("Nome: " + getClient().getNome());

        //Fantasia
        TextView fantasia = (TextView) findViewById(R.id.clientFantasia);
        fantasia.setText("Fantasia: " + getClient().getFantasia());

        //Cidade
        TextView cidade = (TextView) findViewById(R.id.clientCity);
        cidade.setText("Cidade: " + getClient().getCidade());

        //Bairro
        TextView bairro = (TextView) findViewById(R.id.clientBairro);
        bairro.setText("Bairro: " + getClient().getBairro());

        //Endereço
        TextView endereco = (TextView) findViewById(R.id.clientAdress);
        endereco.setText("Endereço: " + getClient().getEndereco());

        //Numero
        TextView numero = (TextView) findViewById(R.id.clientNum);
        numero.setText("Número: " + getClient().getNumero());

        //Contato
        TextView contato = (TextView) findViewById(R.id.clientContact);
        contato.setText("Contato: " + getClient().getContato());

        //Telefone1
        TextView telefone1 = (TextView) findViewById(R.id.clientPhone1);
        telefone1.setText("Telefone 1: " + getClient().getTelefone1());

        //Telefone2
        TextView telefone2 = (TextView) findViewById(R.id.clientPhone2);
        telefone2.setText("Telefone 2: " + getClient().getTelefone2());

        //Visitado a
        TextView textVisitadoTempo = (TextView) findViewById(R.id.textVisitadoa);

        //Status
        ImageView imgView = (ImageView) findViewById(R.id.imageViewVisitadoInfo);

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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateData();
    }

    public ClientActivity getThis(){
        return this;
    }

}
