package iia.tristan.persistanceclient.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import iia.tristan.persistanceclient.R;
import iia.tristan.persistanceclient.client.connection.ConnectionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton buttonStartOnline;
    private MaterialButton buttonStartOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartOnline = (MaterialButton) findViewById(R.id.menu_btn_online);
        buttonStartOffline = (MaterialButton) findViewById(R.id.menu_btn_offline);

        buttonStartOffline.setOnClickListener(this);

        if (ConnectionManager.INSTANCE.getStateFirstConnection()) {
            buttonStartOffline.setEnabled(false);
            Toast.makeText(getApplicationContext(), "This is your first Connection, you must start app online to synchronize datas", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonStartOnline) {
            ConnectionManager.INSTANCE.connect();
            switchToChooseAccountPage();
        } else if (v == buttonStartOffline) {
            switchToChooseAccountPage();
        }
    }

    private void switchToChooseAccountPage() {

    }
}
