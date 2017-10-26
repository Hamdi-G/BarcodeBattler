package fr.mbds.hamdigazzah.barcode_battler.Model;

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

    public Character() {}

    public Character(int id, String name, String code, int life, Weapon weapon, Shield shield, String imagePath) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.life = life;
        this.weapon = weapon;
        this.shield = shield;
        this.imagePath = imagePath;
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

    public void attack(Character c) {
        this.life -= c.getWeapon().getDemage() - this.shield.getCapacity();
    }

}

