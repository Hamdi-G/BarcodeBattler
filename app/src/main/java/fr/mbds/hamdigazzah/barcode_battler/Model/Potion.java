package fr.mbds.hamdigazzah.barcode_battler.Model;

/**
 * Created by hamdigazzah on 26/10/2017.
 */

public class Potion {

    private int id;
    private String name;
    private String code;
    private int energy;
    private String imagePath;

    public Potion() {
    }

    public Potion(int id, String name, String code, int energy, String imagePath) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.energy = energy;
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
