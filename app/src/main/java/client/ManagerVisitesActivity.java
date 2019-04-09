package client;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import client.adapter.VisiteAdapter;
import iia.tristan.persistanceclient.R;
import share.dataObject.Visite;
import share.manager.DataManager;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerVisitesActivity extends AppCompatActivity implements View.OnClickListener {

    public static String SELLER_ID = "SELLER_ID";

    private ListView listVisites;
    private ImageButton btnAddVisite;
    private ImageButton btnDeleteAll;

    private int sellerId;

    private Visite itemSelected;
    private int idItemSelected = -1;
    private View lastViewSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_visites);

        sellerId = savedInstanceState.getInt(SELLER_ID);

        listVisites = findViewById(R.id.list_visite_manager);
        btnAddVisite = findViewById(R.id.bt_manager_visites_add);
        btnDeleteAll = findViewById(R.id.bt_manager_visites_delete);

        btnDeleteAll.setOnClickListener(this);
        btnAddVisite.setOnClickListener(this);

        final Visite[] items = (Visite[]) DataManager.INSTANCE.getAllVisiteByUser(sellerId).toArray();

        listVisites.setAdapter(new VisiteAdapter(getApplicationContext(), items));

        listVisites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectItem(view, position);
            }
        });
    }

    private void selectItem(View view, int position)
    {

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

            }
    }
}
