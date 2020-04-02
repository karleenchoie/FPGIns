package com.example.fpgins.ui.motors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MotorsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MotorsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is motors fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
