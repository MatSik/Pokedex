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
        //wlacz();
        setContentView(R.layout.activity_main);
        pokemonOnline = (Button) findViewById(R.id.buttonPokemonOnline);
        pokemonOnline.setBackgroundResource(R.drawable.custom_button_pokeball);
        pokemonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pokemonOnlineIntent = new Intent(getApplicationContext() , PokemonsOnline.class);
                startActivity(pokemonOnlineIntent);
            }
        });

        favouritePokemons = (Button) findViewById(R.id.buttonFavourite);
        favouritePokemons.setBackgroundResource(R.drawable.custom_button_star);
        favouritePokemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favouritePokemonsIntent = new Intent(getApplicationContext() , FavouritePokemons.class);
                startActivity(favouritePokemonsIntent);
            }
        });
    }

    public void wlacz() {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater lin = getLayoutInflater();
        View appear = lin.inflate(R.layout.activity_pokedex_image, (ViewGroup) findViewById(R.id.toast_linear_layout));
        toast.setView(appear);
        toast.show();
    }
}
