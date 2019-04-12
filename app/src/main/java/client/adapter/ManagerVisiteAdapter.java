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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
public class ManagerVisiteAdapter extends ArrayAdapter implements View.OnClickListener {

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
        ImageView btnEdit = itemView.findViewById(R.id.visite_btn_edit);
        ImageView btnDelete = itemView.findViewById(R.id.visite_btn_delete);
        ImageView visiteIco = itemView.findViewById(R.id.iconVisiteItem);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

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
        }

        return itemView;
    }

    @Override
    public void onClick(View view)
    {
        if (view == btnEdit)
        {
            int position = ((ListView) view.getParent()).getPositionForView(view);
            showEditPp(values.get(position));
            notifyDataSetChanged();
        }
        else
            if (view == btnDelete)
            {
                int position = ((ListView) view.getParent()).getPositionForView(view);
                ((ListView) view.getParent()).removeView(view);
                //TODO remove this visite from database
                notifyDataSetChanged();
            }
    }

    /**
     * Popup de création avec les valeurs initiales déjà set
     *
     * @param visite visite initiale
     */
    private void showEditPp(Visite visite)
    {
        // Composant de la pp //
        TextView txtclose;
        final MaterialButton btnCreate, btnCancel;
        final Spinner spinnerEnseigne, spinnerVille;
        final ImageView imageEnseigne;
        final TextView textVille;
        final DatePicker datePicker;

        dialog.setContentView(R.layout.pp_create_visite);
        btnCreate = dialog.findViewById(R.id.pp_create_visit_btn_create);
        btnCancel = dialog.findViewById(R.id.pp_create_visit_btn_cancel);
        spinnerVille = dialog.findViewById(R.id.pp_create_visit_select_ville);
        spinnerEnseigne = dialog.findViewById(R.id.pp_create_visit_select_enseigne);
        imageEnseigne = dialog.findViewById(R.id.pp_create_visit_icon_enseigne);
        textVille = dialog.findViewById(R.id.pp_create_visit_display_ville);
        datePicker = dialog.findViewById(R.id.pp_create_visit_date_picker);

        Magasin magasin = DataManager.INSTANCE.getMagasinById(visite.getIdMagasin());

        spinnerEnseigne.setAdapter(new ArrayAdapter<>(context, R.layout.item_spinner, R.id.item_spinner_text, EnumEnseigne.values()));
        spinnerVille.setAdapter(new ArrayAdapter<>(context, R.layout.item_spinner_magasin, R.id.item_spinner_magasin_text, EnumVille.values()));
        spinnerEnseigne.setSelection(((ArrayAdapter<String>) spinnerEnseigne.getAdapter()).getPosition(magasin.getEnseigne().toString()));
        spinnerVille.setSelection(((ArrayAdapter<String>) spinnerEnseigne.getAdapter()).getPosition(EnumVille.getVilleById(magasin.getVille()).getDisplayName()));
        String[] dateFormat = visite.getDateVisite().split("/");
        datePicker.updateDate(Integer.valueOf(dateFormat[2]), Integer.valueOf(dateFormat[1]), Integer.valueOf(dateFormat[0]));

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(calendar.getTime());
                //TODO edit on database new visit for this seller
                Toast.makeText(context, "Succesfully create new visite : " + date + " enseigne : " + EnumEnseigne.getEnumFromId(spinnerEnseigne.getSelectedItemPosition()), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        spinnerEnseigne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                EnumEnseigne enumEnseigne = EnumEnseigne.getEnumFromId(position);

                imageEnseigne.setImageResource(
                        EnumEnseigne.CARREFOUR.equals(enumEnseigne) ? R.drawable.carrefour :
                                EnumEnseigne.LECLERC.equals(enumEnseigne) ? R.drawable.leclerc : R.drawable.super_u);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }

        });

        spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // Magasin m = ((ItemSpinnerMagasinAdapter) parent.getAdapter()).getMagasin();
                // textVille.setText(m.getVille());
                textVille.setText(spinnerVille.getSelectedItem().toString());
                btnCreate.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
