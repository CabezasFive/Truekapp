package com.cabezasfive.truekapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cabezasfive.truekapp.models.PublicacionViewModel;

public class PublicacionFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final Application application;

    public PublicacionFactory(@NonNull Application application){
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == PublicacionViewModel.class){
            return (T) new PublicacionViewModel(application);
        }
        return null;
    }
}
