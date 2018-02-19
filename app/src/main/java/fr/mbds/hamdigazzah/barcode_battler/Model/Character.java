package fr.mbds.hamdigazzah.barcode_battler.Model;

import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hamdigazzah on 26/10/2017.
 */

public class Character {

    private int id;
    private String name;
    private String code;
    private int life;
    private Weapon weapon;
    private Shield shield;
    private String imagePath;

    public Character() {
    }

    public Character(int id, String name, String code, int life, String imagePath) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.life = life;
        this.imagePath = imagePath;
    }

    public Integer attack(Character c) {
        Log.d("life", String.valueOf(c.life));
        Log.d("damage", String.valueOf(this.weapon.getDemage()));
        Log.d("capacity", String.valueOf(c.shield.getCapacity()));
        Log.d("difference", String.valueOf((this.weapon.getDemage() - c.shield.getCapacity())));

        int attackDamage = randomInt(this.weapon.getDemage());
        int defenseCapacity = randomInt(c.shield.getCapacity());
        if ((attackDamage - defenseCapacity) > 0) {
            c.life -= (attackDamage - defenseCapacity);
            return (attackDamage - defenseCapacity);
        }
        return 0;
    }

    private static Integer randomInt(int value) {
        return ThreadLocalRandom.current().nextInt(0, value + 1);
    }

    public void getPotion(Potion p) {
        this.life += p.getEnergy();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

}

