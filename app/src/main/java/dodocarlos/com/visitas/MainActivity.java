package dodocarlos.com.visitas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import dodocarlos.com.visitas.utils.CitySpinnerAdapter;
import dodocarlos.com.visitas.utils.Client;
import dodocarlos.com.visitas.utils.ClientListAdapter;
import dodocarlos.com.visitas.utils.DB;
import dodocarlos.com.visitas.utils.Utils;
import dodocarlos.com.visitas.utils.Vars;

public class MainActivity extends AppCompatActivity {

    public static DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Save database if not exits
        try {
            File f = new File(this.getFilesDir() + "/database.sqlite");
            if(!f.exists()){
                System.out.println("Criando database padrao");

//                BufferedReader reader = new BufferedReader(new InputStreamReader(this.getAssets().open("database.sqlite")));

//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));

                InputStream inputStream = this.getAssets().open("database.sqlite");
                OutputStream outputStream = new FileOutputStream(f);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                System.out.println("Salvo");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Initialize db connection
        db = new DB(this.getFilesDir() + "/database.sqlite");
//        Get all clients
        Vars.clients = db.getAllClients();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vars.cidades.add("Todas");
        if(Vars.cidadeSelecionada == null){
            Vars.cidadeSelecionada = "todas";
        }

        Spinner s = (Spinner) findViewById(R.id.spinner);
        updateSpinner();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cidade = String.valueOf(parent.getItemAtPosition(position));
                Vars.cidadeSelecionada = cidade;
                Vars.cidadeSelecionadaIndex = position;
                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //Initialize adapter
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.color.optionColor);

                try{
                    Client c = (Client) parent.getItemAtPosition(position);
                    Intent i = new Intent(getBaseContext(), ClientActivity.class);
                    i.putExtra("clientCode", c.getCodigo());
                    startActivity(i);
                }catch (Exception ex){ex.printStackTrace();}
            }
        });

        Utils.orderClientesByAddressAlphabetic(Vars.clients);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateListView();
        updateSpinner();
    }

    public void updateSpinner(){
        Vars.cidades.clear();
        Vars.cidades.add("Todas");

        Vars.clients = db.getAllClients();

        for(Client c : Vars.clients){
            String cidade = Utils.upperCaseFirst(Utils.removerAcentos(c.getCidade().toLowerCase()));
            if(!Vars.cidades.contains(cidade)){
                Vars.cidades.add(cidade);
            }
        }

        Spinner s = (Spinner) findViewById(R.id.spinner);
        s.setAdapter(new CitySpinnerAdapter(this, Vars.cidades));

        s.setSelection(Vars.cidadeSelecionadaIndex);
    }

    public void updateListView(){
        if(Vars.cidadeSelecionada.equalsIgnoreCase("todas")){
            ListView lv = (ListView) findViewById(R.id.listView1);
            lv.setAdapter(null);
            lv.setAdapter(new ClientListAdapter(getApplicationContext(), Vars.clients));
        }else{
            Vars.tempClientList.clear();
            for(Client c : Vars.clients){
                if(Utils.removerAcentos(c.getCidade()).equalsIgnoreCase(Vars.cidadeSelecionada)){
                    Vars.tempClientList.add(c);
                }
            }

            Utils.orderClientesByAddressAlphabetic(Vars.tempClientList);

            ListView lv = (ListView) findViewById(R.id.listView1);
            lv.setAdapter(null);
            lv.setAdapter(new ClientListAdapter(getApplicationContext(), Vars.tempClientList));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_client) {
            Intent intent = new Intent(getBaseContext(), AddClientActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
