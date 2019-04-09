package client;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import iia.tristan.persistanceclient.R;
import client.connection.ConnectionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static MainActivity INSTANCE;

    private MaterialButton buttonStartOnline;
    private MaterialButton buttonStartOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartOnline = findViewById(R.id.menu_btn_online);
        buttonStartOffline = findViewById(R.id.menu_btn_offline);

        buttonStartOffline.setOnClickListener(this);

        if (ConnectionManager.INSTANCE.getStateFirstConnection())
        {
            buttonStartOffline.setEnabled(false);
            Toast.makeText(getApplicationContext(), "This is your first Connection, you must start app online to synchronize datas", Toast.LENGTH_LONG).show();
        }
        INSTANCE = this;
    }

    @Override
    public void onClick(View v)
    {
        if (v == buttonStartOnline)
        {
            ConnectionManager.INSTANCE.connect();
            switchToChooseAccountPage();
        }
        else
            if (v == buttonStartOffline)
            {
                switchToChooseAccountPage();
            }
    }

    /**
     * On passe à la sélection de compte
     */
    private void switchToChooseAccountPage()
    {
        Intent menuIntent = new Intent(this, ChooseAccountActivity.class);
        startActivity(menuIntent);
    }

    public AssetManager getAssetManager()
    {
        return getAssets();
    }
}
