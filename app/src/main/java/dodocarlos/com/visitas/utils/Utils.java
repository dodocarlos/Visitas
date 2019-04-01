package dodocarlos.com.visitas.utils;

import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by DodoCarlos on 09/03/2017.
 */

public class Utils {

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    public static ArrayList<Client> orderClientesByAddressAlphabetic(ArrayList<Client> clients){
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client cli1, Client cli2) {
                return cli1.getEndereco().compareToIgnoreCase(cli2.getEndereco());
            }
        });

        return clients;
    }

}
