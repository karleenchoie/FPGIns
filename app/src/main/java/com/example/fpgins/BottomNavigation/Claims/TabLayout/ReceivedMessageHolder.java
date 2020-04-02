package com.example.fpgins.BottomNavigation.Claims.TabLayout;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.MessagingData;
import com.example.fpgins.R;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

    public TextView mMessage;
    public TextView mTime;

    public ReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        mMessage = (TextView) itemView.findViewById(R.id.message_body);
        mTime = (TextView) itemView.findViewById(R.id.message_time);
    }

    void bind(MessagingData messagingData){
        mMessage.setText(messagingData.getMessage());
        mTime.setText(messagingData.getTime());
    }
}
