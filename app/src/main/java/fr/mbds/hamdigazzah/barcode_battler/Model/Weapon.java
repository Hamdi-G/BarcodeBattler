package fr.mbds.hamdigazzah.barcode_battler.Model;

/**
 * Created by hamdigazzah on 26/10/2017.
 */

public class Weapon {

    private int id;
    private String name;
    private String code;
    private int demage;
    private String imagePath;

    public Weapon() {
    }

    public Weapon(int id, String name, String code, int demage, String imagePath) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.demage = demage;
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

    public int getDemage() {
        return demage;
    }

    public void setDemage(int demage) {
        this.demage = demage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
