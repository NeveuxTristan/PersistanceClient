package client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import iia.tristan.persistanceclient.R;
import share.dataObject.Magasin;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.manager.DataManager;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class VisiteAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Visite> values;

    public VisiteAdapter(Context context, ArrayList<Visite> values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_visite, parent, false);
        TextView txtVisiteDate = itemView.findViewById(R.id.visite_item_date);
        TextView txtVisiteName = itemView.findViewById(R.id.visite_item_name);
        TextView txtVisiteState = itemView.findViewById(R.id.visite_item_state);
        TextView txtComment = itemView.findViewById(R.id.visite_item_comment);
        ImageView visiteIco = itemView.findViewById(R.id.icVisiteItem);

        int idMagasin = values.get(position).getIdMagasin();
        Magasin magasin = null;
        for (Magasin m : DataManager.INSTANCE.getAllMagasins())
        {
            if (m.getId() == idMagasin)
            {
                magasin = m;
                break;
            }
        }

        if (magasin != null)
        {
            visiteIco.setImageResource(EnumEnseigne.CARREFOUR.equals(magasin.getEnseigne()) ? R.drawable.carrefour :
                    EnumEnseigne.LECLERC.equals(magasin.getEnseigne()) ? R.drawable.leclerc : R.drawable.super_u);

            txtVisiteDate.setText(values.get(position).getDateVisite());
            txtVisiteName.setText(magasin.getDisplayName());
            txtVisiteState.setText(values.get(position).isVisiteDone() ? "Visit DONE" : "Visit TO DO");
            txtVisiteState.setTextColor(values.get(position).isVisiteDone() ? Color.GREEN : Color.RED);
            String comment = values.get(position).getComment();
            comment = comment.length() > 30 ? comment.substring(0, 28) + "..." : comment;
            txtComment.setText(comment);

        }

        return itemView;
    }


}