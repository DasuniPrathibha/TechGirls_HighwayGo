package com.example.highwaygo.ui.mybookings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyBookingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyBookingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}