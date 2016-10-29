package matisek.pokedex;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class FavouritePokemons extends AppCompatActivity {

    List<Pokemon> pokemonList;
    Set<Integer> pokemonAlreadyInList;

    ListView listViewOfPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pokemons);

        initializeCollections();
        addDataToArray();
        initializeViews();
        setAdapterToListView();
        setupListViewPokemonItem();
    }

    private void initializeCollections() {
        pokemonList = new ArrayList<>();
        pokemonAlreadyInList = new HashSet<>();
    }

    private void setAdapterToListView() {
        PokemonListAdapter pokemonAdapter = new PokemonListAdapter(this, pokemonList);
        listViewOfPokemon.setAdapter(pokemonAdapter);
    }

    private void initializeViews() {
        listViewOfPokemon = (ListView) findViewById(R.id.listViewOfPokemons);
    }

    private void setupListViewPokemonItem() {
        listViewOfPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle send = new Bundle();

                send.putInt("id",pokemonList.get(position).getId());
                send.putString("name",pokemonList.get(position).getName());
                send.putInt("experience",pokemonList.get(position).getExperience());
                send.putInt("height",pokemonList.get(position).getHeight());
                send.putInt("weight",pokemonList.get(position).getWeight());
                Intent intent = new Intent(getApplicationContext(),FavoritePokemonInfo.class);
                intent.putExtras(send);
                startActivity(intent);
            }
        });
    }

    public void addDataToArray(){
        String message;
        try {
            BufferedReader bufferedReader = new BufferedReader(getInputStreamReader());
            while ((message = bufferedReader.readLine()) != null) {
                Pokemon readedPokemon = createPokemonFromToken(new StringTokenizer(message, "$"));
                checkDuplicate(readedPokemon);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDuplicate(Pokemon readedPokemon) {
        if(!pokemonAlreadyInList.contains(readedPokemon.getId())) {
            pokemonAlreadyInList.add(readedPokemon.getId());
            pokemonList.add(readedPokemon);
        }
    }

    private Pokemon createPokemonFromToken(StringTokenizer pokemonTokenizer) throws IOException {
        String id = pokemonTokenizer.nextToken();
        String name = pokemonTokenizer.nextToken();
        String experience = pokemonTokenizer.nextToken();
        String height = pokemonTokenizer.nextToken();
        String weight = pokemonTokenizer.nextToken();
        FileInputStream fis = getApplicationContext().openFileInput(name + ".png");
        Bitmap pokemonImage = BitmapFactory.decodeStream(fis);
        fis.close();
        return new Pokemon(Integer.valueOf(id), name, pokemonImage, Integer.valueOf(experience), Integer.valueOf(height), Integer.valueOf(weight));
    }

    private InputStreamReader getInputStreamReader() throws FileNotFoundException {
        FileInputStream fileInputStream = openFileInput(PokemonsOnline.DESCRIPTIONS_OF_POKEMONS_FILE_STRING);
        return new InputStreamReader(fileInputStream);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem findID = menu.add(0,1,1,"find");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MaterialDialog materialDialog = new MaterialDialog.Builder( FavouritePokemons.this)
                .customView(R.layout.layout_for_dialog, true)
                .show();
        setupViewButton(materialDialog);
        return true;
    }
    private void setupViewButton(final MaterialDialog materialDialog){
        final View view  = materialDialog.getView();
        ((Button)view.findViewById(R.id.buttonFind)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pokemon findedPokemon = findPokemon(((EditText)view.findViewById(R.id.editTextNameOrId)).getText().toString());
                if(findedPokemon != null){
                    setDescription(findedPokemon);
                }
                materialDialog.dismiss();
            }
        });
    }

    private void setDescription(Pokemon findedPokemon) {
        Bundle send = new Bundle();
        send.putInt("id",findedPokemon.getId());
        send.putString("name",findedPokemon.getName());
        send.putInt("experience",findedPokemon.getExperience());
        send.putInt("height",findedPokemon.getHeight());
        send.putInt("weight",findedPokemon.getWeight());
        Intent intent = new Intent(getApplicationContext(),FavoritePokemonInfo.class);
        intent.putExtras(send);
        startActivity(intent);
    }

    public Pokemon findPokemon(String identify){
        for (int i = 0; i < pokemonList.size(); i++) {
            if (String.valueOf(pokemonList.get(i).getId()).equals(identify) || String.valueOf(pokemonList.get(i).getName()).equals(identify) ){
                return pokemonList.get(i);
            }
        }
        return null;
    }
}
