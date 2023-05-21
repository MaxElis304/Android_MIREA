package com.example.pr7.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pr7.Domain_Layer.Repository;

public class MainViewModel extends ViewModel {
    private Repository repository;

    public MainViewModel() {
        repository = new Repository();
    }

    public LiveData<String> getData() {
        return repository.getData();
    }

    public void setData(String newData) {
        repository.setData(newData);
    }
}


