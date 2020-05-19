package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.Settings.PersonalInformation;
import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;

import java.util.ArrayList;

public class ClientNameDetails extends AppCompatActivity {

    private ImageView mBackButton;
    private RecyclerView mRecyclerView;
    private EditText mID, mFirstName, mLastName, mBirthday, mContact1;
    private Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_name_details);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mID = findViewById(R.id.txt_clientId);
        mFirstName = findViewById(R.id.txt_clientFirstName);
        mBirthday = findViewById(R.id.txt_clientBday);
        mContact1 = findViewById(R.id.txt_clientMobile);
        mRegister = findViewById(R.id.btn_registerClient);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mFirstName.setText(bundle.getString("adclientname"));
            mID.setText(bundle.getString("adclientid"));
        }


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ClientNameDetails.this, "Client registered", Toast.LENGTH_LONG).show();
                try {
                    new DefaultDialog.Builder(ClientNameDetails.this)
                            .imageResource(ContextCompat.getDrawable(ClientNameDetails.this, R.drawable.green_check), View.VISIBLE)
                            .message("Client successfully registered")
                            .positiveAction("OK", new DefaultDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, String et) {
                                    dialog.dismiss();
                                }
                            })
                            .build()
                            .show();
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });

    }
}
