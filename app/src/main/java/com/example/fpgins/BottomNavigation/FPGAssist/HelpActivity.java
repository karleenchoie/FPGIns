package com.example.fpgins.BottomNavigation.FPGAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fpgins.R;

public class HelpActivity extends AppCompatActivity {

    private ImageView mBackButton;

    private TextView mQuestionOne, mQuestioTwo, mQuestionThree, mDescriptionOne, mDescriptionTwo, mDescriptionThree;
    private boolean isDisplayOne = false;
    private boolean isDisplayTwo = false;
    private boolean isDisplayThree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mBackButton = findViewById(R.id.img_backbutton);

        mQuestionOne = findViewById(R.id.txt_question1);
        mQuestioTwo = findViewById(R.id.txt_question2);
        mQuestionThree = findViewById(R.id.txt_question3);
        mDescriptionOne = findViewById(R.id.txt_question1_desc);
        mDescriptionTwo = findViewById(R.id.txt_question2_desc);
        mDescriptionThree = findViewById(R.id.txt_question3_desc);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mQuestionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDisplayOne = !isDisplayOne;
                if (isDisplayOne == true){
                    mDescriptionOne.setVisibility(View.VISIBLE);
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    mQuestioTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionTwo.setVisibility(View.GONE);
                    mDescriptionThree.setVisibility(View.GONE);

                }else {
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestioTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionOne.setVisibility(View.GONE);
                    mDescriptionTwo.setVisibility(View.GONE);
                    mDescriptionThree.setVisibility(View.GONE);
                }
            }
        });
        mQuestioTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDisplayTwo = !isDisplayTwo;
                if (isDisplayTwo == true){
                    mQuestioTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionOne.setVisibility(View.GONE);
                    mDescriptionTwo.setVisibility(View.VISIBLE);
                    mDescriptionThree.setVisibility(View.GONE);
                }else {
                    mQuestioTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionOne.setVisibility(View.GONE);
                    mDescriptionTwo.setVisibility(View.GONE);
                    mDescriptionThree.setVisibility(View.GONE);
                }
            }
        });

        mQuestionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDisplayThree = !isDisplayThree;
                if (isDisplayThree == true){
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestioTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionOne.setVisibility(View.GONE);
                    mDescriptionTwo.setVisibility(View.GONE);
                    mDescriptionThree.setVisibility(View.VISIBLE);
                }else {
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mQuestionThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    mDescriptionOne.setVisibility(View.GONE);
                    mDescriptionTwo.setVisibility(View.GONE);
                    mDescriptionThree.setVisibility(View.GONE);
                }
            }
        });
    }

    public void checkDisplayed(){

    }
}
