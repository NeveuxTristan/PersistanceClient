package iia.tristan.persistanceclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import iia.tristan.persistanceclient.R;
import iia.tristan.persistanceclient.client.connection.ConnectionManager;

public class ChooseAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btnManager;
    private MaterialButton btnSeller1;
    private MaterialButton btnSeller2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        btnManager = findViewById(R.id.btn_account_manager);
        btnSeller1 = findViewById(R.id.btn_account_seller1);
        btnSeller2 = findViewById(R.id.btn_account_seller2);

        btnManager.setOnClickListener(this);
        btnSeller1.setOnClickListener(this);
        btnSeller2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnManager) {
            ConnectionManager.INSTANCE.setUser(true, 1);
            switchToManagerView();
        } else if (v == btnSeller1) {
            ConnectionManager.INSTANCE.setUser(false, 2);
            switchToSellerView();
        } else if (v == btnSeller2) {
            ConnectionManager.INSTANCE.setUser(false, 3);
            switchToSellerView();
        }
    }

    private void switchToManagerView() {
        Intent menuIntent = new Intent(this, ManagerActivity.class);
        startActivity(menuIntent);
    }

    private void switchToSellerView() {
        Intent menuIntent = new Intent(this, VisiteActivity.class);
        startActivity(menuIntent);
    }
}