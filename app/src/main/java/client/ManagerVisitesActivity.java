package client;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import client.adapter.ManagerVisiteAdapter;
import client.adapter.VisiteAdapter;
import iia.tristan.persistanceclient.R;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumVille;
import share.manager.DataManager;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerVisitesActivity extends AppCompatActivity implements View.OnClickListener {

    public static String SELLER_ID = "SELLER_ID";

    private ListView listVisites;
    private ImageButton btnAddVisite;
    private ImageButton btnDeleteAll;

    private Dialog dialog;

    private int sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_visites);
        dialog = new Dialog(this);

        sellerId = savedInstanceState.getInt(SELLER_ID);

        listVisites = findViewById(R.id.list_visite_manager);
        btnAddVisite = findViewById(R.id.bt_manager_visites_add);
        btnDeleteAll = findViewById(R.id.bt_manager_visites_delete);

        btnDeleteAll.setOnClickListener(this);
        btnAddVisite.setOnClickListener(this);

        listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), DataManager.INSTANCE.getAllVisiteByUser(sellerId)));

    }

    @Override
    public void onClick(View view)
    {
        if (view == btnDeleteAll)
        {
            if (!listVisites.getAdapter().isEmpty())
            {
                DataManager.INSTANCE.deleteAllVisiteForUserId(sellerId);
                listVisites.removeAllViews();
                listVisites.invalidate();
            }
        }
        else
            if (view == btnAddVisite)
            {
                showPp();
            }
    }

    private void showPp()
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

        // Inaccessible par défaut
        spinnerEnseigne.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, R.id.item_spinner_text, EnumEnseigne.values()));
        spinnerVille.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner_magasin, R.id.item_spinner_magasin_text, EnumVille.values()));

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
                //TODO create on database new visit for this seller
                Toast.makeText(getApplicationContext(), "Succesfully create new visite : " + date + " enseigne : " + EnumEnseigne.getEnumFromId(spinnerEnseigne.getSelectedItemPosition()), Toast.LENGTH_LONG).show();
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
