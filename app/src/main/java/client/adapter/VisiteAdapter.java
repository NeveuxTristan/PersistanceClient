package client.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

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
public class VisiteAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Visite> values;

    private Dialog dialog;

    public VisiteAdapter(Context context, ArrayList<Visite> values)
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

    private void showPpSellerVisite(final Visite visite)
    {
        // Init Pp
        dialog.setContentView(R.layout.pp_seller_visite);
        final MaterialButton btnUpdate, btnCancel;
        final ImageView imageEnseigne;
        final TextView textVille;
        final DatePicker datePicker;
        final Switch stateVisit;
        final EditText comment;

        btnCancel = dialog.findViewById(R.id.pp_seller_visit_btn_cancel);
        btnUpdate = dialog.findViewById(R.id.pp_seller_visit_btn_create);
        imageEnseigne = dialog.findViewById(R.id.pp_seller_visit_icon_enseigne);
        textVille = dialog.findViewById(R.id.pp_seller_visit_display_ville);
        datePicker = dialog.findViewById(R.id.pp_seller_visit_date_picker);
        stateVisit = dialog.findViewById(R.id.pp_seller_visit_switch_state);
        comment = dialog.findViewById(R.id.pp_seller_visit_comment);

        Magasin m = DataManager.INSTANCE.getMagasinById(visite.getIdMagasin());
        imageEnseigne.setImageResource(
                EnumEnseigne.CARREFOUR.equals(m.getEnseigne()) ? R.drawable.carrefour :
                        EnumEnseigne.LECLERC.equals(m.getEnseigne()) ? R.drawable.leclerc : R.drawable.super_u);
        textVille.setText(EnumVille.getVilleById(m.getVille()).getDisplayName());

        String dateFormat[] = visite.getDateVisite().split("/");
        datePicker.updateDate(Integer.valueOf(dateFormat[2]), Integer.valueOf(dateFormat[1]), Integer.valueOf(dateFormat[0]));
        datePicker.setEnabled(false);

        stateVisit.setChecked(visite.isVisiteDone());
        comment.setText(visite.getComment().length() > 0 ? visite.getComment() : "Enter comment here...");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO Save new visit State
                visite.setVisiteDone(stateVisit.isChecked());
                visite.setComment(comment.getText().toString());
                // TODO Save new visit State
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}