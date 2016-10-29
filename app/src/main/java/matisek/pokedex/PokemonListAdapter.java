package matisek.pokedex;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mateusz on 10.07.2016.
 */
public class PokemonListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Pokemon> pokemons;

    public PokemonListAdapter(Activity context, List<Pokemon> pokemons) {
        super();
        this.pokemons = pokemons;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Pokemon item = pokemons.get(position);
        View vi = convertView;
        if(convertView == null)
        {
            vi = inflater.inflate(R.layout.layout_custom_list, null);
        }

        ImageView imageOfPokemon = (ImageView) vi.findViewById(R.id.imageViewPokemon);
        imageOfPokemon.setImageBitmap(item.getImage());

        TextView pokemonName = (TextView) vi.findViewById(R.id.textViewNameOfFavoritePokemon);
        pokemonName.setText(item.getName());

        return vi;
    }
}
