package com.example.fpgins.Utility;


import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

import com.example.fpgins.R;

public class DialogMain {
	
	Context context;
	TextView TV_Title;
	TextView TV_Message;
	Button Btn_Positive;
	Button Btn_Negative;
	Dialog dialog;
	
	public DialogMain(Context context, int theme) {
		dialog = new Dialog(context,theme);
		this.context = context;
		dialog.setCancelable(false);		
		init();
	}
	
	public interface ClickListener{
		void onClickListener(DialogMain dialog);
	}
	private void init(){
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.alert_pop_up_dialog, null);
		dialog.setContentView(view);
		TV_Title = (TextView) view.findViewById(R.id.TV_Title);
		TV_Message = (TextView) view.findViewById(R.id.TV_Msg);
		Btn_Positive = (Button) view.findViewById(R.id.dialog_button_pos);
		Btn_Negative = (Button) view.findViewById(R.id.dialog_button_neg);
			
	}
	public void setPositiveButton(int text, ClickListener clickListener){
		setPositiveButton(context.getString(text),clickListener);
	}
	public void setPositiveButton(String text, final ClickListener clickListener){
		Btn_Positive.setText(text);
		Btn_Positive.setVisibility(View.VISIBLE);
		Btn_Positive.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				clickListener.onClickListener(DialogMain.this);
			}
		});
	}
	public void setNegativeButton(int text, ClickListener clickListener){
		setNegativeButton(context.getString(text),clickListener);
	}
	public void setNegativeButton(String text, final ClickListener clickListener){
		Btn_Negative.setText(text);
		Btn_Negative.setVisibility(View.VISIBLE);
		Btn_Negative.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				clickListener.onClickListener(DialogMain.this);
			}
		});
	}
	public void setTitle(int text){
		setTitle(context.getString(text));
	}
	public void setTitle(final CharSequence text){
		TV_Title.setText(text);
		TV_Title.setVisibility(View.VISIBLE);
	}
	public void setMessage(int text){
		setMessage(context.getString(text));
	}
	public void setMessage(final CharSequence text){
		TV_Message.setText(text);
		TV_Message.setVisibility(View.VISIBLE);
	}
	public void show(){
		dialog.show();
	}
	public void dismiss(){
		dialog.dismiss();
	}
	public void cancel(){
		dialog.cancel();
	}
}
