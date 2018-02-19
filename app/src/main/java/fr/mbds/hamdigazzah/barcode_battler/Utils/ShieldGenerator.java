package fr.mbds.hamdigazzah.barcode_battler.Utils;

import android.util.Log;

import java.util.ArrayList;

import fr.mbds.hamdigazzah.barcode_battler.Model.Shield;

/**
 * Created by hamdigazzah on 09/02/2018.
 */

public class ShieldGenerator {

    private ArrayList<Shield> shieldArrayList = new ArrayList<Shield>() {{
        add(new Shield(0, "Shield0", "1234",  5, "shield0"));
        add(new Shield(1, "Shield1", "1234",  10, "shield1"));
        add(new Shield(2, "Shield2", "1234",  7, "shield2"));
        add(new Shield(3, "Shield3", "1234",  2, "shield3"));
        add(new Shield(4, "Shield4", "1234",  6, "shield4"));
        add(new Shield(5, "Shield5", "1234",  8, "shield5"));
    }};

    public Shield generate(String barCode) {
        int somme = 0;
        for (int i = 0; i < barCode.length(); i++) {
            somme += java.lang.Character.getNumericValue(barCode.charAt(i));
        }
        Log.d("barcode", barCode);
        Log.d("somme", String.valueOf(somme));
        int id = somme % shieldArrayList.size();
        Log.d("id", String.valueOf(id));
        shieldArrayList.get(id).setCode(barCode);
        return shieldArrayList.get(id);
    }
}

