package com.cancunsoftware.hotelbooking.userinterfaces.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cancunsoftware.hotelbooking.R;

public class ServicesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_services, container, false);
        setHasOptionsMenu(true);
        return root;
    }
}