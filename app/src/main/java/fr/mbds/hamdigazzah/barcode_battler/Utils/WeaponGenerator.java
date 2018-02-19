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
        add(new Weapon(0, "Weapon0", "1234",  5, "weapon0"));
        add(new Weapon(1, "Weapon1", "1234",  10, "weapon1"));
        add(new Weapon(2, "Weapon2", "1234",  7, "weapon2"));
        add(new Weapon(3, "Weapon3", "1234",  15, "weapon3"));
        add(new Weapon(4, "Weapon4", "1234",  18, "weapon4"));
        add(new Weapon(5, "Weapon5", "1234",  13, "weapon5"));
        add(new Weapon(6, "Weapon6", "1234",  13, "weapon6"));
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
