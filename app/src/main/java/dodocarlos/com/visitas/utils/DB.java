package dodocarlos.com.visitas.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dodocarlos.com.visitas.MainActivity;

/**
 * Created by DodoCarlos on 08/03/2017.
 */

public class DB {

    private static String dbPath;

    private static SQLiteDatabase db;

    public DB(String dbPath) {
        db = SQLiteDatabase.openDatabase(dbPath, null, 0);
    }

    public ArrayList<Client> getAllClients(){
        ArrayList<Client> clients = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Clientes", null);

        while(cursor.moveToNext()){
            if(Integer.valueOf(cursor.getString(0)) >= Vars.nextCliCode) Vars.nextCliCode = Integer.valueOf(cursor.getString(0)) + 1;
            clients.add(new Client(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)));

        }

        return clients;
    }

    public boolean setData(Client client, Long dateLong){
        String dateString = String.valueOf(dateLong);
        try{
            db.execSQL("UPDATE Clientes SET dataVis = ? WHERE codcli = ?", new String[] {dateString, client.getCodigo()});
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean removeClient(Client client){
        try{
            db.execSQL("DELETE FROM Clientes WHERE codcli = ?", new String[] {client.getCodigo()});
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean editClient(Client client){
        try{
//            String codCli, String nomCli, String fanCli, String endCli,
//                    String numCli, String baiCli, String cidCli, String conCli,
//                    String te1Cli, String te2Cli, String dataVis
            db.execSQL("UPDATE Clientes SET nomCli=?, fanCli=?, endCli=?, numCli=?, baiCli=?, cidCli=?, conCli=?, te1Cli=?, te2Cli=? WHERE codcli = ?"
            , new String[] {client.getNome(), client.getFantasia(), client.getEndereco(), client.getNumero(), client.getBairro(), client.getCidade()
            , client.getContato(), client.getTelefone1(), client.getTelefone2(), client.getCodigo()});
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean addClient(Client client){
        try{
//            String codCli, String nomCli, String fanCli, String endCli,
//                    String numCli, String baiCli, String cidCli, String conCli,
//                    String te1Cli, String te2Cli, String dataVis
            db.execSQL("INSERT INTO Clientes (codCli, nomCli, fanCli, endCli, numCli, baiCli, cidCli, conCli, te1Cli, te2Cli, dataVis) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    , new String[] {String.valueOf(Vars.nextCliCode), client.getNome(), client.getFantasia(), client.getEndereco(), client.getNumero(), client.getBairro(), client.getCidade()
                            , client.getContato(), client.getTelefone1(), client.getTelefone2(), ""});
        }catch (Exception ex){
            return false;
        }
        return true;
    }

}
