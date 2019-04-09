package client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import client.adapter.VisiteAdapter;
import iia.tristan.persistanceclient.R;
import share.dataObject.Visite;
import share.manager.DataManager;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerVisitesActivity extends AppCompatActivity {

    public static String SELLER_ID = "SELLER_ID";

    private ListView listVisites;
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

}
