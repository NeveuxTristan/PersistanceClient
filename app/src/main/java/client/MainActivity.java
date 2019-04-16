package client;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import client.connection.ConnectionManager;
import iia.tristan.persistanceclient.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static MainActivity INSTANCE;

    private MaterialButton buttonStartOnline;
    private MaterialButton buttonStartOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        buttonStartOnline = findViewById(R.id.menu_btn_online);
        buttonStartOffline = findViewById(R.id.menu_btn_offline);

        buttonStartOffline.setOnClickListener(this);
        buttonStartOnline.setOnClickListener(this);

        INSTANCE = this;

        if (!ConnectionManager.INSTANCE.getStateFirstConnection()) {
            buttonStartOffline.setEnabled(false);
            Toast.makeText(getApplicationContext(), "This is your first Connection, you must start app online to synchronize datas", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonStartOnline) {
            buttonStartOffline.setEnabled(true);
            ConnectionManager.INSTANCE.connect();
            switchToChooseAccountPage();
        } else if (v == buttonStartOffline) {
            ConnectionManager.INSTANCE.disconnect();
            switchToChooseAccountPage();
        }
    }

    /**
     * On passe à la sélection de compte
     */
    private void switchToChooseAccountPage() {
        Intent menuIntent = new Intent(this, ChooseAccountActivity.class);
        startActivity(menuIntent);
    }

}
