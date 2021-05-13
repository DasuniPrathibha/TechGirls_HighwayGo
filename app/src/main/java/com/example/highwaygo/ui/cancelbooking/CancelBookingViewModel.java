package com.example.highwaygo.ui.cancelbooking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CancelBookingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CancelBookingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}