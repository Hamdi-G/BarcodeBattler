package fr.mbds.hamdigazzah.barcode_battler.Utils;

import android.util.Log;

import java.util.ArrayList;

import fr.mbds.hamdigazzah.barcode_battler.Model.Character;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;

/**
 * Created by hamdigazzah on 08/02/2018.
 */

public class WeaponGenerator {

    private ArrayList<Weapon> weaponArrayList = new ArrayList<Weapon>() {{
        add(new Weapon(0, "Gauntlet", "1234",  10, "weapon0"));
        add(new Weapon(1, "Baselard", "1234",  20, "weapon1"));
        add(new Weapon(2, "Emeici", "1234",  14, "weapon2"));
        add(new Weapon(3, "Maduvu", "1234",  25, "weapon3"));
        add(new Weapon(4, "Piandao", "1234",  18, "weapon4"));
        add(new Weapon(5, "Chokutō", "1234",  19, "weapon5"));
        add(new Weapon(6, "Nagamaki", "1234",  25, "Weapon6"));
    }};

    public Weapon generate(String barCode) {
        int somme = 0;
        for (int i = 0; i < barCode.length(); i++) {
            somme += java.lang.Character.getNumericValue(barCode.charAt(i));
        }
        Log.d("barcode", barCode);
        Log.d("somme", String.valueOf(somme));
        int id = somme % weaponArrayList.size();
        Log.d("id", String.valueOf(id));
        weaponArrayList.get(id).setCode(barCode);
        return weaponArrayList.get(id);
    }
}