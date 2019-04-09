package iia.tristan.persistanceclient.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
public class ManagerVisiteAdapter extends ArrayAdapter implements View.OnClickListener {

    private final Context context;
    private final Visite[] values;

    public ManagerVisiteAdapter(Context context, Visite[] values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    private ImageButton btnEdit, btnDelete;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_manager_visite, parent, false);
        TextView txtVisiteDate = itemView.findViewById(R.id.visite_date);
        TextView txtVisiteName = itemView.findViewById(R.id.visite_name);
        TextView txtVisiteState = itemView.findViewById(R.id.visite_state);
        ImageButton btnEdit = itemView.findViewById(R.id.visite_btn_edit);
        ImageButton btnDelete = itemView.findViewById(R.id.visite_btn_delete);
        ImageView visiteIco = itemView.findViewById(R.id.iconVisiteItem);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        int idMagasin = values[position].getIdMagasin();
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

            txtVisiteDate.setText(values[position].getDateVisite());
            txtVisiteName.setText(magasin.getDisplayName());
            txtVisiteState.setText(values[position].isVisiteDone() ? "Visit TO DO" : "Visit DONE");
            txtVisiteState.setTextColor(values[position].isVisiteDone() ? Color.RED : Color.GREEN);
        }

        return itemView;
    }

    @Override
    public void onClick(View view)
    {
        if (view == btnEdit)
        {

        }
        else
            if (view == btnDelete)
            {

            }
    }
}
