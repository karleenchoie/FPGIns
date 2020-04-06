package com.example.fpgins.ui.customercare;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.R;

public class CustomerCareFragment extends AppCompatActivity {

//    private ToolsViewModel toolsViewModel;
    private Button mSubmit;
    private BottomSheetMaterialDialog bottomSheetMaterialDialog;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tools);
//        toolsViewModel = ViewModelProviders.of(this).get(ToolsViewModel.class);


        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSubmit = findViewById(R.id.btn_sumbit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                delete();
            }
        });
    }

//    private void delete(){
//        bottomSheetMaterialDialog = new BottomSheetMaterialDialog.Builder(this)
//                .setTitle("Delete?")
//                .setMessage("Are you sure want to delete this file?")
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.delete), new BottomSheetMaterialDialog.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//
//                    }
//                })
//                .setNegativeButton(getString(R.string.cancel), new BottomSheetMaterialDialog.OnClickListener(){
//
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .build();
//        bottomSheetMaterialDialog.show();
//    }
}