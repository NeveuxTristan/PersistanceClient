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
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import client.adapter.ManagerVisiteAdapter;
import iia.tristan.persistanceclient.R;
import share.dataObject.Magasin;
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

    private ArrayList<Visite> visitesDisplay;
    private View itemSelected;
    private Visite visiteSelected;
    private int posVisiteSelected;

    private Dialog dialog;

    private int sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_visites);
        dialog = new Dialog(this);

        Bundle extras = getIntent().getExtras();
        sellerId = extras.getInt(SELLER_ID);

        listVisites = findViewById(R.id.list_visite_manager);
        btnAddVisite = findViewById(R.id.bt_manager_visites_add);
        btnDeleteAll = findViewById(R.id.bt_manager_visites_delete);

        btnDeleteAll.setOnClickListener(this);
        btnAddVisite.setOnClickListener(this);

        visitesDisplay = DataManager.INSTANCE.getAllVisiteByUser(sellerId);
        listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), visitesDisplay));

        listVisites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                posVisiteSelected = position;
                visiteSelected = visitesDisplay.get(position);
                itemSelected = view;
                showEditPp(visiteSelected);
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        if (view == btnDeleteAll)
        {
            if (!listVisites.getAdapter().isEmpty())
            {
                DataManager.INSTANCE.deleteAllVisiteForUserId(sellerId);
                visitesDisplay.clear();
                listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), visitesDisplay));
                listVisites.invalidate();
            }
        }
        else
            if (view == btnAddVisite)
            {
                showPp();
            }
    }

    /**
     * Popup de création avec les valeurs initiales déjà set
     *
     * @param visite visite initiale
     */
    private void showEditPp(final Visite visite)
    {
        // Composant de la pp //
        TextView txtclose;
        final MaterialButton btnUpdate, btnDelete;
        final Spinner spinnerEnseigne, spinnerVille;
        final ImageView imageEnseigne;
        final TextView textVille, txtTitle;
        final DatePicker datePicker;

        dialog.setContentView(R.layout.pp_create_visite);
        btnUpdate = dialog.findViewById(R.id.pp_create_visit_btn_create);
        btnDelete = dialog.findViewById(R.id.pp_create_visit_btn_cancel);
        spinnerVille = dialog.findViewById(R.id.pp_create_visit_select_ville);
        spinnerEnseigne = dialog.findViewById(R.id.pp_create_visit_select_enseigne);
        imageEnseigne = dialog.findViewById(R.id.pp_create_visit_icon_enseigne);
        textVille = dialog.findViewById(R.id.pp_create_visit_display_ville);
        txtTitle = dialog.findViewById(R.id.pp_create_visit_txt_title);
        datePicker = dialog.findViewById(R.id.pp_create_visit_date_picker);
        txtTitle.setText("Update Visit");
        btnUpdate.setText("Update");
        btnDelete.setText("Delete");

        Magasin magasin = DataManager.INSTANCE.getMagasinById(visite.getIdMagasin());

        spinnerEnseigne.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, R.id.item_spinner_text, EnumEnseigne.values()));
        spinnerVille.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner_magasin, R.id.item_spinner_magasin_text, EnumVille.values()));
        spinnerEnseigne.setSelection(magasin.getEnseigne().getId());
        spinnerVille.setSelection(magasin.getVille());
        String[] dateFormat = visite.getDateVisite().split("/");
        datePicker.updateDate(Integer.valueOf(dateFormat[2]), Integer.valueOf(dateFormat[1]), Integer.valueOf(dateFormat[0]));

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DataManager.INSTANCE.deleteVisiteById(visiteSelected.getId());
                visitesDisplay.remove(posVisiteSelected);
                visiteSelected = null;
                listVisites.removeViewInLayout(itemSelected);
                itemSelected = null;
                listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), visitesDisplay));
                listVisites.invalidate();
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(calendar.getTime());
                visiteSelected.setDateVisite(date);
                visiteSelected.setIdMagasin(DataManager.INSTANCE.getMagasinByEnseigneAndCity(
                        EnumEnseigne.getEnumFromId(spinnerEnseigne.getSelectedItemPosition()), spinnerVille.getSelectedItemPosition()).getId());
                visitesDisplay.set(posVisiteSelected, visiteSelected);
                listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), visitesDisplay));
                listVisites.invalidate();
                DataManager.INSTANCE.saveVisite(visiteSelected);
                Toast.makeText(getApplicationContext(), "Succesfully edit visite date = " + date, Toast.LENGTH_LONG).show();
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
                textVille.setText(spinnerVille.getSelectedItem().toString());
                btnUpdate.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(calendar.getTime());

                Visite newVisite = DataManager.INSTANCE.addVisite(sellerId,
                        DataManager.INSTANCE.getMagasinByEnseigneAndCity(
                                EnumEnseigne.getEnumFromId(spinnerEnseigne.getSelectedItemPosition()), spinnerVille.getSelectedItemPosition()).getId(),
                        date);

                visitesDisplay.add(newVisite);
                listVisites.setAdapter(new ManagerVisiteAdapter(getApplicationContext(), visitesDisplay));

                Toast.makeText(getApplicationContext(), "Succesfully create new visite : " + DataManager.INSTANCE.getMagasinById(newVisite.getIdMagasin()).getDisplayName() + " " + date + ".", Toast.LENGTH_LONG).show();
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
