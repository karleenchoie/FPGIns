package com.example.fpgins.BottomNavigation.FPGAssist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.FAQData;
import com.example.fpgins.R;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    private List<FAQData> mData;
    private Context mContext;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition  = -1;

    public HelpAdapter(List<FAQData> list, Context context){
        this.mData = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        FAQData data = mData.get(position);

        final boolean isExpanded = position == mExpandedPosition;
        holder.question.setText(data.getTitle());
        holder.question.setCompoundDrawablesWithIntrinsicBounds(0, 0, isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up, 0);
        holder.description.setText(data.getContent());
        holder.description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded){
            previousExpandedPosition = position;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView question;
        public TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.txt_question);
            description = itemView.findViewById(R.id.txt_question_desc);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
