package com.example.fpgins.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fpgins.R;

/**
 * Created by mark on 22/07/2016.
 */
public class DefaultDialog extends Dialog {

    public static class Builder {
        private DefaultDialog dialog;
        public Builder(Context context) {
            dialog = new DefaultDialog(context);
        }

        public Builder title(int value) {
            dialog.setTitle(value);
            return this;
        }

        public Builder message(String value) {
            dialog.setMessage(value);
            return this;
        }

        public Builder message(int value) {
            dialog.setMessage(value);
            return this;
        }

        public Builder detail(String value) {
            dialog.setExtraDetail(value);
            return this;
        }

        public Builder detail(int value) {
            dialog.setExtraDetail(value);
            return this;
        }

        public Builder editText(int hint, int visibility){
            dialog.setEditText(hint, visibility);
            return this;
        }

        public Builder editText(String hint, int visibility){
            dialog.setEditText(hint, visibility);
            return this;
        }

        public Builder imageResource(Drawable image, int visibilty){
            dialog.setImageResource(image, visibilty);
            return this;
        }

        public Builder positiveAction(String label, OnClickListener listener) {
            dialog.setPositiveAction(label, listener);
            return this;
        }

        public Builder positiveAction(int labelId, OnClickListener listener) {
            dialog.setPositiveAction(labelId, listener);
            return this;
        }

        public Builder negativeAction(String label, OnClickListener listener) {
            dialog.setNegativeAction(label, listener);
            return this;
        }

        public Builder negativeAction(int labelId, OnClickListener listener) {
            dialog.setNegativeAction(labelId, listener);
            return this;
        }


        public DefaultDialog build() {
            dialog.shrinkContent();
            dialog.setCancelable(false);
            dialog.getEditTextString();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return dialog;
        }
    }

    public interface OnClickListener {
        void onClick(Dialog dialog, String et);
    }


    private Button positiveButton;
    private Button negativeButton;
    private EditText editText;

    public DefaultDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_default);
        positiveButton = (Button) findViewById(R.id.dialog_default_button_positive);
        negativeButton = (Button) findViewById(R.id.dialog_default_button_negative);
        editText = findViewById(R.id.etDialog);

    }

    public void setMessage(String message) {
        ((TextView)findViewById(R.id.dialog_default_message)).setText(message);
//        ((TextView)findViewById(R.id.dialog_default_message)).setGravity(Gravity.CENTER);
    }

    public void setMessage(int messageId) {
        ((TextView)findViewById(R.id.dialog_default_message)).setText(messageId);
    }

    public void setExtraDetail(String extraDetail) {
        ((TextView)findViewById(R.id.dialog_default_extra_detail)).setText(extraDetail);
    }

    public void setExtraDetail(int extraDetail) {
        ((TextView)findViewById(R.id.dialog_default_extra_detail)).setText(extraDetail);
    }

    public void setImageResource(Drawable drawable, int visibility){
        ImageView imageView = (ImageView) findViewById(R.id.imagePreview);
        imageView.setImageDrawable(drawable);
        imageView.setVisibility(visibility);
    }

    public void setEditText (int hint, int visibility){
        editText.setHint(hint);
        editText.setVisibility(visibility);
    }

    public void setEditText (String hint, int visibility){
        editText.setHint(hint);
        editText.setVisibility(visibility);
    }

    public void setPositiveAction(String label, final OnClickListener listener) {
        positiveButton.setText(label);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DefaultDialog.this, getEditTextString());
            }
        });
    }

    public void setPositiveAction(int labelId, final OnClickListener listener) {
        positiveButton.setText(labelId);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DefaultDialog.this, getEditTextString());
            }
        });
    }

    public void setNegativeAction(String label, final OnClickListener listener) {
        negativeButton.setText(label);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DefaultDialog.this, "");
            }
        });
    }

    public void setNegativeAction(int labelId, final OnClickListener listener) {
        negativeButton.setText(labelId);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DefaultDialog.this, "");
            }
        });
    }

    public String getEditTextString(){
        return editText.getText().toString();
    }

    private boolean hasPositiveAction() {
        return !positiveButton.getText().toString().isEmpty();
    }

    private boolean hasNegativeAction() {
        return !negativeButton.getText().toString().isEmpty();
    }

    private void shrinkContent() {
        if (!hasPositiveAction()) {
            positiveButton.setVisibility(View.GONE);
        }
        if (!hasNegativeAction()) {
            negativeButton.setVisibility(View.GONE);
        }
    }

}
