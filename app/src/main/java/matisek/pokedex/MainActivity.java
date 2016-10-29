package matisek.pokedex;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button pokemonOnline;
    Button favouritePokemons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeViews();
        setBackgroundsForImages();
        setupPokemonOnlineButton();
        setupFavouritePokemonButton();
    }

    private void intializeViews() {
        pokemonOnline = (Button) findViewById(R.id.buttonPokemonOnline);
        favouritePokemons = (Button) findViewById(R.id.buttonFavourite);
    }

    private void setBackgroundsForImages() {
        pokemonOnline.setBackgroundResource(R.drawable.custom_button_pokeball);
        favouritePokemons.setBackgroundResource(R.drawable.custom_button_star);
    }

    private void setupPokemonOnlineButton() {
        pokemonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pokemonOnlineIntent = new Intent(getApplicationContext() , PokemonsOnline.class);
                startActivity(pokemonOnlineIntent);
            }
        });
    }

    private void setupFavouritePokemonButton() {
        favouritePokemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favouritePokemonsIntent = new Intent(getApplicationContext() , FavouritePokemons.class);
                startActivity(favouritePokemonsIntent);
            }
        });
    }


}
