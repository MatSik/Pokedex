package matisek.pokedex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FavoritePokemonInfo extends AppCompatActivity {

    ImageView imageOfPokemon;
    TextView number;
    TextView name;
    TextView experience;
    TextView height;
    TextView weight;

    Button next;
    Button previous;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons_online);

        initlializateTextViews();
        initlializateButtons();
        setInvisible();



        try {
            setDescription();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void setInvisible() {
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void setDescription() throws IOException {
        Intent intent = getIntent();
        Bundle recive = intent.getExtras();

        imageOfPokemon.setImageBitmap(getImageOfPokemon(recive.getString("name")));
        number.setText(String.valueOf(recive.getInt("id")));
        name.setText(recive.getString("name"));
        experience.setText(String.valueOf(recive.getInt("experience")));
        height.setText(String.valueOf(recive.getInt("height")));
        weight.setText(String.valueOf(recive.getInt("weight")));
    }

    private void initlializateTextViews() {
        imageOfPokemon = (ImageView) findViewById(R.id.imageViewOfPokemon);
        number = (TextView) findViewById(R.id.textViewNumerOfPokemon);
        name = (TextView) findViewById(R.id.textViewNameOfPokemon);
        experience = (TextView) findViewById(R.id.textViewExperienceForPokemon);
        height = (TextView) findViewById(R.id.textViewHeightOfPokemon);
        weight = (TextView) findViewById(R.id.textViewWeightOfPokemon);
    }
    private void initlializateButtons() {
        next = (Button) findViewById(R.id.buttonNext);
        previous = (Button) findViewById(R.id.buttonPrevious);
        save = (Button) findViewById(R.id.buttonSave);
    }

    public Bitmap getImageOfPokemon(String name) throws IOException {
        FileInputStream fis = getApplicationContext().openFileInput(name + ".png");
        Bitmap b = BitmapFactory.decodeStream(fis);
        fis.close();
        return b;
    }
}
