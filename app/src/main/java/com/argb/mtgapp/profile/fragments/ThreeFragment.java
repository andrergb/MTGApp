package com.argb.mtgapp.profile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.argb.mtgapp.R;

import androidx.fragment.app.Fragment;

public class ThreeFragment extends Fragment {
    public ThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three, container, false);
    }
}
