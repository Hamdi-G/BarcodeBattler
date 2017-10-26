package fr.mbds.hamdigazzah.barcode_battler.Model;

/**
 * Created by hamdigazzah on 26/10/2017.
 */

public class Shield {

    private int id;
    private String name;
    private String code;
    private int capacity;
    private String imagePath;

    public Shield() {
    }

    public Shield(int id, String name, String code, int capacity, String imagePath) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
