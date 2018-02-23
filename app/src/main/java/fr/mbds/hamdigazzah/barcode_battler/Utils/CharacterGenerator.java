package fr.mbds.hamdigazzah.barcode_battler.Utils;

import android.util.Log;

import java.util.ArrayList;

import fr.mbds.hamdigazzah.barcode_battler.Model.Character;

/**
 * Created by hamdigazzah on 07/02/2018.
 */

public class CharacterGenerator {

    private ArrayList<Character> characterArrayList = new ArrayList<Character>() {{
        add(new Character(0, "Domitia", "1234",  100, "character0"));
        add(new Character(1, "Oenomaus", "1234",  40, "character1"));
        add(new Character(2, "Hamilcar", "1234",  50, "character2"));
        add(new Character(3, "Segovax", "1234",  60, "character3"));
        add(new Character(4, "Ramel", "1234",  30, "character4"));
        add(new Character(5, "Spartacus", "1234",  100, "character5"));
        add(new Character(6, "Auctus", "1234",  100, "character6"));
        add(new Character(7, "Mercato", "1234",  40, "character7"));
        add(new Character(8, "Lovis", "1234",  40, "character8"));
        add(new Character(9, "Agron", "1234",  40, "character9"));
    }};

    public Character generate(String barCode) {
        int somme = 0;
        for (int i = 0; i < barCode.length(); i++) {
            somme += java.lang.Character.getNumericValue(barCode.charAt(i));
        }
        Log.d("barcode", barCode);
        Log.d("somme", String.valueOf(somme));
        int id = somme % characterArrayList.size();
        Log.d("id", String.valueOf(id));
        characterArrayList.get(id).setCode(barCode);
        return characterArrayList.get(id);
    }
}
