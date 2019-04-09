package iia.tristan.persistanceclient.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import iia.tristan.persistanceclient.R;
import iia.tristan.persistanceclient.client.adapter.VisiteAdapter;
import share.dataObject.Visite;

public class VisiteActivity extends AppCompatActivity {

    private ListView listVisites;

    private Visite itemSelected;
    private int idItemSelected = -1;
    private View lastViewSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite);

        listVisites = findViewById(R.id.list_visite);

        final Visite[] items = null;

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
