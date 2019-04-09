package client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import iia.tristan.persistanceclient.R;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {


    private MaterialButton btnSeller1;
    private MaterialButton btnSeller2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        btnSeller1 = findViewById(R.id.btn_manager_select_seller1);
        btnSeller2 = findViewById(R.id.btn_manager_select_seller2);

        btnSeller1.setOnClickListener(this);
        btnSeller2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view == btnSeller1)
        {
            switchToManagerSellerVisiteActivity(2);
        }
        else
            if (view == btnSeller2)
            {
                switchToManagerSellerVisiteActivity(3);
            }
    }

    private void switchToManagerSellerVisiteActivity(int id)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(ManagerVisitesActivity.SELLER_ID, id);
        Intent menuIntent = new Intent(this, ManagerVisitesActivity.class);
        startActivity(menuIntent, bundle);
    }

}
