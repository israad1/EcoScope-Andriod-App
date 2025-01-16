package com.example.final_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class frag_display_carbon_footprint extends Fragment {

    private static final String ARG_RESULT = "result";

    public static frag_display_carbon_footprint newInstance(String result) {
        frag_display_carbon_footprint fragment = new frag_display_carbon_footprint();
        Bundle args = new Bundle();
        args.putString(ARG_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_display_carbon_footprint, container, false);
        TextView textView = view.findViewById(R.id.result_text);
        String result = getArguments().getString(ARG_RESULT, "No result");
        textView.setText(result);
        return view;
    }
}