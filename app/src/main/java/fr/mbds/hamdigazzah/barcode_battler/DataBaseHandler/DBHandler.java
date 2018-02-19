package fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fr.mbds.hamdigazzah.barcode_battler.Model.Character;
import fr.mbds.hamdigazzah.barcode_battler.Model.Potion;
import fr.mbds.hamdigazzah.barcode_battler.Model.Shield;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;

/**
 * Created by hamdigazzah on 26/10/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myDataBase";

    // table name
    private static final String TABLE_CHARACTER = "character";
    private static final String TABLE_SHIELD = "shield";
    private static final String TABLE_WEAPON = "weapon";
    private static final String TABLE_POTION = "potion";

    // Character Table Columns names
    private static final String KEY_ID_C = "id";
    private static final String KEY_NAME_C = "name";
    private static final String KEY_CODE_C = "code";
    private static final String KEY_LIFE_C = "life";
    private static final String KEY_IMAGEPATH_C = "imagePath";

    // Shield Table Columns names
    private static final String KEY_ID_S = "id";
    private static final String KEY_NAME_S = "name";
    private static final String KEY_CODE_S = "code";
    private static final String KEY_CAPACITY_S = "capacity";
    private static final String KEY_IMAGEPATH_S = "imagePath";


    // Weapon Table Columns names
    private static final String KEY_ID_W = "id";
    private static final String KEY_NAME_W = "name";
    private static final String KEY_CODE_W = "code";
    private static final String KEY_DEMAGE_W = "demage";
    private static final String KEY_IMAGEPATH_W = "imagePath";

    // Potion Table Columns names
    private static final String KEY_ID_P = "id";
    private static final String KEY_NAME_P = "name";
    private static final String KEY_CODE_P = "code";
    private static final String KEY_ENERGY_P = "energy";
    private static final String KEY_IMAGEPATH_P = "imagePath";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating character table
        String CREATE_CHARACTER_TABLE = "CREATE TABLE " + TABLE_CHARACTER + "("
                + KEY_ID_C + " INTEGER PRIMARY KEY," + KEY_NAME_C + " TEXT,"
                + KEY_CODE_C + " TEXT," + KEY_LIFE_C + " INTEGER,"
                + KEY_IMAGEPATH_C + " TEXT" + ")";
        db.execSQL(CREATE_CHARACTER_TABLE);

        // Creating shield table
        String CREATE_SHIELD_TABLE = "CREATE TABLE " + TABLE_SHIELD + "("
                + KEY_ID_S + " INTEGER PRIMARY KEY," + KEY_NAME_S + " TEXT,"
                + KEY_CODE_S + " TEXT," + KEY_CAPACITY_S + " INTEGER,"
                + KEY_IMAGEPATH_S + " TEXT" + ")";
        db.execSQL(CREATE_SHIELD_TABLE);

        // Creating weapon table
        String CREATE_WEAPON_TABLE = "CREATE TABLE " + TABLE_WEAPON + "("
                + KEY_ID_W + " INTEGER PRIMARY KEY," + KEY_NAME_W + " TEXT,"
                + KEY_CODE_W + " TEXT," + KEY_DEMAGE_W + " INTEGER,"
                + KEY_IMAGEPATH_W + " TEXT" + ")";
        db.execSQL(CREATE_WEAPON_TABLE);

        // Creating potion table
        String CREATE_POTION_TABLE = "CREATE TABLE " + TABLE_POTION + "("
                + KEY_ID_P + " INTEGER PRIMARY KEY," + KEY_NAME_P + " TEXT,"
                + KEY_CODE_P + " TEXT," + KEY_ENERGY_P + " INTEGER,"
                + KEY_IMAGEPATH_P + " TEXT" + ")";
        db.execSQL(CREATE_POTION_TABLE);


    /*    String INSERT_ADMIN_BUSINESS_CARD = "INSERT INTO " + TABLE_BUSINESS_CARD + " ( "
                + KEY_ID + " , " + KEY_FIRST_NAME + " , "
                + KEY_LAST_NAME + " , " + KEY_NUMBERS + " , "
                + KEY_MAIL + " , " + KEY_ADDRESS + " , "
                + KEY_FUNCTION + " , " + KEY_IMAGE_PATH  + " ) " + " VALUES ( "
                + "'1' , 'admin' ,'' ,'' ,'' ,'' ,'' ,'' )";
        db.execSQL(INSERT_ADMIN_BUSINESS_CARD);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIELD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEAPON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POTION);


        // Creating tables again
        onCreate(db);
    }

    // Adding new character
    public void addCharacter(Character c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_C, c.getName());
        values.put(KEY_CODE_C, c.getCode());
        values.put(KEY_LIFE_C, c.getLife());
        values.put(KEY_IMAGEPATH_C, c.getImagePath());

        // Inserting Row
        db.insert(TABLE_CHARACTER, null, values);
        db.close(); // Closing database connection
    }

    // Adding new shield
    public void addShield(Shield s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_S, s.getName());
        values.put(KEY_CODE_S, s.getCode());
        values.put(KEY_CAPACITY_S, s.getCapacity());
        values.put(KEY_IMAGEPATH_S, s.getImagePath());

        // Inserting Row
        db.insert(TABLE_SHIELD, null, values);
        db.close(); // Closing database connection
    }

    // Adding new weapon
    public void addWeapon(Weapon w) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_W, w.getName());
        values.put(KEY_CODE_W, w.getCode());
        values.put(KEY_DEMAGE_W, w.getDemage());
        values.put(KEY_IMAGEPATH_W, w.getImagePath());

        // Inserting Row
        db.insert(TABLE_WEAPON, null, values);
        db.close(); // Closing database connection
    }

    // Adding new potion
    public void addPotion(Potion p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_P, p.getName());
        values.put(KEY_CODE_P, p.getCode());
        values.put(KEY_ENERGY_P, p.getEnergy());
        values.put(KEY_IMAGEPATH_P, p.getImagePath());

        // Inserting Row
        db.insert(TABLE_POTION, null, values);
        db.close(); // Closing database connection
    }

    // Getting one character
    public Character getCharacter(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHARACTER, new String[]{KEY_ID_C,
                        KEY_NAME_C, KEY_CODE_C, KEY_LIFE_C, KEY_IMAGEPATH_C}, KEY_ID_C + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Character c = new Character(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));
        // return character
        return c;
    }

    // Getting one shield
    public Shield getShield(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHIELD, new String[]{KEY_ID_S,
                        KEY_NAME_S, KEY_CODE_S, KEY_CAPACITY_S, KEY_IMAGEPATH_S}, KEY_ID_S + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Shield s = new Shield(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));
        // return shield
        return s;
    }

    // Getting one weapon
    public Weapon getWeapon(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WEAPON, new String[]{KEY_ID_W,
                        KEY_NAME_W, KEY_CODE_W, KEY_DEMAGE_W, KEY_IMAGEPATH_W}, KEY_ID_W + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Weapon w = new Weapon(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));
        // return weapon
        return w;
    }

    // Getting one potion
    public Potion getPotion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POTION, new String[]{KEY_ID_P,
                        KEY_NAME_P, KEY_CODE_P, KEY_ENERGY_P, KEY_IMAGEPATH_P}, KEY_ID_P + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Potion p = new Potion(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));
        // return potion
        return p;
    }

    // Getting All characters
    public List<Character> getAllCharacters() {
        List<Character> cList = new ArrayList<Character>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CHARACTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Character c = new Character();
                c.setId(Integer.parseInt(cursor.getString(0)));
                c.setName(cursor.getString(1));
                c.setCode(cursor.getString(2));
                c.setLife(Integer.parseInt(cursor.getString(3)));
                c.setImagePath(cursor.getString(4));
                // Adding character to list
                cList.add(c);
            } while (cursor.moveToNext());
        }
        // return contact list
        return cList;
    }

    // Getting All shields
    public List<Shield> getAllShields() {
        List<Shield> sList = new ArrayList<Shield>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SHIELD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Shield s = new Shield();
                s.setId(Integer.parseInt(cursor.getString(0)));
                s.setName(cursor.getString(1));
                s.setCode(cursor.getString(2));
                s.setCapacity(Integer.parseInt(cursor.getString(3)));
                s.setImagePath(cursor.getString(4));
                // Adding character to list
                sList.add(s);
            } while (cursor.moveToNext());
        }
        // return shields list
        return sList;
    }

    // Getting All weapons
    public List<Weapon> getAllWeapons() {
        List<Weapon> wList = new ArrayList<Weapon>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_WEAPON;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Weapon w = new Weapon();
                w.setId(Integer.parseInt(cursor.getString(0)));
                w.setName(cursor.getString(1));
                w.setCode(cursor.getString(2));
                w.setDemage(Integer.parseInt(cursor.getString(3)));
                w.setImagePath(cursor.getString(4));
                // Adding character to list
                wList.add(w);
            } while (cursor.moveToNext());
        }
        // return weapons list
        return wList;
    }

    // Getting All potions
    public List<Potion> getAllPotions() {
        List<Potion> pList = new ArrayList<Potion>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_POTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Potion p = new Potion();
                p.setId(Integer.parseInt(cursor.getString(0)));
                p.setName(cursor.getString(1));
                p.setCode(cursor.getString(2));
                p.setEnergy(Integer.parseInt(cursor.getString(3)));
                p.setImagePath(cursor.getString(4));
                // Adding character to list
                pList.add(p);
            } while (cursor.moveToNext());
        }
        // return potions list
        return pList;
    }

    // Getting characters Count
    public int getCharactersCount() {
        String countQuery = "SELECT * FROM " + TABLE_CHARACTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Getting shields Count
    public int getShieldsCount() {
        String countQuery = "SELECT * FROM " + TABLE_SHIELD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Getting weapons Count
    public int getWeaponsCount() {
        String countQuery = "SELECT * FROM " + TABLE_WEAPON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Getting potions Count
    public int getPotionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_POTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a character
    public int updateCharacter(Character c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_C, c.getName());
        values.put(KEY_CODE_C, c.getCode());
        values.put(KEY_LIFE_C, c.getLife());
        values.put(KEY_IMAGEPATH_C, c.getImagePath());
        // updating row
        return db.update(TABLE_CHARACTER, values, KEY_ID_C + " = ?",
                new String[]{String.valueOf(c.getId())});
    }

    // Updating a shild
    public int updateShield(Shield s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_S, s.getName());
        values.put(KEY_CODE_S, s.getCode());
        values.put(KEY_CAPACITY_S, s.getCapacity());
        values.put(KEY_IMAGEPATH_S, s.getImagePath());
        // updating row
        return db.update(TABLE_SHIELD, values, KEY_ID_S + " = ?",
                new String[]{String.valueOf(s.getId())});
    }

    // Updating a weapon
    public int updateWeapon(Weapon w) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_W, w.getName());
        values.put(KEY_CODE_W, w.getCode());
        values.put(KEY_DEMAGE_W, w.getDemage());
        values.put(KEY_IMAGEPATH_W, w.getImagePath());
        // updating row
        return db.update(TABLE_WEAPON, values, KEY_ID_S + " = ?",
                new String[]{String.valueOf(w.getId())});
    }

    // Updating a weapon
    public int updatePotion(Potion p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_P, p.getName());
        values.put(KEY_CODE_P, p.getCode());
        values.put(KEY_ENERGY_P, p.getEnergy());
        values.put(KEY_IMAGEPATH_P, p.getImagePath());
        // updating row
        return db.update(TABLE_POTION, values, KEY_ID_S + " = ?",
                new String[]{String.valueOf(p.getId())});
    }

    // Deleting a character
    public void deleteCharacter(Character c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHARACTER, KEY_ID_C + " = ?",
                new String[]{String.valueOf(c.getId())});
        db.close();
    }

    // Deleting a shield
    public void deleteShield(Shield s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHIELD, KEY_ID_S + " = ?",
                new String[]{String.valueOf(s.getId())});
        db.close();
    }

    // Deleting a weapon
    public void deleteWeapon(Weapon w) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEAPON, KEY_ID_W + " = ?",
                new String[]{String.valueOf(w.getId())});
        db.close();
    }

    // Deleting a potion
    public void deletePotion(Potion p) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POTION, KEY_ID_P + " = ?",
                new String[]{String.valueOf(p.getId())});
        db.close();
    }

}
