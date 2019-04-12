package client.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import iia.tristan.persistanceclient.R;
import share.dataObject.Magasin;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumVille;
import share.manager.DataManager;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerVisiteAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Visite> values;

    private Dialog dialog;

    public ManagerVisiteAdapter(Context context, ArrayList<Visite> values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.dialog = new Dialog(context);
    }


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
        TextView txtComment = itemView.findViewById(R.id.visite_item_manager_comment);
        ImageView visiteIco = itemView.findViewById(R.id.iconVisiteItem);

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
