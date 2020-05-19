package com.example.fpgins.BottomNavigation.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.ClientPoliciesData;
import com.example.fpgins.DataModel.VehicleDetailsData;
import com.example.fpgins.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientPolicyAdapter extends RecyclerView.Adapter<ClientPolicyAdapter.MyViewHolder> {

    private List<ClientPoliciesData> clientPoliciesDataList;
    private List<VehicleDetailsData> vehicleDetailsDataList;

    private ArrayList<String> make;

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mPolicyNumber;
        public RelativeLayout mRelativeLayout;


        public MyViewHolder(View view) {
            super(view);
            mPolicyNumber = view.findViewById(R.id.txt_clientPolicy);
            mRelativeLayout = view.findViewById(R.id.relative_clientPolicy);
            mRelativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ClientPoliciesData clientPoliciesData = clientPoliciesDataList.get(getLayoutPosition());
            VehicleDetailsData vehicleDetailsData = vehicleDetailsDataList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, ClientPoliciesDetailsActivity.class);
            intent.putExtra("clientpolicynum", clientPoliciesData.getmPolicyNumber());
            intent.putExtra("clientcertificatenum", clientPoliciesData.getmCertificateNo());
            intent.putExtra("clientinceptiondate", clientPoliciesData.getmInceptionDate());
            intent.putExtra("clientexpirydate", clientPoliciesData.getmExpiryDate());
            intent.putExtra("clientassuredid", clientPoliciesData.getmAssuredID());
            intent.putExtra("clientassuredname", clientPoliciesData.getmAssuredName());
            intent.putExtra("clientintermediaryid", clientPoliciesData.getmIntermediaryID());

//            intent.putExtra("clientvehiclemake", vehicleDetailsData.getmMake());
//            intent.putExtra("clientvehiclemodel", vehicleDetailsData.getmModel());
//            intent.putExtra("clientvehiclevariance", vehicleDetailsData.getmVariant());
//            intent.putExtra("clientvehicletype", vehicleDetailsData.getmTypeBody());
//            intent.putExtra("clientvehicleyear", vehicleDetailsData.getmYearManufactured());
//            intent.putExtra("clientvehicleplate", vehicleDetailsData.getmPlateNumber());
//            intent.putExtra("clientvehicleconduction", vehicleDetailsData.getmConductionSticker());
//            intent.putExtra("clientvehiclemv", vehicleDetailsData.getmMvFileNo());
//            intent.putExtra("clientvehicleengine", vehicleDetailsData.getmEngineNo());
//            intent.putExtra("clientvehiclechassis", vehicleDetailsData.getmChassisNo());
//            intent.putExtra("clientvehiclecolor", vehicleDetailsData.getmColor());
//            intent.putExtra("clientvehiclepassenger", vehicleDetailsData.getmPassengerNo());
//            intent.putExtra("clientvehicleaccesories", vehicleDetailsData.getmAccessories());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }


    public ClientPolicyAdapter(ArrayList<ClientPoliciesData> clientPoliciesDataList, ArrayList<VehicleDetailsData> vehicleDetailsDataList, Context contex) {
        this.clientPoliciesDataList = clientPoliciesDataList;
        this.vehicleDetailsDataList = vehicleDetailsDataList;
        this.mContext = contex;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_policies_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ClientPoliciesData clientPoliciesData = clientPoliciesDataList.get(position);
        holder.mPolicyNumber.setText(clientPoliciesData.getmPolicyNumber());
    }

    @Override
    public int getItemCount() {
        return clientPoliciesDataList.size();
    }
}