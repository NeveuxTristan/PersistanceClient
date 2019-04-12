package client;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import client.adapter.VisiteAdapter;
import client.connection.ConnectionManager;
import iia.tristan.persistanceclient.R;
import share.dataObject.Magasin;
import share.dataObject.Visite;
import share.enumUtils.EnumEnseigne;
import share.enumUtils.EnumVille;
import share.manager.DataManager;

public class VisiteActivity extends AppCompatActivity {

    private ListView listVisites;
    private Dialog dialog;
    private ArrayList<Visite> visitesDisplay;
    private View itemSelected;
    private Visite visiteSelected;
    private int posVisiteSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite);
        this.dialog = new Dialog(this);

        listVisites = findViewById(R.id.list_visite);

        visitesDisplay = DataManager.INSTANCE.getAllVisiteByUser(ConnectionManager.INSTANCE.getUser());
        listVisites.setAdapter(new VisiteAdapter(getApplicationContext(), visitesDisplay));

        TextView empty = findViewById(R.id.activity_visite_txt_empty);
        listVisites.setEmptyView(empty);

        listVisites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                posVisiteSelected = position;
                visiteSelected = visitesDisplay.get(position);
                itemSelected = view;
                showPpSellerVisite(visiteSelected);
            }
        });
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
                visiteSelected.setVisiteDone(stateVisit.isChecked());
                visiteSelected.setComment(comment.getText().toString());
                visitesDisplay.set(posVisiteSelected, visiteSelected);
                itemSelected.invalidate();
                DataManager.INSTANCE.saveVisite(visiteSelected);
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
