package matisek.pokedex;

import android.graphics.Bitmap;
import android.media.audiofx.Equalizer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mateusz on 10.07.2016.
 */
public class Pokemon{
    private int id;
    private String name;
    private int experience;
    private Bitmap image;
    private int height;
    private int weight;

    public Pokemon(int id, String name, Bitmap image, int experience, int height, int weight) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.experience = experience;
        this.height = height;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
    @Override
    public boolean equals(Object other){
        return (other instanceof Pokemon) && ((Pokemon) other).name.equals(name);
    }



}
