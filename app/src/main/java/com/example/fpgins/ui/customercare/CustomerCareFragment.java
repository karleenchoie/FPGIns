package com.example.fpgins.ui.customercare;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;

public class CustomerCareFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private Button mSubmit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        mSubmit = (Button) root.findViewById(R.id.btn_sumbit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                    .setTitle("Delete?")
                    .setMessage("Are you sure want to delete this file?")
                    .setCancelable(false)
                    .setPositiveButton("Delete", new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();

            // Show Dialog
            mBottomSheetDialog.show();
            }
        });
        return root;
    }
}