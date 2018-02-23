package fr.mbds.hamdigazzah.barcode_battler.Utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hamdigazzah on 23/02/2018.
 */

public class Utils {

    public static Integer randomInt(int value) {
        return ThreadLocalRandom.current().nextInt(1, value + 1);
    }
}
