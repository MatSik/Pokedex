package matisek.pokedex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PokemonsOnline extends AppCompatActivity {

    private static final String OPEN_POKEMON_API =
            "http://pokeapi.co/api/v2/pokemon/";
    static final String DESCRIPTIONS_OF_POKEMONS_FILE_STRING = "Moje_Pokemonki.txt";
    private int id = 1;
    ImageView imageOfPokemon;
    TextView number;
    TextView name;
    TextView experience;
    TextView height;
    TextView weight;

    Button next;
    Button previous;
    Button save;

    JSONObject data;
    Bitmap currentPokemonPhoto;
    int countOfPokemon = 721;
    Thread find;
    ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons_online);

        initlializateButtons();
        initlializateViews();
        findPokemon(String.valueOf(id));
        setupSaveButton();
        setupNextButton();
        setupPreviousButton();
    }

    private void initlializateViews() {
        number = (TextView) findViewById(R.id.textViewNumerOfPokemon);
        name = (TextView) findViewById(R.id.textViewNameOfPokemon);
        experience = (TextView) findViewById(R.id.textViewExperienceForPokemon);
        height = (TextView) findViewById(R.id.textViewHeightOfPokemon);
        weight = (TextView) findViewById(R.id.textViewWeightOfPokemon);
        imageOfPokemon = (ImageView) findViewById(R.id.imageViewOfPokemon);
    }


    private void setupSaveButton() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    savePokemon(currentPokemonPhoto);
            }
        });
    }

    private void setupNextButton() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == countOfPokemon) {
                    id = 0;
                }
                findPokemon(String.valueOf(++id));
            }
        });
    }

    private void setupPreviousButton() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1) {
                    id = 722;
                }
                findPokemon(String.valueOf(--id));
            }
        });
    }

    public void findPokemon(final String identify) {
        showLoadingDialog();
        createFindThread(identify);
        find.start();
        waitForDataPass();
        setDescription();
        progDailog.dismiss();
        }

    private void createFindThread(final String identify) {
        find = new Thread(new Runnable() {
            public void run() {
                StringBuilder stringBuilder;
                try {
                    BufferedReader reader = new BufferedReader(getInputStreamReader(identify));
                    stringBuilder = filledStringBuilder(reader);
                    reader.close();
                    data = new JSONObject(stringBuilder.toString());
                    currentPokemonPhoto = BitmapFactory.decodeStream((InputStream) new URL(data.getJSONObject("sprites").getString("front_default")).getContent());
                }catch(IOException e){
                    e.printStackTrace();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDescription(){
        try {
            imageOfPokemon.setImageBitmap(currentPokemonPhoto);
            number.setText(String.valueOf(data.getInt("id")));
            name.setText(data.getString("name"));
            experience.setText(String.valueOf(data.getInt("base_experience")));
            height.setText(String.valueOf(data.getInt("height")));
            weight.setText(String.valueOf(data.getInt("weight")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private InputStreamReader getInputStreamReader(String identify) throws IOException {
        URL url = new URL(OPEN_POKEMON_API + identify + "/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return new InputStreamReader(connection.getInputStream());
    }

    private StringBuilder filledStringBuilder(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String tmp = "";
        while ((tmp = reader.readLine()) != null) {
            stringBuilder.append(tmp).append("\n");
        }
        return stringBuilder;
    }

    public void showLoadingDialog() {
        progDailog = new ProgressDialog(PokemonsOnline.this);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();
    }
    private void waitForDataPass() {
        try {
            find.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem find = menu.add(0,1,1,"Find");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MaterialDialog materialDialog = new MaterialDialog.Builder( PokemonsOnline.this)
                .customView(R.layout.layout_for_dialog, true)
                .show();
        setupSearchOption(materialDialog);
        return true;
    }
    private void setupSearchOption(final MaterialDialog materialDialog) {
        final View view  = materialDialog.getView();
        ((Button)view.findViewById(R.id.buttonFind)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPokemon(((EditText)view.findViewById(R.id.editTextNameOrId)).getText().toString());
                materialDialog.dismiss();
            }
        });
    }

    public String getNameOfPicture(){
        String name = "";
        try {
            name = data.getString("name")+".png";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    public void savePokemon(Bitmap image){
        FileOutputStream outImage;
        FileOutputStream description;
        try {
            outImage = getApplicationContext().openFileOutput(getNameOfPicture(), Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, outImage);
            description = getApplicationContext().openFileOutput(DESCRIPTIONS_OF_POKEMONS_FILE_STRING, Context.MODE_APPEND);
            saveDescription(description);
            outImage.close();
            description.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveDescription(FileOutputStream description) throws JSONException, IOException {
        description.write((data.getInt("id") + "$").getBytes());
        description.write((data.getString("name") + "$").getBytes());
        description.write((data.getInt("base_experience") + "$").getBytes());
        description.write((data.getInt("height") + "$").getBytes());
        description.write((data.getInt("weight") + "$" + "\n").getBytes());
    }

    private void initlializateButtons() {
        next = (Button) findViewById(R.id.buttonNext);
        next.setBackgroundResource(R.drawable.custom_button_next);
        previous = (Button) findViewById(R.id.buttonPrevious);
        previous.setBackgroundResource(R.drawable.cutom_button_previous);
        save = (Button) findViewById(R.id.buttonSave);
        save.setBackgroundResource(R.drawable.custom_button_save);
    }
}

