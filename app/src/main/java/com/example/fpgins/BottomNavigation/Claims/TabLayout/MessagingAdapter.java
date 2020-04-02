package com.example.fpgins.BottomNavigation.Claims.TabLayout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.MessagingData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;

import java.util.List;

public class MessagingAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<MessagingData> messagingDataList;
    private Context mContext;

    public MessagingAdapter(List<MessagingData> messagingDataList, Context context) {
        this.messagingDataList = messagingDataList;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        MessagingData messagingData = messagingDataList.get(position);
        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(mContext));

        String id = userData.getId();
        String created = messagingData.getCreatedBy();

        if (id.equals(created)){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_receive, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagingData messagingData = messagingDataList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(messagingData);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(messagingData);
                break;
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.message_time);
        }

        void bind (MessagingData messagingData){
            messageText.setText(messagingData.getMessage());
            timeText.setText(messagingData.getTime());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.message_time);
        }

        void bind (MessagingData messagingData){
            messageText.setText(messagingData.getMessage());
            timeText.setText(messagingData.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return messagingDataList.size();
    }
}
