package client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import iia.tristan.persistanceclient.R;
import share.dataObject.Magasin;

/**
 * @author Neveux_du_Geniebre on 11/04/2019.
 */
public class ItemSpinnerMagasinAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Magasin> values;

    public ItemSpinnerMagasinAdapter(Context context, ArrayList<Magasin> values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public int id;
    public Magasin magasin;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_spinner_magasin, parent, false);
        TextView txtVille = itemView.findViewById(R.id.item_spinner_magasin_text);

        magasin = values.get(position);
        id = magasin.getId();
        txtVille.setText(magasin.getVille());

        return itemView;
    }

    public int getId()
    {
        return id;
    }

    public Magasin getMagasin()
    {
        return magasin;
    }
}