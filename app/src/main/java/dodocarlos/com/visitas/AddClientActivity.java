package dodocarlos.com.visitas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dodocarlos.com.visitas.utils.Client;
import dodocarlos.com.visitas.utils.Vars;

/**
 * Created by DodoCarlos on 13/03/2017.
 */

public class AddClientActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        //Client_code
        TextView code = (TextView) findViewById(R.id.clientCodeEdit);
        code.setText("Código: " + String.valueOf(Vars.nextCliCode));

        //Salvar
        Button salvar = (Button) findViewById(R.id.buttonSaveEdit);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getThis());
              alertBuilder.setMessage("Deseja salvar este cliente?");
              alertBuilder.setCancelable(true);

              alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      //Nome_edit
                      EditText name = (EditText) findViewById(R.id.editTextNomeEdit);

                      //Fantasia_edit
                      EditText fantasia = (EditText) findViewById(R.id.editTextFantasiaEdit);

                      //Cidade_edit
                      EditText cidade = (EditText) findViewById(R.id.editTextCidadeEdit);

                      //Bairro_edit
                      EditText bairro = (EditText) findViewById(R.id.editTextBairroEdit);

                      //Endereco_edit
                      EditText endereco = (EditText) findViewById(R.id.editTextEnderecoEdit);

                      //Numero_edit
                      EditText numero = (EditText) findViewById(R.id.editTextNumeroEdit);

                      //Contato_edit
                      EditText contato = (EditText) findViewById(R.id.editTextContatoEdit);

                      //Telefone1_edit
                      EditText telefone1 = (EditText) findViewById(R.id.editTextTelefone1Edit);

                      //Telefone2_edit
                      EditText telefone2 = (EditText) findViewById(R.id.editTextTelefone2Edit);
                      Client newClient = new Client(String.valueOf(Vars.nextCliCode), name.getText().toString(), fantasia.getText().toString(), endereco.getText().toString()
                              , numero.getText().toString(), bairro.getText().toString(), cidade.getText().toString(), contato.getText().toString()
                              , telefone1.getText().toString(), telefone2.getText().toString(), "");

                      MainActivity.db.addClient(newClient);
                      Vars.clients = MainActivity.db.getAllClients();

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
            }

        );

        //Cancelar
        Button cancel = (Button) findViewById(R.id.buttonCancelEdit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getThis());
                alertBuilder.setMessage("Deseja sair sem salvar?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    }

    public AddClientActivity getThis(){
        return this;
    }

}
