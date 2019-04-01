package dodocarlos.com.visitas.utils;

import org.joda.time.DateTime;

import java.security.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Created by DodoCarlos on 08/03/2017.
 */

public class Client {

    private String codCli, nomCli, fanCli, cidCli, endCli, numCli, baiCli, conCli, te1Cli, te2Cli, dataVis;

    public Client(String codCli, String nomCli, String fanCli, String endCli,
                  String numCli, String baiCli, String cidCli, String conCli,
                  String te1Cli, String te2Cli, String dataVis){

        this.codCli = codCli;
        this.nomCli = nomCli;
        this.fanCli = fanCli;
        this.cidCli = cidCli;
        this.endCli = endCli;
        this.numCli = numCli;
        this.baiCli = baiCli;
        this.conCli = conCli;
        this.te1Cli = te1Cli;
        this.te2Cli = te2Cli;
        this.dataVis = dataVis;

    }

    public String getCodigo(){
        return codCli != null ? codCli : "";
    }

    public String getNome() {
        return nomCli != null ? nomCli : "";
    }

    public String getFantasia() {
        return fanCli != null ? fanCli : "";
    }

    public String getCidade() {
        return cidCli != null ? cidCli : "";
    }

    public String getEndereco() {
        return endCli != null ? endCli : "";
    }

    public String getNumero() {
        return numCli != null ? numCli : "";
    }

    public String getBairro() {
        return baiCli != null ? baiCli : "";
    }

    public String getContato() {
        return conCli != null ? conCli : "";
    }

    public String getTelefone1() {
        return te1Cli != null ? te1Cli : "";
    }

    public String getTelefone2() {
        return te2Cli != null ? te2Cli : "";
    }

    public DateTime getDataVis(){
        if(dataVis != null && !dataVis.equals("")){
            return new DateTime(Long.valueOf(dataVis));
        }
        return null;
    }

    public void setData(){
        this.dataVis = String.valueOf(DateTime.now().getMillis() - 1000);
    }

}
