package client;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import client.adapter.VisiteAdapter;
import client.connection.ConnectionManager;
import iia.tristan.persistanceclient.R;
import share.manager.DataManager;

public class VisiteActivity extends AppCompatActivity {

    private ListView listVisites;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite);

        listVisites = findViewById(R.id.list_visite);
        listVisites.setAdapter(new VisiteAdapter(getApplicationContext(), DataManager.INSTANCE.getAllVisiteByUser(ConnectionManager.INSTANCE.getUser())));
    }

}
