package matisek.pokedex;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mateusz on 09.07.2016.
 */
public class RemoteFetch {
    private static final String OPEN_POKEMON_API =
            "http://pokeapi.co/api/v2/pokemon/";

    public static JSONObject getJSON(Context context, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            URL url = new URL("http://swapi.co/api/people/1/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Toast.makeText(context,"chuj",Toast.LENGTH_LONG).show();
            Toast.makeText(context,connection.getResponseMessage(),Toast.LENGTH_LONG).show();
            Toast.makeText(context,"jednak nie",Toast.LENGTH_LONG).show();
            InputStreamReader input = new InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(input);



            String tmp="";
            Toast.makeText(context,"chuj",Toast.LENGTH_LONG).show();
            Toast.makeText(context,String.valueOf(reader.readLine()!=null),Toast.LENGTH_LONG).show();
            while((tmp=reader.readLine())!=null) {
                stringBuilder.append(tmp);
                Toast.makeText(context,tmp,Toast.LENGTH_LONG).show();
            }reader.close();

            //JSONObject data = new JSONObject(json.toString());


            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //return null;
    }

}
